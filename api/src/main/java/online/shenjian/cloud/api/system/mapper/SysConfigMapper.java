package online.shenjian.cloud.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.shenjian.cloud.api.system.model.Config;
import org.springframework.stereotype.Repository;

/**
 * 系统配置表 Mapper 接口
 *
 * @author shenjian
 * @since 2023-08-22
 */
@Repository
public interface SysConfigMapper extends BaseMapper<Config> {

}
