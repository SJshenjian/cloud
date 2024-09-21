package online.shenjian.cloud.api.ai.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import online.shenjian.cloud.api.config.ai.SseServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/sse")
public class SseController {

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @CrossOrigin
    @Operation(summary = "SSE订阅", tags = "AI大模型")
    public SseEmitter subscribe(String token, HttpServletResponse response) {
        SseEmitter sseEmitter = SseServer.subscribe(token);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        return sseEmitter;
    }

    @GetMapping("/close")
    @Operation(summary = "SSE连接关闭", tags = "AI大模型")
    public String closeConnection() {
        return SseServer.closeConnection();
    }
}