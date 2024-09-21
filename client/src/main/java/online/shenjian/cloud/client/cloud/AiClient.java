package online.shenjian.cloud.client.cloud;

import io.swagger.v3.oas.annotations.Operation;
import online.shenjian.cloud.client.cloud.dto.ai.ChatDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ai", contextId = "ai")
@Component
@RequestMapping("/chat")
public interface AiClient {

    @PostMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "AI聊天", tags = "AI大模型")
    ResponseVo<Object> chat(@RequestBody ChatDto chatDto);

    @PostMapping(value = "/sseChat")
    @Operation(summary = "AI聊天(SSE)", tags = "AI大模型")
    void sseChat(@RequestBody ChatDto chatDto);
}

