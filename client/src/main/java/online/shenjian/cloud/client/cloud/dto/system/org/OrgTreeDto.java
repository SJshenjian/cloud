package online.shenjian.cloud.client.cloud.dto.system.org;

import lombok.Data;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/1
 */
@Data
public class OrgTreeDto {

    private String orgCode;

    private String label;

    private List<OrgTreeDto> children;
}
