package online.shenjian.cloud.api.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.api.base.mapper.UserMapper;
import online.shenjian.cloud.api.base.model.User;
import online.shenjian.cloud.client.cloud.dto.LoginUserDto;
import online.shenjian.cloud.common.enums.Constant;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 基于数据库登录验证
 *
 * @author shenjian
 * @since 2023/07/24
 */
@Slf4j
@Component
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    private DefaultUserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", username);
        queryWrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        LoginUserDto loginUserDto = CommonDtoUtils.transform(user, LoginUserDto.class);
        return loginUserDto;
    }
}