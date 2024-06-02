package online.shenjian.cloud.api.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleTreeDto;
import online.shenjian.cloud.api.system.mapper.ModuleMapper;
import online.shenjian.cloud.api.system.mapper.RoleModuleMapper;
import online.shenjian.cloud.api.system.model.Module;
import online.shenjian.cloud.api.system.model.RoleModule;
import online.shenjian.cloud.api.system.service.ModuleService;
import online.shenjian.cloud.api.utils.TreeUtils;
import io.micrometer.common.util.StringUtils;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/1
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    private ModuleMapper moduleMapper;
    private RoleModuleMapper roleModuleMapper;

    public ModuleServiceImpl(ModuleMapper moduleMapper, RoleModuleMapper roleModuleMapper) {
        this.moduleMapper = moduleMapper;
        this.roleModuleMapper = roleModuleMapper;
    }

    @Override
    public List<ModuleTreeDto> initModuleInfoTree() {
        QueryWrapper<Module> queryWrapper = new QueryWrapper<>();
        List<Module> moduleList = moduleMapper.selectList(queryWrapper);

        List<ModuleTreeDto> moduleTreeDtoList = TreeUtils.listModuleTree(moduleList, "-1");
        return moduleTreeDtoList;
    }

    @Override
    public IPage<ModuleDto> listModule(ModuleQueryDto moduleQueryDto) {
        IPage<Module> page = new Page<>(moduleQueryDto.getPageNumber(), moduleQueryDto.getPageSize());
        QueryWrapper<Module> queryWrapper = new QueryWrapper<>();
        // 查询该组织机构下所有信息
        queryWrapper.likeRight("parent_id", moduleQueryDto.getParentId());
        if (StringUtils.isNotBlank(moduleQueryDto.getModuleName())) {
            queryWrapper.like("module_name", moduleQueryDto.getModuleName());
        }
        queryWrapper.orderByAsc("sort_code");
        IPage<Module> iPage = moduleMapper.selectPage(page, queryWrapper);
        List<ModuleDto> patientDtoList = CommonDtoUtils.transformList(iPage.getRecords(), ModuleDto.class);
        IPage<ModuleDto> resultPage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        resultPage.setRecords(patientDtoList);
        return resultPage;
    }

    @Override
    public Boolean saveModule(ModuleDto moduleDto) {
        Module module = CommonDtoUtils.transform(moduleDto, Module.class);
        module.setModuleId(IdUtil.getSnowflakeNextIdStr());
        moduleMapper.insert(module);
        return true;
    }

    @Override
    public void deleteModule(String moduleId) {
        if (StringUtils.isBlank(moduleId)) {
            return ;
        }
        Module module = new Module();
        module.setModuleId(moduleId);
        moduleMapper.deleteById(module);

        QueryWrapper<RoleModule> roleModuleQueryWrapper = new QueryWrapper<>();
        roleModuleQueryWrapper.eq("module_id", moduleId);
        roleModuleMapper.delete(roleModuleQueryWrapper);
    }

    @Override
    public Boolean updateModule(ModuleDto moduleDto) {
        if (StringUtils.isBlank(moduleDto.getModuleId())) {
            return false;
        }
        Module module = CommonDtoUtils.transform(moduleDto, Module.class);
        moduleMapper.updateById(module);
        return true;
    }
}
