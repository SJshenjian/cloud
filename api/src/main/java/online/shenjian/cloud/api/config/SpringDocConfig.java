package online.shenjian.cloud.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder()
				.group("public")
				// 指定路径
				.pathsToMatch("/**")
				// 指定特定的 API 文档信息
				.addOpenApiCustomizer(userApiCustomizer())
				.build();
	}

	/**
	 * 定义 OpenApiCustomizer ，用于指定的 group
	 * @return
	 */
	public OpenApiCustomizer userApiCustomizer() {
		return openApi -> openApi.info(new io.swagger.v3.oas.models.info.Info()
				.title("Cloud API文档")
				.version("1.0")
				.contact(new io.swagger.v3.oas.models.info.Contact().name("").email("")))
				// 接口增加权限校验，如果接口需要，添加 security = { @SecurityRequirement(name = "token") }即可
				.components(new Components().addSecuritySchemes("token", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
}