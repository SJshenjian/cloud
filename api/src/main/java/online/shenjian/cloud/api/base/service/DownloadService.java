package online.shenjian.cloud.api.base.service;

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
}
