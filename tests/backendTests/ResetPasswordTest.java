package backendTests;

import online_store.entities.User;
import online_store.entities.impl.DefaultUser;
import online_store.services.impl.DefaultResetPasswordService;
import online_store.util.mail.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ResetPasswordTest {
	@Mock
	private MailSender mailSenderMock;

	@Captor
	private ArgumentCaptor<String> captor;
	@InjectMocks
	private DefaultResetPasswordService resetPasswordService = new DefaultResetPasswordService();


	@Test
	void shouldSendToUserEmail() {
		User user = new DefaultUser();
		String userEmail = "testemail@email.com";
		user.setEmail(userEmail);

		resetPasswordService.resetPasswordForUser(user);

		verify(mailSenderMock).sendEmail(captor.capture(), anyString());
		assertEquals(captor.getValue(), userEmail);
	}
	

}
