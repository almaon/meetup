package com.example.meetup.gateway.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ProjectConfig {

	@Value("${keySetURI}")
	private String keySetUri;

//	private final static String[] ALLOWED_PATHS = {"/api/userAccess/commands/confirmUserRegistration"};
//
//
//	public NegatedServerWebExchangeMatcher getURLsForDisabledCSRF() {
//		return new NegatedServerWebExchangeMatcher(exchange -> ServerWebExchangeMatchers.pathMatchers(ALLOWED_PATHS).matches(exchange));
//	}

	@Bean
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

		http.oauth2ResourceServer(
				c -> c.jwt(
						j -> j.jwkSetUri(keySetUri)));
		
		http.cors(Customizer.withDefaults());

		return http
                .authorizeExchange(exchange -> exchange
//        				.pathMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
//                        .pathMatchers("/", "/*.css", "/*.js", "/favicon.ico").permitAll()
                        .pathMatchers(HttpMethod.POST, "/public/api/userAccess/commands/registerNewUser").permitAll()
                        .pathMatchers(HttpMethod.POST, "/public/api/userAccess/commands/confirmUserRegistration").permitAll()
                        .anyExchange().authenticated())
//				.csrf(csrf -> csrf.requireCsrfProtectionMatcher(getURLsForDisabledCSRF()))
//				.csrf().requireCsrfProtectionMatcher(getURLsForDisabledCSRF()).and()
				.csrf(csrf -> csrf.disable())
//                .csrf(csrf -> csrf.requireCsrfProtectionMatcher(
//                        new ServerWebExchangeMatcher() {
//
//                            @Override
//                            public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
//                                return ServerWebExchangeMatchers.pathMatchers("/api/**").matches(serverWebExchange);
//                            }
//                        }
//                ))
                .build();
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}

}