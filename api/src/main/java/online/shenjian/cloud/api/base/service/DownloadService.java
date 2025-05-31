package online.shenjian.cloud.api.base.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface DownloadService {

    /**
     * 分片下载大文件
     *
     * @param headers
     * @param fileId
     * @return
     */
    ResponseEntity<Object> downloadFile(@RequestHeader HttpHeaders headers, @RequestParam String fileId);

    /**
     * 强制浏览器下载，无需前端额外工作
     * @param response
     */
    void downloadStream(String fileId, HttpServletResponse response);

}
