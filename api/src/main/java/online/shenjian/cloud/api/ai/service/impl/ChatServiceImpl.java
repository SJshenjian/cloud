package online.shenjian.cloud.api.ai.service.impl;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import online.shenjian.cloud.api.ai.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private ClientV4 clientV4;

    public ChatServiceImpl(ClientV4 clientV4) {
        this.clientV4 = clientV4;
    }

    @Override
    public Object chat(String content) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(chatMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        ModelApiResponse modelApiResponse = clientV4.invokeModelApi(chatCompletionRequest);
        if (modelApiResponse.isSuccess()) {
            return modelApiResponse.getData().getChoices().get(0).getMessage().getContent();
        }
        return "";
    }
}
