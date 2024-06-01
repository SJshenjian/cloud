package online.shenjian.cloud.api.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.api.system.mapper.SysConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigDto;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigQueryDto;
import online.shenjian.cloud.api.config.security.model.Claims;
import online.shenjian.cloud.api.system.mapper.SysConfigPlusMapper;
import online.shenjian.cloud.api.system.model.SysConfig;
import online.shenjian.cloud.api.system.service.SysConfigService;
import online.shenjian.cloud.api.utils.TokenUtils;
import io.micrometer.common.util.StringUtils;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 系统配置表 服务实现类
 *
 * @author shenjian
 * @since 2023-08-22
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private SysConfigMapper sysConfigMapper;
    private SysConfigPlusMapper sysConfigPlusMapper;

    public SysConfigServiceImpl(SysConfigMapper sysConfigMapper, SysConfigPlusMapper sysConfigPlusMapper) {
        this.sysConfigMapper = sysConfigMapper;
        this.sysConfigPlusMapper = sysConfigPlusMapper;
    }

    @Override
    public Boolean saveConfig(SysConfigDto sysConfigDto) {
        SysConfig sysConfig = CommonDtoUtils.transform(sysConfigDto, SysConfig.class);
        if (StringUtils.isBlank(sysConfigDto.getCode())) {
            return false;
        }
        sysConfig.setId(IdUtil.getSnowflakeNextIdStr());
        sysConfig.setUpdateTime(new Date());
        Claims claims = TokenUtils.getClaimsFromToken();
        sysConfig.setUpdateUser(claims.getAccount());
        sysConfigMapper.insert(sysConfig);
        return true;
    }

    @Override
    public void deleteConfig(String id) {
        if (StringUtils.isBlank(id)) {
            return ;
        }
        sysConfigMapper.deleteById(id);
    }

    @Override
    public IPage<SysConfigDto> listConfig(SysConfigQueryDto queryDto) {
        IPage<SysConfigDto> page = new Page<>(queryDto.getPageNumber(), queryDto.getPageSize());
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryDto.getCode())) {
            queryWrapper.like("code", queryDto.getCode());
        }
        if (StringUtils.isNotBlank(queryDto.getName())) {
            queryWrapper.like("name", queryDto.getName());
        }
        IPage<SysConfigDto> resultPage = sysConfigPlusMapper.page(page, queryWrapper);
        return resultPage;
    }

    @Override
    public Boolean updateConfig(SysConfigDto sysConfigDto) {
        if (StringUtils.isBlank(sysConfigDto.getId())) {
            return false;
        }
        SysConfig sysConfig = CommonDtoUtils.transform(sysConfigDto, SysConfig.class);
        sysConfig.setUpdateUser(TokenUtils.getClaimsFromToken().getAccount());
        sysConfig.setUpdateTime(new Date());
        sysConfigMapper.updateById(sysConfig);
        return true;
    }

    @Override
    public SysConfigDto getConfig(SysConfigDto sysConfigDto) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sysConfigDto.getCode())) {
            queryWrapper.eq("code", sysConfigDto.getCode());
        }
        if (StringUtils.isNotBlank(sysConfigDto.getOrgCode())) {
            queryWrapper.eq("org_code", sysConfigDto.getOrgCode());
        }
        queryWrapper.last("LIMIT 1");
        SysConfig sysConfig = sysConfigMapper.selectOne(queryWrapper);
        SysConfigDto result = CommonDtoUtils.transform(sysConfig, SysConfigDto.class);
        return result;
    }
}
