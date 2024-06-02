package online.shenjian.cloud.api.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户加强版Mapper
 *
 * @author shenjian
 * @since 2023/8/10
 */
@Repository
public interface UserPlusMapper extends BaseMapper<UserDto> {

    /**
     * 直接写SQL即可，即使关联查询的结果也会映射到DTO中
     */
    String querySql = "SELECT " +
            "               u.user_id, u.username, u.account, u.role_id, u.department, u.title, u.employee_number, u.state, u.phone_number, u.del_flag, u.org_code, u.create_time, o.org_name, r.role_name " +
            "          FROM " +
            "               usr_user AS u " +
            "               LEFT JOIN sys_org AS o ON u.org_code = o.org_code" +
            "               LEFT JOIN sys_role AS r ON u.role_id = r.role_id ";
    String wrapperSql = "SELECT * FROM ( " + querySql + " ) AS q ${ew.customSqlSegment}";

    /**
     * 分页查询
     */
    @Select(wrapperSql)
    Page<UserDto> page(IPage page, @Param("ew") Wrapper queryWrapper);
}
