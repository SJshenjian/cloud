package online.shenjian.cloud.api.config;

import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.client.common.ResponseCode;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.TreeMap;

/**
 * 统一返回封装
 *
 * @author shenjian
 * @since 2023/7/14
 */
@RestControllerAdvice
@Slf4j
public class UnitedResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null && !(body instanceof ResponseVo) && !(body instanceof byte[])) {
            // 放行Swagger相关
            if (body instanceof TreeMap && ((TreeMap)body).containsKey("oauth2RedirectUrl")) {
                return body;
            }
            return ResponseVo.message(ResponseCode.SUCCESS.val(), ResponseCode.SUCCESS.des(), body);
        }
        return body;
    }
}