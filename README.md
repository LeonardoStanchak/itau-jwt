# 🔐 JWT Validator API – Desafio Técnico Itaú

API REST desenvolvida em Java com Spring Boot para validar tokens JWT com base nas regras de negócio propostas no desafio técnico do Banco Itaú.

---

## 🎯 Objetivo

Expor um endpoint `/api/v1/validate` que receba um JWT como entrada e retorne um booleano indicando se ele é válido ou não, com base nos seguintes critérios:

---

## 📜 Regras de Validação

Para que o JWT seja considerado **válido**, ele deve:

1. Ser um JWT com 3 partes (`header.payload.signature`)
2. Ter exatamente **3 claims**:
    - `Name`: apenas letras, até 256 caracteres
    - `Role`: um dos valores **Admin**, **Member**, ou **External**
    - `Seed`: um número inteiro **primo**

> ⚠️ JWTs com mais ou menos de 3 claims, ou com claims inválidas, são rejeitados com retorno `false`.

---

## 🔧 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **JUnit 5 + Mockito**
- **Lombok**
- **SLF4J + Logback** (logging estruturado)
- **Swagger / OpenAPI**
- **Docker & Docker Compose**
- (Pronto para CI/CD e deploy em Cloud – AWS Fargate, ECS, etc.)

---

## 🧪 Testes

> Totalmente coberto com testes unitários críticos para:

- Parser de JWT (`JwtParserUtil`)
- Validações semânticas (`JwtClaimsValidator`)
- Serviço de orquestração (`JwtValidationServiceImpl`)
- Controller REST (`JwtValidatorController`)
- Handler global de exceções

### Rodando os testes:

```bash
./mvnw test
```

---

## 🐳 Como Rodar com Docker Compose

### Pré-requisitos

- Docker instalado ([download](https://www.docker.com/products/docker-desktop))

### Subir a aplicação

```bash
docker-compose up --build
```

> A API será exposta em:  
> 🟢 `http://localhost:8080/api/v1/validate`

### Exemplo de chamada via `curl`

```bash
curl -X POST http://localhost:8080/api/v1/validate      -H "Content-Type: application/json"      -d '{"jwt":"eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg"}'
```

---

## 🔍 Observabilidade

A aplicação conta com:

- Logs informativos, de advertência e erro via **SLF4J/Logback**
- Logging segregado por camada (Controller, Service, Util)
- Mensagens detalhadas para troubleshooting de problemas

---

## ☁️ Pronto para Produção / Cloud

Embora o deploy *não esteja ativo* por motivos de custo (projeto free), o sistema está **pronto para subir** em ambientes como:

- **AWS ECS / Fargate**
- **Render.com (grátis)**
- **Heroku Free Tier**
- **Kubernetes com Helm** (estrutura Helm pronta)
- **Terraform** (infra como código planejada)

---

## 📬 Exemplo de Payload

### Requisição:

```http
POST /api/v1/validate
Content-Type: application/json

{
  "jwt": "<TOKEN_JWT_AQUI>"
}
```

### Resposta:

```json
{
  "valid": true
}
```

---

## 🧠 Premissas Assumidas

- A assinatura (cryptographic signature) do JWT não é verificada, pois o desafio se foca no conteúdo do payload.
- O número primo é validado via método matemático direto (`isPrime(n)`), sem uso de libs externas.
- Claims obrigatórias são tratadas como sensíveis e validadas com rigidez.
- O serviço é **imutável e sem estado** (stateless), facilitando escalabilidade horizontal.

---

## 📂 Estrutura do Projeto

```
src
├── controller            # Camada de exposição (REST API)
├── service               # Regras de negócio e orquestração
├── util                  # Helpers estáticos e validadores
├── validator             # Validação semântica do payload
├── exception             # Handlers globais para erros e validações
├── dto / domain          # DTOs de entrada/saída e modelo de domínio
└── resources             # application.properties, logs, etc.
```

---

## 🔁 Commits Semânticos

Durante a construção foram utilizados **commits pequenos, descritivos e progressivos**, como:

```
feat: valida claims obrigatórias do JWT
test: adiciona cobertura para exceções no parser
refactor: extrai validador de claims para util dedicado
docs: atualiza README com instruções de uso
```

---

## 🔧 Scripts e Ferramentas Úteis

```bash
# Rodar localmente sem docker
./mvnw spring-boot:run

# Executar testes
./mvnw test

# Gerar build para produção
./mvnw clean package

# Subir com docker
docker-compose up --build
```

---

## 📚 Documentação Swagger

Acesse em:

```
http://localhost:8080/swagger-ui.html
```

Ou via:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 💬 Contato

Desenvolvido por [Leonardo Stanchak](https://github.com/LeonardoStanchak)  
Profissional com foco em Engenharia de Software, Clean Code, SOLID, Microsserviços, Java e Cloud.

---