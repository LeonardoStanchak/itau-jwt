# Etapa 1: Build da aplicação usando Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src

# Executa build (gera o .jar)
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final, mais leve (runtime apenas)
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o JAR gerado
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta (ajuste conforme a tua API)
EXPOSE 8080

# Ativa UTF-8 e profile prod
ENV LANG C.UTF-8
ENV JAVA_OPTS="-Dspring.profiles.active=prod"

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
