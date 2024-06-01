package online.shenjian.cloud.api.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgTreeDto;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/1
 */
public interface OrgService {

    List<OrgTreeDto> initOrgInfoTree();

    IPage<OrgInfoDto> listOrg(OrgInfoQueryDto orgInfoQueryDto);

    Boolean saveOrg(OrgInfoDto orgInfoDto);

    void deleteOrg(String orgId);

    Boolean updateOrg(OrgInfoDto orgInfoDto);
}
