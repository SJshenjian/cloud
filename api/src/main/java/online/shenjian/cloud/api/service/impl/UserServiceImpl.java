package online.shenjian.cloud.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.shenjian.cloud.api.config.security.model.Claims;
import online.shenjian.cloud.api.mapper.UserMapper;
import online.shenjian.cloud.api.model.User;
import online.shenjian.cloud.api.service.UserService;
import online.shenjian.cloud.api.utils.TokenUtils;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjian
 * @since 2023-08-25
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVo login(UserDto userDto) {
        // 根据用户登录名获取用户实体
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDto.getUsername());
        wrapper.last("LIMIT 1");
        User user = userMapper.selectOne(wrapper);

        // 假设用户一定存在且密码正确

        Claims claims = new Claims();
        claims.setUserId(user.getId());
        claims.setUsername(user.getUsername());

        // 获取权限列表
        String token = TokenUtils.buildToken(claims);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        return ResponseVo.success(jsonObject);
    }
}
