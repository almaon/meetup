package com.example.meetup.infrastructure.auth;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev | prod")
public class SecurityConfig {

	@Value("${keySetURI}")
	private String keySetUri;

	static class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

		public AbstractAuthenticationToken convert(Jwt jwt) {

			Collection<String> authorities = jwt.getClaimAsStringList("roles").stream()
					.map(role -> "ROLE_" + role).toList();
			Collection<GrantedAuthority> grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			return new JwtAuthenticationToken(jwt, grantedAuthorities);
		}
	}

	@Bean
	public SecurityFilterChain scurityFilterChain(HttpSecurity http) throws Exception {

		http.oauth2ResourceServer(c -> c
				.jwt(j -> j.jwkSetUri(keySetUri).jwtAuthenticationConverter(new CustomAuthenticationConverter())));

		http.oauth2Client(Customizer.withDefaults());

		http.cors(Customizer.withDefaults());

		http.authorizeHttpRequests(c -> c.

				requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().

				requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/camunda/**").permitAll().

				requestMatchers("/public/api/userAccess/commands/registerNewUser").permitAll()
				.requestMatchers("/public/api/userAccess/commands/confirmUserRegistration").permitAll()
				.requestMatchers("/api/administration/commands/**").permitAll()
				.requestMatchers("/api/meetings/views/**").permitAll().

				

				requestMatchers("/api/administration/**").hasAnyRole("admin").requestMatchers("/api/userAccess/**")
				.hasAnyRole("admin").

				anyRequest().authenticated());

		http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/camunda/**")
				.ignoringRequestMatchers("/public/api/userAccess/commands/registerNewUser")
				.ignoringRequestMatchers("/api/administration/commands/**")
				.ignoringRequestMatchers("/api/meetings/views/**")
				.ignoringRequestMatchers("/public/api/userAccess/commands/confirmUserRegistration"));

		return http.build();
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:9000"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
//		configuration.setAllowedHeaders(List.of("*"));
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(@Value("${auth.client.id}") String clientId,
			@Value("${auth.client.secret}") String clientSecret, @Value("${auth.redirect.uri}") String redirectUri) {

		ClientRegistration c1 = ClientRegistration.withRegistrationId("1").clientId(clientId).clientSecret(clientSecret)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC).tokenUri(redirectUri)
				.scope(OidcScopes.OPENID).scope(OidcScopes.PROFILE).build();

		var repository = new InMemoryClientRegistrationRepository(c1);

		return repository;
	}

	@Bean
	public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) {

		var provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();

		var cm = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
				auth2AuthorizedClientRepository);

		cm.setAuthorizedClientProvider(provider);

		return cm;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.toString().equals(encodedPassword);
			}
		};
	}

}
