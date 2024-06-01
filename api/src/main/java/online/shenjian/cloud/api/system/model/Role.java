package online.shenjian.cloud.api.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Schema(description = "角色表")
@TableName(value = "sys_role")
@Data
public class Role {

    @Schema(description = "角色ID")
    @TableId(type = IdType.INPUT)
    private String roleId;

    @Schema(description = "角色名")
    @TableField(value = "role_name")
    private String roleName;

    @Schema(description = "备注")
    @TableField(value = "remark")
    private String remark;
}
