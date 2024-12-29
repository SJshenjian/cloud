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
    private List<ChatMessage> streamMessages = new ArrayList<>();

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
        // 重置对话，TODO 记录数据库
        if (chatDto.getContent().contains("init")) {
            streamMessages = new ArrayList<>();
        }
        ChatMessage streamSystemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你可以通过以下步骤来了解客户的购房需求：\n" +
                "1. 首先询问客户是首次购房还是改善型购房。\n" +
                "2. 接着了解客户对于房屋地理位置（例如城市、区域、靠近的地标等）的偏好。\n" +
                "3. 询问客户期望的房屋类型（如公寓、别墅、住宅等）。\n" +
                "4. 了解客户对房屋面积的大致要求。\n" +
                "5. 询问客户的预算范围。\n" +
                "6. 了解客户是否有特殊的配套设施需求（如停车位、花园、健身房等）。\n" +
                "7. 了解客户是否有其他购房方面的需求\n" +
                "\n" +
                "在你与客户的对话过程中，仔细记录下客户对于每个问题的回答。最后，请在<总结>标签内以JSON格式总结客户的购房需求，格式如下：\n" +
                "{\n" +
                "    \"购房类型\": \"首次购房/改善型购房\",\n" +
                "    \"地理位置\": \"具体地理位置\",\n" +
                "    \"房屋类型\": \"公寓/别墅/住宅等\",\n" +
                "    \"面积要求\": \"X平方米左右\",\n" +
                "    \"预算范围\": \"X - X元\",\n" +
                "    \"特殊配套设施\": \"停车位/花园/健身房等\"\n" +
                "    \"其他需求\": \"具体需求\"\n" +
                "}\n" +
                "请现在开始与客户的对话并总结需求。").build();
        streamMessages.add(streamSystemMessage);

        ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(chatDto.getContent()).build();
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
                            }
                        }
                );
        SseServer.sendMsg(account, SysConstants.SSE_END);
    }
}
