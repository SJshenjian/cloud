package online.shenjian.cloud.api.service;

import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.common.ResponseVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjian
 * @since 2023-08-25
 */
public interface UserService {

    ResponseVo login(UserDto userDto);
}
