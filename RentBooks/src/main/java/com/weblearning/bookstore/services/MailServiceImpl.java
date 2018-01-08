package com.weblearning.bookstore.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.weblearning.bookstore.utils.PropertyConstants;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Override
	public String sendEmailNotifications(String emailTo, String firstName,
			String subject, String userMessage) {
		Properties properties = new Properties();
		properties.put(PropertyConstants.HOST, "localhost");
		properties.put(PropertyConstants.PORT, "25");
		try {
			Session session = Session.getDefaultInstance(properties);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropertyConstants.EMAILFROM));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText("Hello " + firstName + ",\n\n" + userMessage
					+ "\n\nBest Regards,\nTeam BT");
			Transport.send(message);
			return PropertyConstants.SUCCESS;
		} catch (Exception e) {
			return PropertyConstants.ERROR;
		}
	}

}
