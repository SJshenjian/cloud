package online.shenjian.cloud.api.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统常量
 *
 * @author: shenjian
 * @since: 2023/10/13
 */
@Component
public class SysConstants implements InitializingBean {

    @Value("${aliyunOss.file.endpoint}")
    private String endpoint;

    @Value("${aliyunOss.file.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyunOss.file.accessKeySecret}")
    private String accessKeySecret ;

    @Value("${aliyunOss.file.bucketName}")
    private String bucketName;

    @Value("${aliyunOss.file.subDir}")
    private String subDir;

    public static final String SSE_START = "<SSE_START>";
    public static final String SSE_END = "<SSE_END>";

    public static String SPRING_FILE_ENDPOINT;
    public static String SPRING_FILE_ACCESS_KEY_ID;
    public static String SPRING_FILE_ACCESS_KEY_SECRET;
    public static String SPRING_FILE_BUCKET_NAME;
    public static String SPRING_FILE_SUB_DIR;

    @Override
    public void afterPropertiesSet() {
        SPRING_FILE_ENDPOINT = endpoint;
        SPRING_FILE_ACCESS_KEY_ID = accessKeyId;
        SPRING_FILE_ACCESS_KEY_SECRET = accessKeySecret;
        SPRING_FILE_BUCKET_NAME = bucketName;
        SPRING_FILE_SUB_DIR = subDir;
    }
}
 
