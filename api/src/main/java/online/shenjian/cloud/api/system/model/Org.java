package online.shenjian.cloud.api.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import online.shenjian.cloud.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author shenjian
 * @since 2023/7/31
 */
@Schema(description = "机构表")
@TableName(value = "sys_org")
@Data
public class Org {

    @Schema(description = "机构ID")
    @TableId(type = IdType.INPUT)
    private String orgId;

    @Schema(description = "机构编码")
    @TableField(value = "org_code")
    private String orgCode;

    @Schema(description = "机构名称")
    @TableField(value = "org_name")
    private String orgName;

    @Schema(description = "父机构ID")
    @TableField(value = "parent_id")
    private String parentId;

    @Schema(description = "父机构编码")
    @TableField(value = "parent_code")
    private String parentCode;

    @Schema(description = "父机构名称")
    @TableField(value = "parent_name")
    private String parentName;

    @Schema(description = "排序码")
    @TableField(value = "sort_code")
    private Integer sortCode;

    @Schema(description = "备注")
    @TableField(value = "remark")
    private String remark;

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
