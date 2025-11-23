package com.campus.sys.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI api() {
    SecurityScheme bearer = new SecurityScheme().name("Authorization").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    return new OpenAPI()
      .info(new Info().title("Campus Sys Backend API").version("0.1.0"))
      .components(new Components().addSecuritySchemes("bearer-jwt", bearer))
      .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
  }
}