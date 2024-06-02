package online.shenjian.cloud.client.cloud.dto.system.module;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/2
 */
@Schema(description = "菜单查询DTO")
@Data
public class ModuleQueryDto {

    @Schema(description = "父菜单ID")
    private String parentId;

    @Schema(description = "菜单ID")
    private String moduleId;

    @Schema(description = "菜单名称")
    private String moduleName;

    @Schema(description = "当前页")
    private Integer pageNumber = 1;

    @Schema(description = "页大小")
    private Integer pageSize = 10;
}
