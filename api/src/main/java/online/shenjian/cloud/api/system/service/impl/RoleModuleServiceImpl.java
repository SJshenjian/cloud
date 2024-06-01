package online.shenjian.cloud.api.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleModuleDto;
import online.shenjian.cloud.api.system.mapper.RoleModuleMapper;
import online.shenjian.cloud.api.system.model.RoleModule;
import online.shenjian.cloud.api.system.service.RoleModuleService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Service
public class RoleModuleServiceImpl  extends ServiceImpl<RoleModuleMapper, RoleModule> implements RoleModuleService {


    private RoleModuleMapper roleModuleMapper;

    public RoleModuleServiceImpl(RoleModuleMapper roleModuleMapper) {
        this.roleModuleMapper = roleModuleMapper;
    }

    /**
     * 角色授权
     *
     * @param roleModuleDto
     * @return
     */
    @Override
    public Boolean authRole(RoleModuleDto roleModuleDto) {
        final String roleId = (roleModuleDto.getRoleId());
        if (StringUtils.isBlank(roleId)) {
            return false;
        }
        QueryWrapper<RoleModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleModuleMapper.delete(queryWrapper);
        List<RoleModule> roleModules = new ArrayList<>();
        for (String moduleId : roleModuleDto.getModuleIds()) {
            roleModules.add(new RoleModule(roleId, moduleId));
        }
        this.saveBatch(roleModules);
        return true;
    }

    /**
     * 获取角色菜单信息
     *
     * @param roleId
     * @return
     */
    @Override
    public String[] getRoleModule(String roleId) {
        QueryWrapper<RoleModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RoleModule> result = roleModuleMapper.selectList(queryWrapper);
        List<String> menuIds = result.stream().map(RoleModule::getModuleId).collect(Collectors.toList());
        return menuIds.toArray(new String[menuIds.size()]);
    }
}
