plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ues.ids.poo"
version = "0.1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc:4.1.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.1.2")
    implementation("org.springframework.boot:spring-boot-starter-validation:4.1.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.2.0")

    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")

    runtimeOnly("org.postgresql:postgresql:42.7.14")

    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test:4.1.2")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test:4.1.2")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test:4.1.2")

    testCompileOnly("org.projectlombok:lombok:1.18.48")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.48")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.1.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
