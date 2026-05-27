package com.desafio.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Lucas");

        Info info = new Info()
                .title("Task Manager API")
                .description("API REST para gerenciamento de tarefas corporativas")
                .version("1.0.0")
                .contact(contact);

        return new OpenAPI().info(info);
    }
}
