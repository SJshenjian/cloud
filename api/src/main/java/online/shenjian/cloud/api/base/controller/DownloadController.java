package online.shenjian.cloud.api.base.controller;

import jakarta.servlet.http.HttpServletResponse;
import online.shenjian.cloud.api.base.service.DownloadService;
import online.shenjian.cloud.client.cloud.DownloadClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenjian
 * @since 2025/5/16
 */
@RestController
public class DownloadController implements DownloadClient {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @Override
    public ResponseEntity<Object> downloadFile(@RequestHeader HttpHeaders headers, @RequestParam String fileId) {
        return downloadService.downloadFile(headers, fileId);
    }

    @Override
    public void downloadStream(HttpServletResponse response, String fileId) {
        downloadService.downloadStream(fileId, response);
    }

}
