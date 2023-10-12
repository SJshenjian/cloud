package online.shenjian.cloud.api.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBaseMapper支持高效批量插入
 *
 * @author shenjian
 * @since 2023/08/15
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param batchList
     * @return
     */
    int insertBatchSomeColumn(@Param("list") List<T> batchList);
}