package online.shenjian.cloud.api.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigDto;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigQueryDto;
import online.shenjian.cloud.api.system.model.Config;

/**
 * 系统配置表 服务类
 *
 * @author shenjian
 * @since 2023-08-22
 */
public interface SysConfigService extends IService<Config> {

    Boolean saveConfig(SysConfigDto sysConfigDto);

    void deleteConfig(String id);

    IPage<SysConfigDto> listConfig(SysConfigQueryDto sysConfigQueryDto);

    Boolean updateConfig(SysConfigDto sysConfigDto);

    SysConfigDto getConfig(SysConfigDto sysConfigDto);
}
