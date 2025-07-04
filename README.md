# Mini Autorizador - VR Benefícios

Este projeto implementa um sistema de autorização simples de transações com cartões de benefício como desafio técnico,
---

## 📌 Requisitos Atendidos

- Criar cartões com saldo inicial fixo (R$500,00)
- Consultar saldo por número do cartão
- Autorizar transações com verificação de:
    - Existência do cartão
    - Senha correta
    - Saldo suficiente
- Garantia de consistência sob concorrência (transações simultâneas)
  - Utilização de LOCK para concorrência, mantendo uma arquitetura simples sabendo que o projeto não será escalado
- Cobertura total com testes unitários
- Separação por camadas com base em DDD + Clean Architecture
---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.x
- Maven
- MySql (via Docker)
- Spring Data JPA
- JUnit 5, Mockito
- JaCoCo (88% cobertura)
- Swagger (Para documentação de desenvolvedor)
- DBeaver (GUI opcional para banco)
- Postman (Utilizado para automatizar os testes de API)
- Docker Compose

---

## 🧱 Arquitetura do Projeto

```bash
src/main/java/br/com/vr/autorizador
├── adapter         # Interfaces REST (Controllers, DTOs, ExceptionHandler)
├── application     # Casos de uso / serviços de aplicação
├── domain          # Entidades, exceções, interfaces (modelo de negócio)
├── infra           # Repositórios (JPA) e entidades de persistência
└── main            # Configurações (Application, config DB, startup)
````

## Testes de API via Postman e consulta Swagger

Collections:
VR - beneficios
  - 📄 [Execução basica de endpoints](./././postman/collection/VR%20-%20beneficios.postman_collection.json)

VR - beneficios Concorrencias
  - 📄 [Execução de concorrência](postman/collection/VR%20-%20beneficios Concorrencias.postman_collection.json)

## Swagger
**path:** /swagger
 - 📄 [Swagger](http://localhost:8080/swagger-ui/index.html) 