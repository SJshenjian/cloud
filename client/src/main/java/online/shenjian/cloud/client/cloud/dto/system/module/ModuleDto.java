package online.shenjian.cloud.client.cloud.dto.system.module;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/2
 */
@Schema(description = "菜单信息DTO")
@Data
public class ModuleDto {

    @Schema(description = "父菜单ID")
    private String parentId;

    @Schema(description = "父菜单名称")
    private String parentName;

    @Schema(description = "菜单ID")
    private String moduleId;

    @Schema(description = "菜单链接")
    private String moduleUrl;

    @Schema(description = "菜单编码")
    private String moduleCode;

    @Schema(description = "菜单名称")
    private String moduleName;

    @Schema(description = "排序码")
    private Integer sortCode;

    @Schema(description = "角色ID")
    private String roleId;
}
