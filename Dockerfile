# ==========================================
# Etapa 1: Compilación con Gradle
# ==========================================
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copia archivos de configuración de Gradle y dependencias
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Descarga dependencias
RUN ./gradlew dependencies --no-daemon

# Copia el código fuente y empaquetar el JAR
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# ==========================================
# Etapa 2: Imagen final de ejecución
# ==========================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia el JAR generado desde la etapa de compilación
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]