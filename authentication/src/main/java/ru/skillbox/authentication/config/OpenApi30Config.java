package ru.skillbox.authentication.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Auth API", version = "v1", description = "Социальная сеть v.1.0"),
        servers = {@Server(url = "https://swagger.ragaev.keenetic.pro/api/v1/auth"), @Server(url = "http://localhost:9090/api/v1/auth")})
public class OpenApi30Config {

}
