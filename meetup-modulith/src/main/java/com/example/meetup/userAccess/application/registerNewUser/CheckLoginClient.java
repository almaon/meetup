package com.example.meetup.userAccess.application.registerNewUser;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "checkLogin", url = "http://localhost:8080/")
public interface CheckLoginClient {

    public static record CheckUser(String name) {}

	@RequestMapping(method = RequestMethod.POST, value = "/auth/check")
	boolean check(@RequestHeader("Authorization") String bearerToken, @RequestBody CheckUser request);

}
