package online.shenjian.cloud.api.config.security.model;

import lombok.Data;

import java.util.List;

/**
 * JWT实体类
 *
 * @author shenjian
 * @since 2023/7/25
 */
@Data
public class Claims {

    /**
     * 过期时间
     */
    private Long exp;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户中文名
     */
    private String username;

    /**
     * 用户登录账号
     */
    private String account;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 状态 0启用 1禁用
     */
    private String state;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> permissions;

    public Claims() {

    }

    public Claims(Long exp, String username, String account, String state, List<String> roles, List<String> permissions) {
        this.exp = exp;
        this.username = username;
        this.account = account;
        this.state = state;
        this.roles = roles;
        this.permissions = permissions;
    }
}
