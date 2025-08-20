package com.ean.api_weather.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Weather")
                        .version("1.0")
                        .description("""
                                <strong>Objetivo: </strong> \n
                                Recuperar informações do Clima, alimentadas por uma solução ETL em Python, \n
                                armazenando em DB PostgreSQL.
                                \n <br><br>
                                <strong>Contato:</strong>
                                Elias A. Neri \n
                                <strong>Email: </strong> eliasneri@hotmail.com \n
                                \n
                                <br>
                                <strong>“Labor omnia vincit improbus”</strong> - A perseverança vence tudo!
                                """)
                        .contact(new Contact()
                                .name("Elias A. Neri")
                                .email("eliasneri@hotmail.com")
                                .url("https://www.github.com/eliasneri")
                        )
                );
    }

}
