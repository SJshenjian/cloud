package online.shenjian.cloud.api.config.ai;

import online.shenjian.cloud.api.utils.TokenUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SseServer {

    private static final Map<String, SseEmitter> EMITTERS = new ConcurrentHashMap<>();

    public static SseEmitter subscribe() {
        String account = TokenUtils.getClaimsFromToken().getAccount();
        SseEmitter emitter = new SseEmitter(0L);
        EMITTERS.put(account, emitter);

        emitter.onCompletion(() -> {
            System.out.println("SSE connection completed for client: " + account);
            EMITTERS.remove(account);
        });

        emitter.onTimeout(() -> {
            System.out.println("SSE connection timed out for client: " + account);
            emitter.complete();
            EMITTERS.remove(account);
        });

        return emitter;
    }

    public static void sendMsg(String account, String message) {
        SseEmitter emitter = EMITTERS.get(account);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                EMITTERS.remove(account);
                emitter.completeWithError(e);
            }
        } else {
            System.out.println("No emitter found for client: " + account);
        }
    }

    public static String closeConnection() {
        String account = TokenUtils.getClaimsFromToken().getAccount();
        SseEmitter emitter = EMITTERS.remove(account);
        if (emitter != null) {
            emitter.complete();
            return "Connection closed for client: " + account;
        }
        return "No active connection found for client: " + account;
    }
}
