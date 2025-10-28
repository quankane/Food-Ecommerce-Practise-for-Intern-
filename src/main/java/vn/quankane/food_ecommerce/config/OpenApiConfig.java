package vn.quankane.food_ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(@Value("${openapi.service.api-docs}") String apiDocs) {
        return GroupedOpenApi.builder().group(apiDocs).packagesToScan("vn.quankane.food_ecommerce.controller").build();
    }

    @Bean
    public OpenAPI openAPI(@Value("${openapi.service.title}") String title, @Value("${openapi.service.version}") String version, @Value("${openapi.service.server}") String serverUrl) {
        return new OpenAPI().servers(List.of(new Server().url(serverUrl).description("Server Initial"))).info(new Info().title(title).description("API Documents").version(version).license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))).components(new Components().addSecuritySchemes("Bearer Token", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))).security(List.of(new SecurityRequirement().addList("Bearer Token")));
    }
}
