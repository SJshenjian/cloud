package online.shenjian.cloud.api.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.common.util.StringUtils;
import online.shenjian.cloud.api.base.mapper.UserMapper;
import online.shenjian.cloud.api.base.mapper.UserPlusMapper;
import online.shenjian.cloud.api.base.model.User;
import online.shenjian.cloud.api.base.service.UserService;
import online.shenjian.cloud.api.config.security.model.Claims;
import online.shenjian.cloud.api.system.mapper.ModulePlusMapper;
import online.shenjian.cloud.api.utils.TokenUtils;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleDto;
import online.shenjian.cloud.client.cloud.dto.user.UserQueryDto;
import online.shenjian.cloud.client.common.ResponseVo;
import online.shenjian.cloud.common.enums.Constant;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import online.shenjian.cloud.common.utils.MD5Encryption;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author shenjian
 * @since 2023/7/13
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserPlusMapper userPlusMapper;
    private ModulePlusMapper modulePlusMapper;


    public UserServiceImpl(UserMapper UserMapper, UserPlusMapper userPlusMapper, ModulePlusMapper modulePlusMapper) {
        this.userMapper = UserMapper;
        this.userPlusMapper = userPlusMapper;
        this.modulePlusMapper = modulePlusMapper;
    }

    @Override
    public ResponseVo login(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return ResponseVo.error("请输入用户名");
        }
        if (StringUtils.isBlank(password) || "".equals(password.trim())) {
            return ResponseVo.error("请输入密码!");
        }

        // 根据用户登录名获取用户实体
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", username);
        wrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        wrapper.last("LIMIT 1");
        User user = userMapper.selectOne(wrapper);

        // 判断是否存在该用户
        if (user == null || password == null || "".equals(password)) {
            return ResponseVo.error("用户信息不存在");
        }
        if (user.getState().equals(Constant.YesOrNo.YES.val())) {
            return ResponseVo.error("账户被禁用，请联系管理员!");
        }
        // 判断用户密码和加密后输入密码是否一致
        if (!user.getPassword().equals(password)) {
            return ResponseVo.error("输入密码和用户密码不匹配，登录失败！");
        }
        Claims claims = new Claims();
        claims.setUserId(user.getUserId());
        claims.setOrgCode(user.getOrgCode());
        claims.setAccount(user.getAccount());
        claims.setUsername(user.getUsername());
        claims.setRoles(Arrays.asList(user.getRoleId()));

        QueryWrapper<ModuleDto> moduleInfoQueryWrapper = new QueryWrapper<>();

        moduleInfoQueryWrapper.eq("role_id", user.getRoleId());
        List<ModuleDto> moduleInfoDtoList = modulePlusMapper.list(moduleInfoQueryWrapper);

        // 获取权限列表
        String token = TokenUtils.buildToken(claims);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("account", user.getAccount());
        jsonObject.put("username", user.getUsername());
        // 权限列表
        List<String> permissions = moduleInfoDtoList.stream().map(ModuleDto::getModuleCode).collect(Collectors.toList());
        jsonObject.put("permissions", permissions);

        claims.setPermissions(permissions);

        return ResponseVo.success(jsonObject);
    }

    @Override
    public ResponseVo<String> updatePassword(String originPassword, String newPassword) {
        if (StringUtils.isBlank(originPassword) || StringUtils.isBlank(newPassword)) {
            return ResponseVo.error("原密码或新密码不能为空!");
        }

        Claims claims = TokenUtils.getClaimsFromToken();
        final String account = claims.getAccount();
        final String orgCode = claims.getOrgCode();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("org_code", orgCode);
        wrapper.eq("account", account);
        wrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            return ResponseVo.error("用户信息不存在!");
        }

        if (!user.getPassword().equals(MD5Encryption.MD5(originPassword))) {
            return ResponseVo.error("原密码输入错误!");
        }

        user.setPassword(MD5Encryption.MD5(newPassword));
        user.setUpdateTime(new Date());
        user.setUpdateUser(claims.getAccount());
        userMapper.updateById(user);
        return ResponseVo.success("修改密码成功!");
    }

    @Override
    public IPage<UserDto> listUser(UserQueryDto userQueryDto) {
        IPage<UserDto> page = new Page<>(userQueryDto.getPageNumber(), userQueryDto.getPageSize());
        QueryWrapper<UserDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        // 查询该组织机构及下属机构下所有用户信息
        if (StringUtils.isNotBlank(userQueryDto.getOrgCode())) {
            queryWrapper.likeRight("org_code", userQueryDto.getOrgCode());
        } else {
            Claims claims = TokenUtils.getClaimsFromToken();
            queryWrapper.likeRight("org_code", claims.getOrgCode());
        }

        if (StringUtils.isNotBlank(userQueryDto.getUsername())) {
            queryWrapper.like("username", userQueryDto.getUsername());
        }
        if (StringUtils.isNotBlank(userQueryDto.getAccount())) {
            queryWrapper.like("account", userQueryDto.getAccount());
        }
        IPage<UserDto> iPage = userPlusMapper.page(page, queryWrapper);
        return iPage;
    }

    @Override
    public ResponseVo saveUser(UserDto userDto) {
        ResponseVo error = this.checkAccount(userDto);
        if (error != null) return error;

        User user = CommonDtoUtils.transform(userDto, User.class);
        user.setUserId(IdUtil.getSnowflakeNextIdStr());
        user.setPassword(MD5Encryption.MD5(Constant.INIT_PASSWORD));
        user.setDelFlag(Constant.YesOrNo.NO.val());
        user.setCreateTime(new Date());
        Claims claims = TokenUtils.getClaimsFromToken();
        user.setCreateUser(claims.getAccount());
        userMapper.insert(user);
        return ResponseVo.success("新增成功，默认登录密码是：" + Constant.INIT_PASSWORD);
    }

    private ResponseVo checkAccount(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", userDto.getAccount());
        queryWrapper.last("LIMIT 1");
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser != null) {
            return ResponseVo.error("您输入的登录账号已存在!");
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ;
        }
        User user = new User();
        user.setUserId(userId);
        user.setDelFlag(Constant.YesOrNo.YES.val());
        user.setUpdateTime(new Date());
        user.setUpdateUser(TokenUtils.getClaimsFromToken().getAccount());
        userMapper.updateById(user);
    }

    @Override
    public ResponseVo updateUser(UserDto userDto) {
        if (StringUtils.isBlank(userDto.getUserId())) {
            return ResponseVo.error("用户ID不能为空");
        }

        User user = CommonDtoUtils.transform(userDto, User.class);
        user.setUpdateUser(TokenUtils.getClaimsFromToken().getAccount());
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return ResponseVo.success("编辑成功！");
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseVo resetPassword(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResponseVo.error("用户ID不能为空");
        }
        User user = new User();
        user.setPassword(MD5Encryption.MD5(Constant.INIT_PASSWORD));
        user.setUserId(userId);
        userMapper.updateById(user);
        user.setUpdateUser(TokenUtils.getClaimsFromToken().getAccount());
        user.setUpdateTime(new Date());
        return ResponseVo.success("修改成功,重置后的密码为:" + Constant.INIT_PASSWORD);
    }
}
