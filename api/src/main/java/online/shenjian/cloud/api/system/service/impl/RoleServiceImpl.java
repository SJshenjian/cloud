package online.shenjian.cloud.api.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.client.common.ResponseVo;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleQueryDto;
import online.shenjian.cloud.api.config.security.model.Claims;
import online.shenjian.cloud.api.system.mapper.RoleMapper;
import online.shenjian.cloud.api.system.mapper.RoleModuleMapper;
import online.shenjian.cloud.api.system.model.Role;
import online.shenjian.cloud.api.system.model.RoleModule;
import online.shenjian.cloud.api.system.service.RoleService;
import online.shenjian.cloud.api.utils.TokenUtils;
import io.micrometer.common.util.StringUtils;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleMapper roleMapper;
    private RoleModuleMapper roleModuleMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleModuleMapper roleModuleMapper) {
        this.roleMapper = roleMapper;
        this.roleModuleMapper = roleModuleMapper;
    }

    @Override
    public IPage<RoleDto> listRole(RoleQueryDto roleQueryDto) {
        IPage<Role> page = new Page<>(roleQueryDto.getPageNumber(), roleQueryDto.getPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(roleQueryDto.getRoleName())) {
            queryWrapper.like("role_name", roleQueryDto.getRoleName());
        }
        // 防止非超级管理员用户新增时增加该角色
        Claims claims = TokenUtils.getClaimsFromToken();
        if (claims.getRoles() != null && !claims.getRoles().contains("super-admin")) {
            queryWrapper.notLike("role_id", "super-admin");
        }
        IPage<Role> iPage = roleMapper.selectPage(page, queryWrapper);
        List<RoleDto> patientDtoList = CommonDtoUtils.transformList(iPage.getRecords(), RoleDto.class);
        IPage<RoleDto> resultPage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        resultPage.setRecords(patientDtoList);
        return resultPage;
    }

    @Override
    public ResponseVo saveRole(RoleDto roleDto) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleDto.getRoleId()).or().eq("role_name", roleDto.getRoleName());
        Role dbRole = roleMapper.selectOne(queryWrapper);
        if (dbRole != null) {
            if (dbRole.getRoleId().equals(roleDto.getRoleId())) {
                return ResponseVo.error("角色编码重复!");
            }
            if (dbRole.getRoleName().equals(roleDto.getRoleName())) {
                return ResponseVo.error("角色名称重复!");
            }
        } else {
            Role role = CommonDtoUtils.transform(roleDto, Role.class);
            roleMapper.insert(role);
        }
        return ResponseVo.success("新增成功!");
    }

    @Override
    public void deleteRole(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return ;
        }
        // 删除角色表中对应数据
        roleMapper.deleteById(roleId);
        // 删除角色菜单关联表中对应数据
        QueryWrapper<RoleModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleModuleMapper.delete(queryWrapper);
    }

    @Override
    public Boolean updateRole(RoleDto roleDto) {
        if (StringUtils.isBlank(roleDto.getRoleId())) {
            return false;
        }
        Role role = CommonDtoUtils.transform(roleDto, Role.class);
        roleMapper.updateById(role);
        return true;
    }
}
