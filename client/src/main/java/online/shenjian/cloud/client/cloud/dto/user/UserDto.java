package online.shenjian.cloud.client.cloud.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

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

    @Schema(description = "部门")
    private String department;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "工号")
    private String employeeNumber;

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "用户角色")
    private String roleName;

    @Schema(description = "手机号")
    private String phoneNumber;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "性别")
    private Integer age;

    @Schema(description = "状态 0启用 1禁用")
    private String state;

    @Schema(description = "删除标记")
    private String delFlag;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "创建人")
    private String createUser;

    @Schema(description = "出生年月")
    private Date birthday;

    @Schema(description = "地区")
    private String region;

    @Schema(description = "修改人")
    private String updateUser;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "备注")
    private String remark;
}
