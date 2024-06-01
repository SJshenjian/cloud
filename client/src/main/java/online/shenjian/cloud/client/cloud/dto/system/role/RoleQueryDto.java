package online.shenjian.cloud.client.cloud.dto.system.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Schema(description = "角色查询DTO")
@Data
public class RoleQueryDto {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "角色名")
    private String roleName;

    @Schema(description = "当前页")
    private Integer pageNumber = 1;

    @Schema(description = "页大小")
    private Integer pageSize = 10;
}
