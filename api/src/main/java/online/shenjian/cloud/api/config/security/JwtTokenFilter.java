package online.shenjian.cloud.api.config.security;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.api.utils.RequestUtils;
import online.shenjian.cloud.api.utils.TokenUtils;
import online.shenjian.cloud.client.common.ResponseCode;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * TOKEN过滤器
 *
 * @author shenjian
 * @since 2023/07/24
 */
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    private static final Set<String> ignoreUrlSet = new HashSet<>();

    static {
        ignoreUrlSet.add("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = RequestUtils.getRequest().getHeader("Authorization");
        // 过滤登录页面，防止token失效
        if (StringUtils.isNotBlank(token) && !"null".equals(token) && !ignoreUrlSet.contains(request.getRequestURI())) {
            String username = TokenUtils.getUsernameFromToken(token);
            if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (TokenUtils.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.setHeader("Content-type", "application/json;charset=UTF-8");
                    response.getWriter().print(JSON.toJSONString(ResponseVo.message(ResponseCode.TOKEN_EXPIRATION)));
                    return ;
                }
            }

            // 权限过滤器，只加载当前菜单下权限
            buildCurrentUserRoleByMenuCode(request, response);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取当前用户当前菜单权限
     *
     * @param request
     * @return
     */
    private void buildCurrentUserRoleByMenuCode(HttpServletRequest request, HttpServletResponse response) {

    }
}