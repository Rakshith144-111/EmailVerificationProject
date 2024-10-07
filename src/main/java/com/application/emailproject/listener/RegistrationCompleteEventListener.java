package com.application.emailproject.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.application.emailproject.event.RegistrationCompletesEvent;
import com.application.emailproject.user.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor																			 //Event Name here registartion completion event
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompletesEvent> {

	@Override
	public void onApplicationEvent(RegistrationCompletesEvent event) {
		//1.get the newly registered user
		User theUser = event.getUser();
		//2.Create a verification token for the user
		String VerificationToken = UUID.randomUUID().toString();
		//3.Save the verification token for the user
		
		//4.Build the verification URL to be sent to the user
		//5.Send the email
		
		
		
	}

}
