package com.example.UserOFGestion_Avion.restControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserOFGestion_Avion.entities.User;
import com.example.UserOFGestion_Avion.service.UserService;
import com.example.UserOFGestion_Avion.service.register.RegistrationRequest;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
    @Autowired
    UserService userService;
    
    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }
    
	@PostMapping("/register")
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);
		
	}

    @GetMapping("/verifyEmail/{token}")
	 public User verifyEmail(@PathVariable("token") String token){
		return userService.validateToken(token);
	 }
}