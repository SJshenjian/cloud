package online.shenjian.cloud.api.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleInfoDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleInfoQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleTreeDto;
import online.shenjian.cloud.api.system.mapper.ModuleInfoMapper;
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

    private ModuleInfoMapper moduleInfoMapper;
    private RoleModuleMapper roleModuleMapper;

    public ModuleServiceImpl(ModuleInfoMapper moduleInfoMapper, RoleModuleMapper roleModuleMapper) {
        this.moduleInfoMapper = moduleInfoMapper;
        this.roleModuleMapper = roleModuleMapper;
    }

    @Override
    public List<ModuleTreeDto> initModuleInfoTree() {
        QueryWrapper<Module> queryWrapper = new QueryWrapper<>();
        List<Module> moduleList = moduleInfoMapper.selectList(queryWrapper);

        List<ModuleTreeDto> moduleTreeDtoList = TreeUtils.listModuleTree(moduleList, "-1");
        return moduleTreeDtoList;
    }

    @Override
    public IPage<ModuleInfoDto> listModule(ModuleInfoQueryDto moduleInfoQueryDto) {
        IPage<Module> page = new Page<>(moduleInfoQueryDto.getPageNumber(), moduleInfoQueryDto.getPageSize());
        QueryWrapper<Module> queryWrapper = new QueryWrapper<>();
        // 查询该组织机构下所有信息
        queryWrapper.likeRight("parent_id", moduleInfoQueryDto.getParentId());
        if (StringUtils.isNotBlank(moduleInfoQueryDto.getModuleName())) {
            queryWrapper.like("module_name", moduleInfoQueryDto.getModuleName());
        }
        queryWrapper.orderByAsc("sort_code");
        IPage<Module> iPage = moduleInfoMapper.selectPage(page, queryWrapper);
        List<ModuleInfoDto> patientDtoList = CommonDtoUtils.transformList(iPage.getRecords(), ModuleInfoDto.class);
        IPage<ModuleInfoDto> resultPage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        resultPage.setRecords(patientDtoList);
        return resultPage;
    }

    @Override
    public Boolean saveModule(ModuleInfoDto moduleInfoDto) {
        Module module = CommonDtoUtils.transform(moduleInfoDto, Module.class);
        module.setModuleId(IdUtil.getSnowflakeNextIdStr());
        moduleInfoMapper.insert(module);
        return true;
    }

    @Override
    public void deleteModule(String moduleId) {
        if (StringUtils.isBlank(moduleId)) {
            return ;
        }
        Module module = new Module();
        module.setModuleId(moduleId);
        moduleInfoMapper.deleteById(module);

        QueryWrapper<RoleModule> roleModuleQueryWrapper = new QueryWrapper<>();
        roleModuleQueryWrapper.eq("module_id", moduleId);
        roleModuleMapper.delete(roleModuleQueryWrapper);
    }

    @Override
    public Boolean updateModule(ModuleInfoDto moduleInfoDto) {
        if (StringUtils.isBlank(moduleInfoDto.getModuleId())) {
            return false;
        }
        Module module = CommonDtoUtils.transform(moduleInfoDto, Module.class);
        moduleInfoMapper.updateById(module);
        return true;
    }
}
