package com.dmx.api_weather.configs;

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
                        .title("API Weather - DMX")
                        .version("1.0")
                        .description("""
                    <h2>API de Desafio Técnico para a DMX </h2> \n
                    <strong>Objetivo: </strong> \n
                    Recuperar informações do Clima, alimentadas por uma solução ETL em Python, \n
                    armazenando em DB PostgreSQL.
                    \n <br><br>
                    <strong>Contato:</strong>
                    Elias A. Neri \n
                    <strong>WhatsApp:</strong> (19) 9.99127-5862 \n
                    <strong>Email: </strong> eliasneri@hotmail.com \n
                    \n
                    Data: 12/Agosto/2025
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
