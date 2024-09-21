package online.shenjian.cloud.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 配置，建造者模式
 *
 * @author shenjian
 * @since 2023/07/24
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    // 匿名登录处理
    private AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;
    private MyAccessDeniedHandler myAccessDeniedHandler;
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityConfiguration(AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint, MyAccessDeniedHandler myAccessDeniedHandler
            , JwtTokenFilter jwtTokenFilter) {
        this.anonymousAuthenticationEntryPoint = anonymousAuthenticationEntryPoint;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).csrf((csrf) -> csrf.disable())
                // 因为使用JWT，所以不需要HttpSession
                .sessionManagement((sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests((authz) -> authz
                        // OPTIONS请求全部放行
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 放行接口
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/sse/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/verifyCode").permitAll()
                        .requestMatchers("/user/resetPassword").permitAll()
                        // 放行Swagger页面
                        .requestMatchers("/springdoc/**").permitAll()
                        // 所有请求全部需要鉴权认证
                        .anyRequest().authenticated()
                )
                // 异常处理(权限拒绝、登录失效等)
                .headers((headers) -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
                        .accessDeniedHandler(myAccessDeniedHandler)
                );

        // 使用自定义的 Token过滤器 验证请求的Token是否合法
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.cacheControl(cacheControlConfig -> {}));
        return http.build();
    }
}
