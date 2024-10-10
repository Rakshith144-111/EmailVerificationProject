package com.application.emailproject.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.emailproject.event.RegistrationCompletesEvent;
import com.application.emailproject.registration.token.VerificationToken;
import com.application.emailproject.registration.token.VerificationTokenRepository;
import com.application.emailproject.user.User;
import com.application.emailproject.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	//
	@PostMapping
	public String register(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request)
	{
		//Bundling the user using the RegistrationRequest record 
		User user  = userService.registerUser(registrationRequest);

		//publish the event registration 
		publisher.publishEvent(new RegistrationCompletesEvent(user, applicationUrl(request)));

		return "Success! Please check your email to complete for registration Confirmation";

	}
	
	//Method to create the url for the specific user
	public  String applicationUrl(HttpServletRequest request) {
		// TODO Auto-generated method stub

		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	
	//This method will take the token from the database and then verify whether this is a valid one or not
	//So that is why we are creating the findByemail in the Verification token repo
	@GetMapping("/verifyEmail")
	public String verifyEmail(@RequestParam("token") String token)
	{
		VerificationToken theToken = tokenRepository.findByToken(token);
		if(theToken.getUser().isEnabled())
		{
			return "This account has already verified,please login";
		}
		String verificationResult = userService.validateToken(token);
		//Checking whether userService.validateToken(token) returning the "Valid"
		if(verificationResult.equalsIgnoreCase("Valid"))
		{
			return "Email verified Successfully.Now you can login to your account";
		}
		
		return "Invalid verification token"; 
		
	}


}