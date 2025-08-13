# 🌦️ API Weather
---
## 📌 Objetivo da API

Esta API tem como objetivo consumir e disponibilizar dados meteorológicos que são inseridos em um banco de dados PostgreSQL por um processo **ETL** externo.  
Ela oferece endpoints REST para consulta, inserção, atualização e exclusão desses dados, com monitoramento integrado via Prometheus.

---

API desenvolvida em **Java** com **Spring Boot** para consumo e disponibilização de dados meteorológicos inseridos por um processo **ETL** em um banco de dados **PostgreSQL**.

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL**
- **Swagger** (Documentação da API)
- **JUnit 5** (Testes Unitários e de Integração)
- **Prometheus + Micrometer** (Observabilidade e Métricas)
- **Docker** (Containerização)
- **Maven** (Gerenciamento de dependências e build)
- **IntelliJ** IDE

---

## 📂 Estrutura de Pastas

```plaintext
src
 ├── main
 │   ├── java
 │   │   └── com.dmx.api_weather
 │   │       ├── configs         # Configurações gerais e beans
 │   │       ├── controllers     # Controladores REST
 │   │       ├── DTOS            # Objetos de transferência de dados
 │   │       ├── entities        # Entidades JPA
 │   │       ├── exceptions      # Tratamento de exceções customizadas
 │   │       ├── repositories    # Interfaces de acesso ao banco de dados
 │   │       ├── services        # Lógica de negócio
 │   │       └── ApiWeatherApplication # Classe principal da aplicação
 │   └── resources
 │       ├── static              # Arquivos estáticos
 │       ├── templates           # Templates (se aplicável)
 │       └── application.properties # Configurações da aplicação
 ├── test
 │   ├── java                    # Testes unitários e de integração
 │   └── resources               # Recursos para testes
```

---

## 📜 Documentação da API

A documentação Swagger está disponível após subir a aplicação:

```
http://localhost:9090/swagger-ui/index.html
```

---

## 📌 Endpoints Principais

| Método | Endpoint                      | Descrição                                                          |
|--------|-------------------------------|--------------------------------------------------------------------|
| GET    | `/api/v1/clima`               | Lista todos os registros meteorológicos                            |
| GET    | `/api/v1/clima/{cidade}`      | Busca dados meteorológicos por CIDADE                              |
| GET    | `/api/v1/clima/{cidade}/hoje` | Busca dados meteorológicos por CIDADE, considerando a data de hoje |

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- **Java 17+**
- **Maven 3.8+**
- **Docker** (opcional, para subir PostgreSQL)
- **PostgreSQL** configurado

### Passos
```bash
# Clonar o repositório
git clone https://github.com/eliasneri/weatherProject.git
cd weatherProject
cd api-weather

# Construir e rodar testes
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

A aplicação subirá em:
```
http://localhost:9090
```

---

## 🧪 Testes Unitários e de Integração

O projeto possui **testes unitários** e **testes de integração** utilizando **JUnit 5**.

Para rodar os testes:
```bash
mvn test
```

---

## 📊 Observabilidade com Prometheus

A aplicação expõe métricas no endpoint padrão do Actuator:

```
http://localhost:9090/actuator/prometheus
```

### Métricas Customizadas
Foi implementada uma contagem personalizada de erros HTTP usando **Micrometer**:

```java
public GlobalExceptionHandler(MeterRegistry registry) {
    this.notFoundCounter = Counter.builder("api_errors_total")
            .description("Total de erros 404 Not Found")
            .tag("error_type", "not_found")
            .register(registry);

    this.badRequestCounter = Counter.builder("api_errors_total")
            .description("Total de erros 400 Bad Request")
            .tag("error_type", "bad_request")
            .register(registry);

    this.internalServerErrorCounter = Counter.builder("api_errors_total")
            .description("Total de erros 500 Internal Server Error")
            .tag("error_type", "internal_server_error")
            .register(registry);
}
```

Essas métricas permitem monitorar a quantidade de erros por tipo, facilitando a detecção e resposta a problemas.

---
