package br.com.imaginer.resqueuegateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http

        .csrf(ServerHttpSecurity.CsrfSpec::disable)

        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/**/eureka/**").permitAll()
            .pathMatchers("/**/actuator/**").permitAll()
            .pathMatchers("/**/login").permitAll()
            .pathMatchers("/**/create/admin").permitAll()
            .pathMatchers("/**/create/group", "/**/create/user", "/**/list/user")
              .hasAuthority("ADMIN")
            .pathMatchers("/**/logout").permitAll()
            .anyExchange().authenticated()

        ).oauth2ResourceServer(oauth2 -> oauth2
            .jwt(Customizer.withDefaults())
        );

    return http.build();
  }
}
