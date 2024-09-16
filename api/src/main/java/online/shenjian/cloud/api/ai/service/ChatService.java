package online.shenjian.cloud.api.ai.service;

/**
 * AI 聊天服务
 */
public interface ChatService {

   Object chat(String content);

   void sseChat(String content);
}
