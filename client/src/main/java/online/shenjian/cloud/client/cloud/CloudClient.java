package online.shenjian.cloud.client.cloud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cloud", contextId = "cloud")
@Component
public interface CloudClient {

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "登录", tags = "用户管理")
    ResponseVo login(@RequestBody UserDto userDto);

    @PostMapping(value = "/testToken", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "测试Token", tags = "用户管理", security = { @SecurityRequirement(name = "token")})
    ResponseVo testToken(@RequestBody UserDto userDto);

}

