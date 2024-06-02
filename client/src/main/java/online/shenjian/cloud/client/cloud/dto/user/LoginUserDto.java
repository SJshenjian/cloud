package online.shenjian.cloud.client.cloud.dto.user;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 扩展用户信息
 *
 * @author shenjian
 * @since 2023/07/24
 */
@Data
public class LoginUserDto implements UserDetails, CredentialsContainer {

    /**
     * 用户名 中文名称
     */
    private String username;
    /**
     * 登录账号
     */
    private String account;

    /**
     * 认证完成后，擦除密码等信息
     */
    @Override
    public void eraseCredentials() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return account;
    }

    /**
     * 账户是否未过期，过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 指示是否已过期的用户的凭据(密码)，过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 用户是否被启用或禁用。禁用的用户无法进行身份验证。
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
