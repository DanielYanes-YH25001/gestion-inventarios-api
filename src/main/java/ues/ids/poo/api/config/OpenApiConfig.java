package ues.ids.poo.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API para Gestión de Inventarios")
                .description("""
                    API REST para la gestión integral de inventarios.
                    El proyecto contempla la administración de catálogos, existencias por sucursal,
                    movimientos de inventario y seguridad.
                    """)
                .version("0.1.0-SNAPSHOT")
                .contact(new Contact()
                    .name("Daniel Yanes")
                    .email("yh25001@ues.edu.sv")));
    }
}
