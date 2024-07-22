package com.example.meetup.authorization;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    
    public static record RegisterUserRequest(String login, String encodedPassword, List<String> roles) {}

    
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserRequest user, Authentication a) {

    		userRepository.save(
    				new User(
    						UUID.randomUUID(), 
    						user.login, 
    						user.encodedPassword, 
    						user.roles)); 
    }

    
    public static record CheckUser(String name) {}

    @PostMapping("/check")
    public Boolean check(@RequestBody CheckUser toCheck) {

    	return !userRepository.findByUsername(toCheck.name).isPresent();
    }
    
}
