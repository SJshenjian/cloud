package online.shenjian.cloud.client.cloud.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/9
 */
@Schema(description = "用户查询DTO")
@Data
public class UserQueryDto {

    @Schema(description = "机构编码")
    private String orgCode;

    @Schema(description = "用户姓名")
    private String username;

    @Schema(description = "登录帐号")
    private String account;

    @Schema(description = "当前页")
    private Integer pageNumber = 1;

    @Schema(description = "页大小")
    private Integer pageSize = 10;
}
