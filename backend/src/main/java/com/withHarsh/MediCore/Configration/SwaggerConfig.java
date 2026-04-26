package com.withHarsh.MediCore.Configration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // 🔐 Add JWT security globally
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                // 🔐 Define security scheme
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))

                // 📘 API Info
                .info(new Info()
                        .title("MediCore – Scalable Hospital Management Backend System")
                        .version("1.0")
                        .description("API documentation for Patient, Doctor, Admin, and Auth modules"));
    }
}