package online.shenjian.cloud.client.cloud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "download", contextId = "download")
@Component
@RequestMapping("/download")
public interface DownloadClient {

    @GetMapping("/download")
    @Operation(summary = "大文件下载", tags = "通用服务", security = {@SecurityRequirement(name = "token")})
    ResponseEntity<Object> downloadFile(@RequestHeader HttpHeaders headers, @RequestParam String fileId);
}