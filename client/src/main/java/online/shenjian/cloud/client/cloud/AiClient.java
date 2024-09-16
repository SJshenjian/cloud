package online.shenjian.cloud.client.cloud;

import io.swagger.v3.oas.annotations.Operation;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "ai", contextId = "ai")
@Component
@RequestMapping("/chat")
public interface AiClient {

    @PostMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "AI聊天", tags = "AI大模型")
    ResponseVo<Object> chat(String content);

    @PostMapping(value = "/sseChat", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "AI聊天(SSE)", tags = "AI大模型")
    void sseChat(String content);
}

