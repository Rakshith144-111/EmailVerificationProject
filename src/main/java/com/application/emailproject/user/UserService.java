package com.application.emailproject.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.emailproject.exception.UserAlreadyExistsException;
import com.application.emailproject.registration.RegistrationRequest;
import com.application.emailproject.registration.token.VerificationToken;
import com.application.emailproject.registration.token.VerificationTokenRepository;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method 

		return userRepository.findAll();
	}

	@Override
	public User registerUser(RegistrationRequest request) {
		// TODO Auto-generated method stub
		
		Optional<User> user = this.findByEmail(request.email());
		if(user.isPresent())
		{
			throw new UserAlreadyExistsException("User with email"+request.email()+"already Exists");
		}
		var newUser = new User();
		newUser.setFirstName(request.firstName());
		newUser.setLastName(request.lastName());
		newUser.setEmail(request.email());
		newUser.setPassword(passwordEncoder.encode(request.password()));
		newUser.setRole(request.role());
		return userRepository.save(newUser);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	public void saveUserVerificationToken(User theUser, String token) {
		var verificationToken = new VerificationToken(token, theUser);
		tokenRepository.save(verificationToken);
		
	}

}
