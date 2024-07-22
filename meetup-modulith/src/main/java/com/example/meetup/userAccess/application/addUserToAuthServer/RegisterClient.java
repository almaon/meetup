package com.example.meetup.userAccess.application.addUserToAuthServer;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "register", url = "http://localhost:8080/")
public interface RegisterClient {

	public static record RegisterUserRequest(String login, String encodedPassword, List<String> roles) {}

	@RequestMapping(method = RequestMethod.POST, value = "/auth/register")
	void register(@RequestHeader("Authorization") String bearerToken, @RequestBody RegisterUserRequest request);

}
