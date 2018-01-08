package com.weblearning.bookstore.services;

public interface MailService {

	public String sendEmailNotifications(String emailTo, String firstName,
			String subject, String message);
}
