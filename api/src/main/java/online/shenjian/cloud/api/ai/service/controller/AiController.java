package online.shenjian.cloud.api.ai.service.controller;

import online.shenjian.cloud.api.ai.service.ChatService;
import online.shenjian.cloud.api.ai.service.impl.DoubaoChatServiceImpl;
import online.shenjian.cloud.client.cloud.AiClient;
import online.shenjian.cloud.client.cloud.dto.ai.ChatDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController implements AiClient {

    private ChatService chatService;
    private DoubaoChatServiceImpl doubaoChatService;

    public AiController(ChatService chatService, DoubaoChatServiceImpl doubaoChatService) {
        this.chatService = chatService;
        this.doubaoChatService = doubaoChatService;
    }

    @Override
    public ResponseVo<Object> chat(ChatDto chatDto) {
         return ResponseVo.success(chatService.chat(chatDto));
    }

    @Override
    public void sseChat(ChatDto chatDto) {
        chatService.sseChat(chatDto);
    }

    @Override
    public void sseChatDoubao(ChatDto chatDto) {
        doubaoChatService.sseChat(chatDto);
    }
}
