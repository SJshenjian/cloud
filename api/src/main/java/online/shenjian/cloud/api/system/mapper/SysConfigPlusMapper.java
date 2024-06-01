package online.shenjian.cloud.api.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 系统配置加强版Mapper
 *
 * @author shenjian
 * @since 2023/8/23
 */
@Repository
public interface SysConfigPlusMapper extends BaseMapper<SysConfigDto> {

    String querySql = "SELECT " +
            "               c.id, c.code, c.name, c.value, c.remark, c.sort_code, c.update_time, o.org_name " +
            "          FROM " +
            "               tb_sys_config AS c " +
            "               LEFT JOIN tb_sys_org_info AS o ON c.org_code = o.org_code";
    String wrapperSql = "SELECT * FROM ( " + querySql + " ) AS q ${ew.customSqlSegment}";

    /**
     * 分页查询
     */
    @Select(wrapperSql)
    IPage<SysConfigDto> page(IPage<SysConfigDto> page, @Param("ew") Wrapper queryWrapper);
}
