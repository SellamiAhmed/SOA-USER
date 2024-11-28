package com.example.UserOFGestion_Avion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.UserOFGestion_Avion.service.UserService;


@SpringBootApplication
public class UsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
    @Autowired
    UserService userService;
    /*
    @PostConstruct
    void init_users() {
//ajouter les rôles
        userService.addRole(new Role((long) 1,"ADMIN"));
        userService.addRole(new Role((long) 2,"USER"));
//ajouter les users
        userService.saveUser(new User((long) 1,true,"admin","123"));
        userService.saveUser(new User((long) 2,true,"ahmed","456"));        
        userService.saveUser(new User((long) 3,true,"jack","789"));
       //ajouter les rôles aux users
        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("admin", "USER");
        userService.addRoleToUser("ahmed", "USER");
        userService.addRoleToUser("jack", "USER");
    }
    */
    @Bean
    BCryptPasswordEncoder getBCE() {
        return new BCryptPasswordEncoder();
    }

}
