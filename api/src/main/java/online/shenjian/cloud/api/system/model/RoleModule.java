package online.shenjian.cloud.api.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Schema(description = "角色与菜单资源关系表")
@TableName(value = "sys_role_module")
@Data
public class RoleModule {

    @Schema(description = "角色ID")
    @TableField(value = "role_id")
    private String roleId;

    @Schema(description = "角色名")
    @TableField(value = "module_id")
    private String moduleId;

    public RoleModule() {}

    public RoleModule(String roleId, String moduleId) {
        this.roleId = roleId;
        this.moduleId = moduleId;
    }
}
