package online.shenjian.cloud.api.ai.service.impl;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import online.shenjian.cloud.api.ai.service.ChatService;
import online.shenjian.cloud.api.config.ai.SseServer;
import online.shenjian.cloud.api.utils.TokenUtils;
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

    public void sseChat(String content) {
        String account = TokenUtils.getClaimsFromToken().getAccount();
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), content);
        messages.add(chatMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();

        ModelApiResponse modelApiResponse = clientV4.invokeModelApi(chatCompletionRequest);
        if (modelApiResponse.isSuccess()) {
            ChatMessageAccumulator chatMessageAccumulator = mapStreamToAccumulator(modelApiResponse.getFlowable())
                    .doOnNext(accumulator -> {
                        if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                            // Send each piece of content as it arrives
                            SseServer.sendMsg(account, accumulator.getDelta().getContent());
                        }
                    })
                    .lastElement()
                    .blockingGet();

            // Here you might want to handle the completion or error cases more explicitly
            Choice choice = chatMessageAccumulator.getChoice();
            // Construct your model data as before, but perhaps you don't need to return it in this context
        }
    }

    private static Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null, chunk.getChoices().get(0), chunk.getUsage(), chunk.getCreated(), chunk.getId());
        });
    }
}
