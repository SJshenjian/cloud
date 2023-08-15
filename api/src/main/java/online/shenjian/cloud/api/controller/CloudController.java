package online.shenjian.cloud.api.controller;

import online.shenjian.cloud.client.cloud.CloudClient;
import online.shenjian.cloud.client.cloud.dto.UserInfoDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenjian
 * @since 2023/8/15
 */
@RestController
public class CloudController implements CloudClient {

    @Override
    public ResponseVo login(UserInfoDto userInfoDto) {
        return ResponseVo.success("登录成功");
    }
}
