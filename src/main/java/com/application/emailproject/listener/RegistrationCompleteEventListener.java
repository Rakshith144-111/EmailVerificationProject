package com.application.emailproject.listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.application.emailproject.event.RegistrationCompletesEvent;
import com.application.emailproject.user.User;
import com.application.emailproject.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor								                     //Event Name here registartion completion event
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompletesEvent> {
	
	@Autowired
	private UserService userService ;
	
	private static final Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);
	//This function is to send the mail for the verification of the registered user once the registration is successful
	@Override
	public void onApplicationEvent(RegistrationCompletesEvent event) {
		
		//1.get the newly registered user
		User theUser = event.getUser();
		
		//2.Create a verification token for the user
		String verificationToken = UUID.randomUUID().toString();
		
		//3.Save the verification token for the user
		userService.saveUserVerificationToken(theUser,verificationToken);
		
		//4.Build the verification URL to be sent to the user
		String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
		
		//5.Send the email
		 log.info("click on the link to verify the registration :"+url);
		
		
	}

}
