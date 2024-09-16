package online.shenjian.cloud.api.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 阿里云OSS工具类
 *
 * @author: shenjian
 * @since: 2023/10/13
 */
@Slf4j
public class AliyunOssUtils {

    private static final String ENDPOINT = SysConstants.SPRING_FILE_ENDPOINT;
    private static final String ACCESS_KEY_ID = SysConstants.SPRING_FILE_ACCESS_KEY_ID;
    private static final String ACCESS_KEY_SECRET = SysConstants.SPRING_FILE_ACCESS_KEY_SECRET;
    private static final String BUCKET_NAME = SysConstants.SPRING_FILE_BUCKET_NAME;
    private static final String SUB_DIR = SysConstants.SPRING_FILE_SUB_DIR;

    /**
     *  阿里云OSS上传文件方法
     *
     * @param inputStreamFile
     * @param fileName
     * @param flag 是否原始文件名
     * @return
     */
    public static String uploadFile(InputStream inputStreamFile, String fileName, Boolean flag) {
        // 新建一个OSS对象
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            // bucket容器不存在，就创建
            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 创建文件路径，DateTimeUtil时间工具类
            String fileUrl = SUB_DIR + "/" +DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()) + "/";
            if (!flag) {
                fileUrl += fileName.substring(0, fileName.lastIndexOf(".")) + "_" + UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
            } else {
                fileUrl += fileName;
            }
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(BUCKET_NAME, fileUrl, inputStreamFile));
            // 设置权限 这里是公开读
            ossClient.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
            if (null != result) {
                log.info("OSS文件上传成功, 文件名：" + fileUrl);
                String url = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + fileUrl;
                log.info("url为：" + url);
                // 返回文件url访问路径
                return url;
            } else {
                log.info("OSS文件上传失败");
                return null;
            }
        } catch (OSSException oe) {
            log.error("OSS文件上传失败OSSException: {}", oe.getMessage());
            return null;
        } catch (ClientException ce) {
            log.error("OSS文件上传失败ClientException: {}", ce.getMessage());
            return null;
        } finally {
            // 关闭 ossClient
            ossClient.shutdown();
        }
    }
}
