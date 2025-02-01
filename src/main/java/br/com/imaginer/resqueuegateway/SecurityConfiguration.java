package br.com.imaginer.resqueuegateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http

        .csrf(ServerHttpSecurity.CsrfSpec::disable)

        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/eureka/**").permitAll()
            .pathMatchers("/login/**").permitAll()
            .pathMatchers("/create/user/**").permitAll()
            .pathMatchers("/logout/**").permitAll()
            .anyExchange().authenticated()
        ).oauth2ResourceServer(oauth2 -> oauth2
            .jwt(withDefaults())
        );

    return http.build();
  }
}
