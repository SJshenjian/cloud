package online.shenjian.cloud.api.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgTreeDto;
import online.shenjian.cloud.common.enums.Constant;
import online.shenjian.cloud.api.config.security.model.Claims;
import online.shenjian.cloud.api.system.mapper.OrgMapper;
import online.shenjian.cloud.api.system.model.Org;
import online.shenjian.cloud.api.system.service.OrgService;
import online.shenjian.cloud.api.utils.TokenUtils;
import online.shenjian.cloud.api.utils.TreeUtils;
import io.micrometer.common.util.StringUtils;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/1
 */
@Service
public class OrgServiceImpl implements OrgService {

    private OrgMapper orgMapper;

    public OrgServiceImpl(OrgMapper orgMapper) {
        this.orgMapper = orgMapper;
    }

    @Override
    public List<OrgTreeDto> initOrgInfoTree() {
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        Claims claims = TokenUtils.getClaimsFromToken();
        // 查询该组织机构下所有信息
        final String orgCode = claims.getOrgCode();
        queryWrapper.likeRight("org_code", orgCode);
        List<Org> orgList = orgMapper.selectList(queryWrapper);

        List<OrgTreeDto> orgTreeDtoList = TreeUtils.listOrgTree(orgList, orgCode);

        return orgTreeDtoList;
    }

    @Override
    public IPage<OrgInfoDto> listOrg(OrgInfoQueryDto orgInfoQueryDto) {
        IPage<Org> page = new Page<>(orgInfoQueryDto.getPageNumber(), orgInfoQueryDto.getPageSize());
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        // 查询该组织机构下所有信息
        queryWrapper.likeRight("org_code", orgInfoQueryDto.getOrgCode());
        if (StringUtils.isNotBlank(orgInfoQueryDto.getOrgName())) {
            queryWrapper.like("org_name", orgInfoQueryDto.getOrgName());
        }
        IPage<Org> iPage = orgMapper.selectPage(page, queryWrapper);
        List<OrgInfoDto> orgDtoList = CommonDtoUtils.transformList(iPage.getRecords(), OrgInfoDto.class);
        IPage<OrgInfoDto> resultPage = new Page<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        resultPage.setRecords(orgDtoList);
        return resultPage;
    }

    @Override
    public Boolean saveOrg(OrgInfoDto orgInfoDto) {
        String nextCode;
        String maxCode = orgMapper.getMaxCode(orgInfoDto.getParentCode());
        if (StringUtils.isBlank(maxCode)) {
            nextCode = orgInfoDto.getParentCode() + ".001";
        } else {
            String maxCodeEnd = maxCode.substring(maxCode.length() - 3, maxCode.length());
            String maxCodePre;
            // 有小数点
            if (maxCode.indexOf(".") > -1) {
                maxCodePre = maxCode.substring(0, maxCode.lastIndexOf(".") + 1);
            } else {
                maxCodePre = maxCode.substring(0, 0);
            }
            String endStr = Integer.valueOf(maxCodeEnd) + 1 + "";
            if (endStr.length() == 1) {
                endStr = "00" + endStr;
            }
            if (endStr.length() == 2) {
                endStr = "0" + endStr;
            }
            nextCode = maxCodePre + endStr;
        }
        Org org = CommonDtoUtils.transform(orgInfoDto, Org.class);
        org.setOrgId(IdUtil.getSnowflakeNextIdStr());
        org.setOrgCode(nextCode);
        org.setDelFlag(Constant.YesOrNo.NO.val());
        org.setCreateTime(new Date());
        Claims claims = TokenUtils.getClaimsFromToken();
        org.setCreateUser(claims.getAccount());
        orgMapper.insert(org);
        return true;
    }

    @Override
    public void deleteOrg(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            return ;
        }
        Org org = new Org();
        org.setOrgId(orgId);
        org.setDelFlag(Constant.YesOrNo.YES.val());
        org.setUpdateTime(new Date());
        org.setUpdateUser(TokenUtils.getClaimsFromToken().getAccount());
        orgMapper.updateById(org);
    }

    @Override
    public Boolean updateOrg(OrgInfoDto orgInfoDto) {
        if (StringUtils.isBlank(orgInfoDto.getOrgId())) {
            return false;
        }
        Org org = CommonDtoUtils.transform(orgInfoDto, Org.class);
        org.setUpdateUser(TokenUtils.getClaimsFromToken().getAccount());
        org.setUpdateTime(new Date());
        orgMapper.updateById(org);
        return true;
    }
}
