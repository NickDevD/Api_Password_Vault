# Use uma imagem base do OpenJDK com Alpine Linux para um tamanho menor
FROM openjdk:17-jdk-slim-buster

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR compilado do seu projeto para o contêiner
# Assuma que seu arquivo JAR está em target/passwordapi-0.0.1-SNAPSHOT.jar
# Verifique o nome exato do seu JAR na pasta 'target' após o 'mvn clean install'
COPY target/password-vault-0.0.1-SNAPSHOT.jar app.jar 

# Expõe a porta que a aplicação Spring Boot está rodando (configurada em application.properties)
EXPOSE 8080

# Comando para executar a aplicação Spring Boot quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]