package online.shenjian.cloud.api.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleInfoDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleInfoQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleTreeDto;

import java.util.List;

/**
 * 菜单服务
 *
 * @author shenjian
 * @since 2023/8/1
 */
public interface ModuleService {

    List<ModuleTreeDto> initModuleInfoTree();

    IPage<ModuleInfoDto> listModule(ModuleInfoQueryDto moduleInfoQueryDto);

    Boolean saveModule(ModuleInfoDto moduleInfoDto);

    void deleteModule(String moduleId);

    Boolean updateModule(ModuleInfoDto moduleInfoDto);
}
