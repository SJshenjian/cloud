package online.shenjian.cloud.api.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单信息加强版Mapper
 *
 * @author shenjian
 * @since 2023/8/10
 */
@Repository
public interface ModulePlusMapper extends BaseMapper<ModuleDto> {

    String querySql = "SELECT " +
            "               m.module_name, m.module_code, rm.role_id " +
            "          FROM " +
            "               sys_module AS m " +
            "               LEFT JOIN sys_role_module AS rm ON m.module_id = rm.module_id";
    String wrapperSql = "SELECT * FROM ( " + querySql + " ) AS q ${ew.customSqlSegment}";

    @Select(wrapperSql)
    List<ModuleDto> list(@Param("ew") Wrapper queryWrapper);
}
