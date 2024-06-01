package online.shenjian.cloud.client.cloud.dto.system.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Schema(description = "角色菜单DTO")
@Data
public class RoleModuleDto {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "菜单ID")
    private String[] moduleIds;
}
