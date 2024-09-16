package online.shenjian.cloud.api.ai.service.controller;

import online.shenjian.cloud.api.ai.service.ChatService;
import online.shenjian.cloud.client.cloud.AiClient;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController implements AiClient {

    private ChatService chatService;

    public AiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public ResponseVo<Object> chat(String content) {
         return ResponseVo.success(chatService.chat(content));
    }

    @Override
    public void sseChat(String content) {
        chatService.sseChat(content);
    }
}
