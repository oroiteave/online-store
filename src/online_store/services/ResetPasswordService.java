package online_store.services;

import online_store.entities.User;

public interface ResetPasswordService {
	void resetPasswordForUser(User user);
}
