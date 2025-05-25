package org.example.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF для прототипов REST API
                .cors(config -> config.configurationSource(corsConfigurationSource())) // Включаем CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/conferences").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    // Настройка CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");                           // Разрешить любые заголовки
        config.addAllowedMethod("*");                           // Разрешить методы
        config.addAllowedOrigin("http://localhost:3000");       // Разрешить запросы с порта 3000
        config.setAllowCredentials(true);                       // Разрешить отправку cookie
        source.registerCorsConfiguration("/**", config); // Применить ко всем маршрутам
        return source;
    }
}
