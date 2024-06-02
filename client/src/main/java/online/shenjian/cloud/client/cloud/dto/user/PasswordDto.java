package online.shenjian.cloud.client.cloud.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/7/31
 */
@Schema(description = "密码修改DTO")
@Data
public class PasswordDto {

    @Schema(description = "原密码")
    private String originPassword;

    @Schema(description = "新密码")
    private String newPassword;
}
