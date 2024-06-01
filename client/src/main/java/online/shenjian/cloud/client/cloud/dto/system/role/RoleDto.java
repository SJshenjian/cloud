package online.shenjian.cloud.client.cloud.dto.system.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/7
 */
@Schema(description = "角色DTO")
@Data
public class RoleDto {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "角色名")
    private String roleName;

    @Schema(description = "备注")
    private String remark;
}
