package online.shenjian.cloud.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.shenjian.cloud.api.system.model.Org;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 组织机构Mapper
 *
 * @author shenjian
 * @since 2023/8/1
 */
@Repository
public interface OrgMapper extends BaseMapper<Org> {

    /**
     * 根据上级编码查找最大编码
     *
     * @param orgCode 上级编码
     * @return
     */
    @Select("SELECT MAX(oi.org_code) FROM sys_org oi WHERE 1=1 AND oi.parent_code=#{orgCode} ")
    String getMaxCode(String orgCode);
}
