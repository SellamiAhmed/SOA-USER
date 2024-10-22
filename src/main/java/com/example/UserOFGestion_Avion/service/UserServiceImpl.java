package com.example.UserOFGestion_Avion.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserOFGestion_Avion.entities.Role;
import com.example.UserOFGestion_Avion.entities.User;
import com.example.UserOFGestion_Avion.repos.RoleRepository;
import com.example.UserOFGestion_Avion.repos.UserRepository;
import com.example.UserOFGestion_Avion.service.register.RegistrationRequest;
import com.example.UserOFGestion_Avion.service.register.VerificationToken;
import com.example.UserOFGestion_Avion.service.register.exception.EmailAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRep;
    @Autowired
    RoleRepository roleRep;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }
    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        Role r = roleRep.findByRole(rolename);
        usr.getRoles().add(r);
        return usr;
    }

    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }
    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }
    
    
	@Override
	public User registerUser(RegistrationRequest request) {

		Optional<User>  optionalUser = userRep.findByEmail(request.getEmail());
		if(optionalUser.isPresent())
			throw new EmailAlreadyExistsException("Email déjà existant!");
		
		User newUser = new User();
		newUser.setUsername(request.getUsername());
		newUser.setEmail(request.getEmail());
		
		newUser.setPassword( bCryptPasswordEncoder.encode( request.getPassword() )  );
		newUser.setEnabled(false);
		
		userRep.save(newUser);
		
		Role r = roleRep.findByRole("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		newUser.setRoles(roles);
		


		 


		return userRep.save(newUser);
	}

}