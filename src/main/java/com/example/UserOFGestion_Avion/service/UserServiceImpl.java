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
import com.example.UserOFGestion_Avion.service.register.VerificationTokenRepository;
import com.example.UserOFGestion_Avion.service.register.exception.EmailAlreadyExistsException;
import com.example.UserOFGestion_Avion.service.register.exception.ExpiredTokenException;
import com.example.UserOFGestion_Avion.service.register.exception.InvalidTokenException;
import com.example.UserOFGestion_Avion.util.EmailSender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRep;
    @Autowired
    RoleRepository roleRep;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    VerificationTokenRepository verificationTokenRepo;
    @Autowired
    EmailSender emailSender;


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
		
		Role r = roleRep.findByRole("ADMIN");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		newUser.setRoles(roles);
		String code = this.generateCode();

		VerificationToken token = new VerificationToken(code, newUser);
		verificationTokenRepo.save(token);
		
		//envoyer le code par email a l'utilisateur
		//sendEmailUser(newUser,token.getToken());
   
		sendEmailUser(newUser, code);


		return userRep.save(newUser);
	}

    	public String generateCode() {
		 Random random = new Random();
		 Integer code = 100000 + random.nextInt(900000);

		 return code.toString();
	}
	
	

	@Override
	public void sendEmailUser(User u, String code) {
	    String emailBody = "Bonjour " + u.getUsername() + ",<br><br>" +
	        "Votre code de validation est : <h1>" + code + "</h1>";
	    
	    emailSender.sendEmail(u.getEmail(), emailBody);
	}
	
	@Override
	public User validateToken(String code) {
		VerificationToken token = verificationTokenRepo.findByToken(code);
		 if(token == null){
			 throw new InvalidTokenException("Invalid Token");
		 }
	
		  User user = token.getUser();
		 Calendar calendar = Calendar.getInstance();
		 if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
			 verificationTokenRepo.delete(token);
			 throw new ExpiredTokenException("expired Token");
		 }
		 user.setEnabled(true);
		 userRep.save(user);
		 return user;
	}



}