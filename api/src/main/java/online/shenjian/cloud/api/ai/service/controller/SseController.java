package online.shenjian.cloud.api.ai.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import online.shenjian.cloud.api.config.ai.SseServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping("/subscribe")
    @Operation(summary = "SSE订阅", tags = "AI大模型")
    public SseEmitter subscribe() {
        return SseServer.subscribe();
    }

    @GetMapping("/close")
    @Operation(summary = "SSE连接关闭", tags = "AI大模型")
    public String closeConnection() {
        return SseServer.closeConnection();
    }
}