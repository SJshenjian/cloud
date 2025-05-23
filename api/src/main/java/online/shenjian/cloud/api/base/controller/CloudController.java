package online.shenjian.cloud.api.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.shenjian.cloud.api.base.service.DogeService;
import online.shenjian.cloud.api.base.service.UserService;
import online.shenjian.cloud.client.cloud.CloudClient;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.cloud.dto.doge.DogeDto;
import online.shenjian.cloud.client.cloud.dto.doge.DogeQueryDto;
import online.shenjian.cloud.client.cloud.dto.user.PasswordDto;
import online.shenjian.cloud.client.cloud.dto.user.UserQueryDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/15
 */
@RestController
public class CloudController implements CloudClient {

    private UserService userService;
    private DogeService dogeService;
    public CloudController(UserService userService, DogeService dogeService) {
        this.userService = userService;
        this.dogeService = dogeService;
    }

    @Override
    public ResponseVo login(UserDto userDto) {
        return userService.login(userDto.getAccount(), userDto.getPassword());
    }

    @Override
    public ResponseVo<String> updatePassword(PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto.getOriginPassword(), passwordDto.getNewPassword());
    }

    @Override
    public IPage<UserDto> listUser(UserQueryDto userQueryDto) {
        return userService.listUser(userQueryDto);
    }

    @Override
    public ResponseVo saveUser(UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @Override
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    @Override
    public ResponseVo updateUser(UserDto userInfoDto) {
        return userService.updateUser(userInfoDto);
    }

    @Override
    public ResponseVo resetPassword(String userId) {
        return userService.resetPassword(userId);
    }

    @Override
    public List<DogeDto> getTop100DogeBalanceHistory(DogeQueryDto dogeQueryDto) {
        return dogeService.getTop100DogeBalanceHistory(dogeQueryDto);
    }
    @Override
    public void checkDogeBalance() {
        dogeService.checkDogeBalance();
    }
}
