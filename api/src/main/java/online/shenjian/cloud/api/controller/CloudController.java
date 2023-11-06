package online.shenjian.cloud.api.controller;

import online.shenjian.cloud.api.service.UserService;
import online.shenjian.cloud.client.cloud.CloudClient;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenjian
 * @since 2023/8/15
 */
@RestController
public class CloudController implements CloudClient {

    private UserService userService;
    public CloudController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseVo login(UserDto userDto) {
        return userService.login(userDto);
    }
}
