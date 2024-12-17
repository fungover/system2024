package org.fungover.system2024.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Fungover System 2024")
                        .version("1.0")
                        .description("API documentation for the System 2024 project."));
    }
}
