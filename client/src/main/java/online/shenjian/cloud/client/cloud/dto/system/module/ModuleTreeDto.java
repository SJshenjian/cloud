package online.shenjian.cloud.client.cloud.dto.system.module;

import lombok.Data;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/2
 */
@Data
public class ModuleTreeDto {

    private String moduleId;

    private String label;

    private List<ModuleTreeDto> children;
}
