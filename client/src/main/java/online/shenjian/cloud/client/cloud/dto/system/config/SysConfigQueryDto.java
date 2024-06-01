package online.shenjian.cloud.client.cloud.dto.system.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shenjian
 * @since 2023/8/2
 */
@Schema(description = "配置查询DTO")
@Data
public class SysConfigQueryDto {

    @Schema(description = "配置编号")
    private String code;

    @Schema(description = "参数名称")
    private String name;

    @Schema(description = "当前页")
    private Integer pageNumber = 1;

    @Schema(description = "页大小")
    private Integer pageSize = 10;
}
