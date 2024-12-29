package online.shenjian.cloud.api.config.ai;

import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DoubaoConfig {

    @Value("${ai.doubao.apiKey}")
    private String doubaoApiKey;

    private static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
    @Bean
    public ArkService arkService() {
        Dispatcher dispatcher = new Dispatcher();
        return ArkService.builder().dispatcher(dispatcher).connectionPool(connectionPool)
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3").apiKey(doubaoApiKey).build();
    }
}
