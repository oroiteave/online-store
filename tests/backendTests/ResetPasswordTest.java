package backendTests;

import online_store.entities.User;
import online_store.entities.impl.DefaultUser;
import online_store.services.impl.DefaultResetPasswordService;
import online_store.util.mail.MailSender;
import online_store.services.ResetPasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ResetPasswordTest {
	
	@InjectMocks
	private ResetPasswordService resetPasswordService = new DefaultResetPasswordService();
	
	@Mock
	private MailSender mailSenderMock;

	@Captor
	private ArgumentCaptor<String> captor;


	@Test
	void shouldSendToUserEmail() {
		User user = new DefaultUser();
		String userEmail = "testemail@email.com";
		user.setEmail(userEmail);

		resetPasswordService.resetPasswordForUser(user);
		verify(mailSenderMock).sendEmail(captor.capture(), anyString());
		assertEquals(captor.getValue(), userEmail);
	}
	
	@Test
	void shouldSendPasswordInfo() {
		User user = new DefaultUser();
		String userPass = "pass";
		user.setPassword(userPass);
		
		resetPasswordService.resetPasswordForUser(user);
		
		verify(mailSenderMock).sendEmail(any(), captor.capture());
	    String[] parts = captor.getValue().split(":");
	    assertTrue(parts.length >= 2);
	    assertTrue(parts[1].trim().equals(userPass));
	}

}
