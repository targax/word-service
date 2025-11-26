# ================================
# 🟦 STAGE 1 — Build da aplicação
# ================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o pom.xml primeiro (cache de dependências)
COPY pom.xml .

# Baixa dependências sem compilar tudo
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Compila o jar
RUN mvn clean package -DskipTests


# ==================================
# 🟩 STAGE 2 — Runtime otimizado
# ==================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o jar do stage de build
COPY --from=build /app/target/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Variável de ambiente opcional
ENV JAVA_OPTS=""

# Comando final
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]