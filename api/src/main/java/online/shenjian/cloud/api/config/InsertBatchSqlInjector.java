package online.shenjian.cloud.api.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 自定义批量插入 SQL 注入器,Mybatis-Plus自带的saveBatch为伪批量插入
 *
 * @author shenjian
 * @since 2023/08/15
 */
public class InsertBatchSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
    	// super.getMethodList() 保留 Mybatis Plus 自带的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 添加自定义方法：批量插入，方法名为 insertBatchSomeColumn
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}