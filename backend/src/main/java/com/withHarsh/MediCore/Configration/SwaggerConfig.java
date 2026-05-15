package com.withHarsh.MediCore.Configration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // Add JWT security globally to all endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                // Define the security scheme
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT token in the format: Bearer <token>")
                        ))

                // Server Configuration
                .servers(List.of(
                        new Server()
                                .url("https://medicore-p9x7.onrender.com")
                                .description("Production Server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server")
                ))

                // Tag Definitions for grouping
                .tags(List.of(
                        new Tag().name("Auth APIs").description("Authentication & Authorization APIs"),
                        new Tag().name("Patient APIs").description("Patient-related operations"),
                        new Tag().name("Doctor APIs").description("Doctor dashboard & operations"),
                        new Tag().name("Admin APIs").description("Admin management system")
                ))

                // API Metadata
                .info(new Info()
                        .title("MediCore – Scalable Hospital Management System API")
                        .version("1.0.0")
                        .description("### MediCore Healthcare Backend\n\n" +
                                "MediCore is a production-grade Hospital Management System built with Spring Boot.\n\n" +
                                "**Core Features:**\n" +
                                "* **Patient Management** – Registration, history, and booking.\n" +
                                "* **Doctor Management** – Schedule and dashboard operations.\n" +
                                "* **Security** – Role-based access with JWT + Refresh Tokens.\n" +
                                "* **Recovery** – Email-based password recovery system.\n\n" +
                                "Built with clean architecture and secure REST practices.")
                        .contact(new Contact()
                                .name("Harshvardhan Vathare")
                                .email("vathare.harsh45@gmail.com")
                                .url("https://github.com/HarshVathare")) 
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                );
    }
}
