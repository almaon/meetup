package com.example.meetup.gateway.infrastructure;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WebEndpoints {

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
				.GET("/meetup-fallback", request ->
//						ServerResponse.ok().body(Mono.just(""), String.class))
						ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build())
				.POST("/meetup-fallback", request ->
						ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build())
				.build();
	}
	
}