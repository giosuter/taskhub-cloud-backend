package ch.devprojects.taskhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * Security for stateless REST: - CORS: allow Angular dev + prod site - CSRF:
 * disabled (no browser sessions/cookies) - 401/403 JSON bodies - /api/**
 * currently permitAll; tighten later when you add OAuth2/JWT.
 */
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain api(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui/**", "/swagger-ui.html",
						"/v3/api-docs/**", "/api-docs/**", "/actuator/health", "/h2-console/**", "/api/**" // open now
																											// for
																											// development
				).permitAll().anyRequest().permitAll()).headers(h -> h.frameOptions(f -> f.sameOrigin())) // allow H2
																											// console
				.exceptionHandling(e -> e.authenticationEntryPoint(new RestAuthenticationEntryPoint())
						.accessDeniedHandler(new RestAccessDeniedHandler()));
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cfg = new CorsConfiguration();
		cfg.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200", "https://devprojects.ch"));
		cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		cfg.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
		cfg.setAllowCredentials(false); // tokens in Authorization header
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cfg);
		return source;
	}
}