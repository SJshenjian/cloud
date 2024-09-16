package online.shenjian.cloud.api.config.ai;

import com.zhipu.oapi.ClientV4;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ZhiPuConfig {

    @Value("${ai.zhipu.apiKey}")
    private String zhipuApiKey;

    @Bean(name = "clientV4")
    public ClientV4 clientV4() {
        ClientV4 client = new ClientV4.Builder(zhipuApiKey).build();
        return client;
    }
}
