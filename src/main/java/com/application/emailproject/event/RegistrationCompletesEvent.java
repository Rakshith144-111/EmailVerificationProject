package com.application.emailproject.event;

import org.springframework.context.ApplicationEvent;

import com.application.emailproject.user.User;

public class RegistrationCompletesEvent extends ApplicationEvent{
	
	public RegistrationCompletesEvent(User user, String applicationUrl) {
		super(user);
		this.user = user;
		this.applicationUrl = applicationUrl;
	}
	
	
	private User user;
	//url which will be sent to user for the verification of the registration
	private String applicationUrl;
	
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	
	

}
