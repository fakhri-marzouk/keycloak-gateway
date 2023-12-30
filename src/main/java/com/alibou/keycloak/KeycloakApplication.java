package com.alibou.keycloak;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@SecurityScheme(
		name = "keycloak"
		, openIdConnectUrl = "http://localhost:9090/realms/dive-into/.well-known/openid-configuration"
		, scheme = "bearer"
		, type = SecuritySchemeType.OPENIDCONNECT
		, in = SecuritySchemeIn.HEADER
)

public class KeycloakApplication {
	public static void main(String[] args) {
		SpringApplication.run(KeycloakApplication.class, args);
	}

}
