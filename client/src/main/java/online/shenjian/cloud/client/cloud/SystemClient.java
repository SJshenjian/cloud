package online.shenjian.cloud.client.cloud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigDto;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleTreeDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgTreeDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleModuleDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleQueryDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@FeignClient(value = "system", contextId = "system")
@Component
public interface SystemClient {

    @PostMapping(value = "/org/initOrgInfoTree", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "机构初始化下拉树形", tags = "系统管理")
    List<OrgTreeDto> initOrgInfoTree();

    @PostMapping(value = "/org/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增机构", tags = "系统管理")
    Boolean saveOrg(@RequestBody OrgInfoDto orgInfoDto);

    @GetMapping(value ="/org/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除机构", tags = "系统管理")
    @Parameters(
            @Parameter(name = "orgId", description = "多个由,分割开的id字符串", required = true)
    )
    void deleteOrg(@RequestParam(value = "orgId") String orgId);

    @PostMapping(value = "/org/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "机构列表", tags = "系统管理")
    IPage<OrgInfoDto> listOrg(@RequestBody OrgInfoQueryDto orgInfoQueryDto);

    @PostMapping(value = "/org/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "机构修改", tags = "系统管理")
    Boolean updateOrg(@RequestBody OrgInfoDto orgInfoDto);

    @PostMapping(value = "/module/initModuleInfoTree", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "菜单初始化下拉树形", tags = "系统管理")
    List<ModuleTreeDto> initModuleInfoTree();

    @PostMapping(value = "/module/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增菜单", tags = "系统管理")
    Boolean saveModule(@RequestBody ModuleDto moduleDto);

    @GetMapping(value ="/module/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除菜单", tags = "系统管理")
    @Parameters(
            @Parameter(name = "moduleId", description = "多个由,分割开的id字符串", required = true)
    )
    void deleteModule(@RequestParam(value = "moduleId") String moduleId);

    @PostMapping(value = "/module/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "菜单列表", tags = "系统管理")
    IPage<ModuleDto> listModule(@RequestBody ModuleQueryDto moduleQueryDto);

    @PostMapping(value = "/module/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "菜单修改", tags = "系统管理")
    Boolean updateModule(@RequestBody ModuleDto moduleDto);

    @PostMapping(value = "/role/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增角色", tags = "系统管理")
    ResponseVo saveRole(@RequestBody RoleDto roleDto);

    @GetMapping(value ="/role/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除角色", tags = "系统管理")
    @Parameters(
            @Parameter(name = "roleId", description = "多个由,分割开的id字符串", required = true)
    )
    void deleteRole(@RequestParam(value = "roleId") String roleId);

    @PostMapping(value = "/role/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "角色列表", tags = "系统管理")
    IPage<RoleDto> listRole(@RequestBody RoleQueryDto roleQueryDto);

    @PostMapping(value = "/role/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "角色修改", tags = "系统管理")
    Boolean updateRole(@RequestBody RoleDto roleDto);

    @PostMapping(value = "/role/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "角色授权", tags = "系统管理")
    Boolean authRole(@RequestBody RoleModuleDto roleModuleDto);

    @GetMapping(value = "/role/getRoleModule", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取角色菜单信息", tags = "系统管理")
    @Parameters(
            @Parameter(name = "roleId", description = "角色ID", required = true)
    )
    String[] getRoleModule(@RequestParam(value = "roleId") String roleId);

    @PostMapping(value = "/config/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增配置", tags = "系统管理")
    Boolean saveConfig(@RequestBody SysConfigDto sysConfigDto);

    @GetMapping(value ="/config/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除配置", tags = "系统管理")
    @Parameters(
            @Parameter(name = "id", description = "多个由,分割开的id字符串", required = true)
    )
    void deleteConfig(@RequestParam(value = "id") String id);

    @PostMapping(value = "/config/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "配置列表", tags = "系统管理")
    IPage<SysConfigDto> listConfig(@RequestBody SysConfigQueryDto sysConfigQueryDto);

    @PostMapping(value = "/config/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取配置", tags = "系统管理")
    SysConfigDto getConfig(@RequestBody SysConfigDto sysConfigDto);

    @PostMapping(value = "/config/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "配置修改", tags = "系统管理")
    Boolean updateConfig(@RequestBody SysConfigDto sysConfigDto);

    @PostMapping(value = "/file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "文件上传", tags = "系统管理")
    ResponseVo uploadFile(@RequestParam MultipartFile file) throws IOException;
}