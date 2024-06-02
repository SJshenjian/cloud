package online.shenjian.cloud.client.cloud.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/7/13
 */
@Schema(description = "用户信息DTO")
@Data
public class UserDto {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录账号")
    private String account;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "机构编码")
    private String orgCode;

    @Schema(description = "机构名称")
    private String orgName;

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "用户角色")
    private String roleName;

    @Schema(description = "状态 0启用 1禁用")
    private String state;
}
