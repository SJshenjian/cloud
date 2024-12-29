package online.shenjian.cloud.api.ai.service.impl;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import online.shenjian.cloud.api.ai.service.ChatService;
import online.shenjian.cloud.api.config.ai.SseServer;
import online.shenjian.cloud.api.utils.SysConstants;
import online.shenjian.cloud.api.utils.TokenUtils;
import online.shenjian.cloud.client.cloud.dto.ai.ChatDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoubaoChatServiceImpl implements ChatService {

    private ArkService service;

    public DoubaoChatServiceImpl(ArkService service) {
        this.service = service;
    }

    @Override
    public Object chat(ChatDto chatDto) {
        return null;
    }

    @Override
    public void sseChat(ChatDto chatDto) {
        String account = TokenUtils.getClaimsFromToken().getAccount();
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("常见的十字花科植物有哪些？").build();
        messages.add(systemMessage);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(SysConstants.DOUBAO_PRO_128k)
                .messages(messages)
                .build();

        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));

        System.out.println("\n----- streaming request -----");
        final List<ChatMessage> streamMessages = new ArrayList<>();
        final ChatMessage streamSystemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
        final ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("常见的十字花科植物有哪些？").build();
        streamMessages.add(streamSystemMessage);
        streamMessages.add(streamUserMessage);

        ChatCompletionRequest streamChatCompletionRequest = ChatCompletionRequest.builder()
                .model(SysConstants.DOUBAO_PRO_128k)
                .messages(streamMessages)
                .build();

        service.streamChatCompletion(streamChatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(
                        choice -> {
                            if (choice.getChoices().size() > 0) {
                                SseServer.sendMsg(account, choice.getChoices().get(0).getMessage().getContent());
                                System.out.print(choice.getChoices().get(0).getMessage().getContent());
                            }
                        }
                );
    }
}
