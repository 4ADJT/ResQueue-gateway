# 🌐 Resqueue Gateway Service

## 📖 Sobre o Projeto
O **Resqueue Gateway Service** é a **porta de entrada** da arquitetura de microserviços do sistema **Resqueue**. Ele é responsável pelo roteamento de requisições entre os serviços internos, autenticação via Keycloak e documentação via Swagger.

Este serviço usa **Spring Cloud Gateway** para gerenciar o tráfego e balanceamento de carga dos microserviços, além de integrar com **Spring Security** para controle de acesso.

---

## 🚀 **Tecnologias Utilizadas**
- **Java 21 (Corretto)**
- **Spring Boot 3 (WebFlux, Security, Eureka Client, Spring Cloud Gateway)**
- **Spring Cloud Gateway**
- **Keycloak (OAuth 2.0)**
- **Swagger OpenAPI (SpringDoc)**
- **Maven**

---

## ⚙️ **Configuração do Ambiente**
### 🔧 **Variáveis de Ambiente**
Antes de rodar o serviço, configure as seguintes variáveis no **`application.yml`** ou no ambiente:

```yaml
server:
  port: 8080

spring:
  application:
    name: gateway

  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: resqueue_user
          uri: lb://RESQUEUE-USER
          predicates:
            - Path=/auth/**, /users/**

        - id: resqueue_clinic
          uri: lb://RESQUEUE-CLINIC
          predicates:
            - Path=/clinic/**

        - id: resqueue_vaccine
          uri: lb://RESQUEUE-VACCINE
          predicates:
            - Path=/vaccine/**

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KC_BASE_ISSUER_URL:http://localhost:9000}/realms/resqueue}

eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      defaultZone: ${EUREKA_URL:http://localhost:8761/eureka/}

springdoc:
  api-docs:
    enabled: true
    path: /v3/api-docs
  swagger-ui:
    enabled: true
    path: /docs
    config-url: /v3/api-docs/swagger-config
    urls:
      - name: users-service
        url: /users/v3/api-docs
      - name: clinic-service
        url: /clinic/v3/api-docs
      - name: vaccine-service
        url: /vaccine/v3/api-docs

management:
  endpoints:
    web:
      exposure:
        include: health
  endpoint:
    health:
      show-details: always
```

### 🔑 **Variáveis Explicadas**
| Variável                | Descrição |
|-------------------------|-----------|
| `KC_BASE_ISSUER_URL`    | URL base do **Keycloak** para autenticação JWT |
| `EUREKA_URL`            | URL do **Eureka Server** para registro do serviço |

---

## 🔥 **Rotas Configuradas**
Este gateway expõe e roteia as requisições para os seguintes serviços:

| Serviço         | Endpoint       | Destino |
|----------------|---------------|---------|
| **Autenticação** | `/auth/**` | `lb://RESQUEUE-USER` |
| **Usuários** | `/users/**` | `lb://RESQUEUE-USER` |
| **Clínicas** | `/clinic/**` | `lb://RESQUEUE-CLINIC` |
| **Vacinas** | `/vaccine/**` | `lb://RESQUEUE-VACCINE` |
| **Swagger UI** | `/docs` | Documentação via SpringDoc |

---

## 🚀 **Executando o Projeto**
###  **Rodando com Docker**
Uma imagem Docker já está disponível no **Docker Hub**:

```sh
docker pull rodrigobrocchi/resqueue-gateway:latest
docker run -p 8080:8080 \
  -e KC_BASE_ISSUER_URL=http://localhost:9000 \
  -e EUREKA_URL=http://localhost:8761/eureka \
  rodrigobrocchi/resqueue-gateway:latest
```
---
