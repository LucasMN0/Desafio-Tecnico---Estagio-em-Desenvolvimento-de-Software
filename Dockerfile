# Imagem base leve com Java 21
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para o container
# O JAR é gerado em target/ após executar: mvn clean package
COPY target/task-manager-1.0.0.jar app.jar

# Expõe a porta padrão da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
