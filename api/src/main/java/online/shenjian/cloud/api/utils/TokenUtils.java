package online.shenjian.cloud.api.utils;

import com.alibaba.fastjson2.JSON;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import online.shenjian.cloud.api.config.security.model.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * TOKEN工具类
 *
 * @author shenjian
 * @since 2023/07/24
 **/
public class TokenUtils {

    private static final String TOKEN_SING = "Shenjian@Suanfaxiaosheng";
    // 过期时间15天
    private static final Long EXPIRATION_TIME = 86400000 * 15L;

    /**
     * 生成Token
     */
    public static String buildToken(Claims claims) {
        try {
            // 对密钥进行签名
            byte[] bytes = Base64.encodeBase64(TOKEN_SING.getBytes(), false);
            JWSSigner jwsSigner = new MACSigner(Arrays.copyOf(bytes, 128));
            // 准备JWS header
            JWSHeader jwsHeader = new JWSHeader
                    .Builder(JWSAlgorithm.HS512)
                    .type(JOSEObjectType.JWT)
                    .build();

            claims.setExp(new Date(System.currentTimeMillis() + EXPIRATION_TIME).getTime());

            Payload payload = new Payload(JSON.toJSONString(claims));
            // 封装JWS对象
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            // 签名
            jwsObject.sign(jwsSigner);
            return "Bearer " + jwsObject.serialize();
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        JWSObject jwsObject;
        try {
            token = token.replace("Bearer ", "");
            jwsObject = JWSObject.parse(token);
            // HMAC验证器
            byte[] decodedBytes = Base64.encodeBase64(TOKEN_SING.getBytes(), false);
            JWSVerifier jwsVerifier = new MACVerifier(Arrays.copyOf(decodedBytes, 128));
            if (!jwsObject.verify(jwsVerifier)) {
                return false;
            }

            String payload = jwsObject.getPayload().toString();
            Claims claims = JSON.parseObject(payload, Claims.class);
            if (claims.getExp() < new Date().getTime()) {
                return false;
            }
            return true;
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从token中获取用户ID
     *
     * @return
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getAccount();
    }

    public static Claims getClaimsFromToken() {
        String token = RequestUtils.getRequest().getHeader("Authorization");
        return getClaimsFromToken(token);
    }

    public static Claims getClaimsFromToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return parseToken(token);
    }

    /**
     * 解析token
     *
     * @return
     */
    private static Claims parseToken(String token) {
        JWSObject jwsObject;
        try {
            token = token.replace("Bearer ", "");
            jwsObject = JWSObject.parse(token);
            // HMAC验证器
            byte[] decodedBytes = Base64.encodeBase64(TOKEN_SING.getBytes(), false);
            JWSVerifier jwsVerifier = new MACVerifier(Arrays.copyOf(decodedBytes, 128));
            if (!jwsObject.verify(jwsVerifier)) {
                return null;
            }

            String payload = jwsObject.getPayload().toString();
            Claims claims = JSON.parseObject(payload, Claims.class);
            return claims;
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(86400000 / 1000 / 60 / 60);
    }
}
