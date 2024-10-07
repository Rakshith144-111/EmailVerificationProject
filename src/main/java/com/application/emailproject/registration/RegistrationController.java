package com.application.emailproject.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.emailproject.event.RegistrationCompletesEvent;
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
	
	@PostMapping
	public String register(RegistrationRequest registrationRequest, final HttpServletRequest request)
	{
		User user  = userService.registerUser(registrationRequest);
		
		//publish the event registration 
		publisher.publishEvent(new RegistrationCompletesEvent(user, applicationUrl(request)));
		
		return "Success! Please check your email to complete for registration Confirmation";
		
	}

	public  String applicationUrl(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	

}
