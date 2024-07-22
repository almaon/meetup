package com.example.meetup.infrastructure.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev | prod")
public class PasswordEncoderAccessor {

	public static PasswordEncoder encoder;
	
	public PasswordEncoderAccessor(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
}
