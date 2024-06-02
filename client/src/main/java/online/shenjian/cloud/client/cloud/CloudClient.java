package online.shenjian.cloud.client.cloud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.cloud.dto.user.PasswordDto;
import online.shenjian.cloud.client.cloud.dto.user.UserQueryDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud", contextId = "cloud")
@Component
public interface CloudClient {

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "登录", tags = "用户管理")
    ResponseVo login(@RequestBody UserDto userDto);

    @PostMapping(value = "/user/updatePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改密码", tags = "用户管理")
    ResponseVo<String> updatePassword(@RequestBody PasswordDto passwordDto);

    @PostMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "用户列表", tags = "用户管理")
    IPage<UserDto> listUser(@RequestBody UserQueryDto userQueryDto);

    @PostMapping(value = "/user/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "保存用户", tags = "用户管理")
    ResponseVo saveUser(@RequestBody UserDto userDto);

    @GetMapping(value = "/user/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除用户", tags = "用户管理")
    void deleteUser(@RequestParam(value = "userId") String userId);

    @PostMapping(value = "/user/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新用户", tags = "用户管理")
    ResponseVo updateUser(@RequestBody UserDto userDto);

    @GetMapping(value = "/user/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "重置密码", tags = "用户管理")
    ResponseVo resetPassword(@RequestParam(value = "userId") String userId);

}

