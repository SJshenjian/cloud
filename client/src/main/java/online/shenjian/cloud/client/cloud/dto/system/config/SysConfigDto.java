package online.shenjian.cloud.client.cloud.dto.system.config;

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
@Schema(description = "系统配置DTO")
public class SysConfigDto implements Serializable {

    @Schema(description = "配置ID")
    private String id;

    @Schema(description = "配置编号")
    private String code;

    @Schema(description = "机构编码:比如报告名,不同机构不同名称")
    private String orgCode;

    @Schema(description = "机构名称")
    private String orgName;

    @Schema(description = "变量分类")
    private String type;

    @Schema(description = "参数名称")
    private String name;

    @Schema(description = "参数值")
    private String value;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序码")
    private Integer sortCode;

    @Schema(description = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @Schema(description = "修改人")
    private String updateUser;
}
