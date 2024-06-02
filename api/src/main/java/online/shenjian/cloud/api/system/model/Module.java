package online.shenjian.cloud.api.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/02
 */
@Schema(description = "菜单资源表")
@TableName(value = "sys_module")
@Data
public class Module {

    @Schema(description = "菜单ID")
    @TableId(type = IdType.INPUT)
    private String moduleId;

    @Schema(description = "菜单名")
    @TableField(value = "module_name")
    private String moduleName;

    @Schema(description = "菜单链接")
    @TableField(value = "module_url")
    private String moduleUrl;

    @Schema(description = "菜单编码")
    @TableField(value = "module_code")
    private String moduleCode;

    @Schema(description = "排序码")
    @TableField(value = "sort_code")
    private Integer sortCode;

    @Schema(description = "父菜单ID")
    @TableField(value = "parent_id")
    private String parentId;

    @Schema(description = "父菜单名")
    @TableField(value = "parent_name")
    private String parentName;
}
