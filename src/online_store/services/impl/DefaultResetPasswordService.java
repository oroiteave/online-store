package online_store.services.impl;

import online_store.entities.User;
import online_store.services.ResetPasswordService;
import online_store.util.mail.MailSender;

public class DefaultResetPasswordService implements ResetPasswordService{
	private MailSender mailSender;
	{
		mailSender = DefaultMailSender.getInstance();
	}
	
	public void resetPasswordForUser(User user) {
		System.out.println("Password changed succefully, please check your email");
		mailSender.sendEmail(user.getEmail(), user.getPassword());
	}
}
