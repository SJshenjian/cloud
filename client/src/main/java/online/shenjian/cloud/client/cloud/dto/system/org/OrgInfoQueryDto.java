package online.shenjian.cloud.client.cloud.dto.system.org;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/7/27
 */
@Schema(description = "机构查询DTO")
@Data
public class OrgInfoQueryDto {

    @Schema(description = "机构编码")
    private String orgCode;

    @Schema(description = "机构名称")
    private String orgName;

    @Schema(description = "当前页")
    private Integer pageNumber = 1;

    @Schema(description = "页大小")
    private Integer pageSize = 10;
}
