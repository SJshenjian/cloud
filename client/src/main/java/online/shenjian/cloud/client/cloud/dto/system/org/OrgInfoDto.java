package online.shenjian.cloud.client.cloud.dto.system.org;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author shenjian
 * @since 2023/7/31
 */
@Schema(description = "机构信息DTO")
@Data
public class OrgInfoDto {

    @Schema(description = "父机构编码")
    private String parentCode;

    @Schema(description = "父机构名称")
    private String parentName;

    @Schema(description = "机构ID")
    private String orgId;

    @Schema(description = "机构编码")
    private String orgCode;

    @Schema(description = "机构名称")
    private String orgName;

    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "排序码")
    private Integer sortCode;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Schema(description = "创建人")
    private String createUser;
}
