package com.example.UserOFGestion_Avion.service;

import java.util.List;

import com.example.UserOFGestion_Avion.entities.Role;
import com.example.UserOFGestion_Avion.entities.User;
import com.example.UserOFGestion_Avion.service.register.RegistrationRequest;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);

    List<User> findAllUsers();
    User registerUser(RegistrationRequest request);

}