package online.shenjian.cloud.api.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import online.shenjian.cloud.common.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author shenjian
 * @since 2023-08-25
 */
@Data
@TableName("usr_user")
@Schema(description = "用户表")
public class User implements Serializable {

    @Schema(description = "用户ID")
    @TableId(type = IdType.INPUT)
    private String userId;

    @Schema(description = "用户名")
    @TableField(value = "username")
    private String username;

    @Schema(description = "登录账号")
    @TableField(value = "account")
    private String account;

    @Schema(description = "密码")
    @TableField(value = "password")
    private String password;

    @Schema(description = "机构编码")
    @TableField(value = "org_code")
    private String orgCode;

    @Schema(description = "部门")
    @TableField(value = "department")
    private String department;

    @Schema(description = "角色ID")
    @TableField(value = "role_id")
    private String roleId;

    @Schema(description = "职称")
    @TableField(value = "title")
    private String title;

    @Schema(description = "工号")
    @TableField(value = "employee_number")
    private String employeeNumber;

    @Schema(description = "状态 0启用 1禁用")
    @TableField(value = "state")
    private String state;

    @Schema(description = "手机号")
    @TableField(value = "phone_number")
    private String phoneNumber;

    @Schema(description = "性别 0男 1女")
    @TableField(value = "gender")
    private String gender;

    @Schema(description = "删除标记 0正常 1删除")
    @TableField(value = "del_flag")
    private String delFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateUtils.DATETIME_FORMAT)
    @TableField(value = "create_time")
    private Date createTime;

    @Schema(description = "创建人")
    @TableField(value = "create_user")
    private String createUser;

    @Schema(description = "修改时间")
    @DateTimeFormat(pattern = DateUtils.DATETIME_FORMAT)
    @TableField(value = "update_time")
    private Date updateTime;

    @Schema(description = "修改人")
    @TableField(value = "update_user")
    private String updateUser;
}
