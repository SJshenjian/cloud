package online.shenjian.cloud.api.config.security;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.shenjian.cloud.client.common.ResponseCode;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 访问拒绝处理器
 *
 * @author shenjian
 * @since 2023/07/24
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.getWriter().write(JSON.toJSONString(ResponseVo.message(ResponseCode.LICENSE_EXPIRED)));
    }
}