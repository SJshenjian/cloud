package online.shenjian.cloud.api.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import online.shenjian.cloud.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统配置表
 *
 * @author shenjian
 * @since 2023-08-22
 */
@Data
@TableName("sys_config")
@Schema(description = "系统配置表")
public class Config implements Serializable {

    @Schema(description = "配置ID")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @Schema(description = "配置编号")
    @TableField("code")
    private String code;

    @Schema(description = "机构编码")
    @TableField("org_code")
    private String orgCode;

    @Schema(description = "参数名称")
    @TableField("name")
    private String name;

    @Schema(description = "参数值")
    @TableField("value")
    private String value;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "排序码")
    @TableField("sort_code")
    private Integer sortCode;

    @Schema(description = "修改时间")
    @DateTimeFormat(pattern = DateUtils.DATETIME_FORMAT)
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "修改人")
    @TableField("update_user")
    private String updateUser;
}
