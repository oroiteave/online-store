package online_store.services.impl;

import online_store.util.mail.MailSender;

public class DefaultMailSender implements MailSender{

	private static DefaultMailSender instance;
	
	private DefaultMailSender() {};
	
	public static DefaultMailSender getInstance() {
		if (instance ==null) {
			instance = new DefaultMailSender();
		}
		return instance;
	}
	
	@Override
	public void sendEmail(String sendTo, String messageToSend) {
		
	}

}
