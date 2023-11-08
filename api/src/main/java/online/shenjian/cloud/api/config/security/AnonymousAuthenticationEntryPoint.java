package online.shenjian.cloud.api.config.security;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.client.common.ResponseCode;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 匿名用户无权访问控制
 *
 * @author shenjian
 * @since 2023/07/24
 */
@Slf4j
@Component
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Role, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(ResponseVo.message(ResponseCode.UN_AUTHORIZED)));
    }
}
