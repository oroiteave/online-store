package com.magicbaits.core.facades;

import java.util.List;

import com.magicbaits.persistence.enteties.User;

public interface UserFacade {
	void registerUser(User user,String referrerCode);
	User getUserByEmail(String email);
	List<User> getUsers();
	User getUserByid(int userId);
	void updateUser(User referrerUser);
	List<User> getReferralsForUser(User loggedInUser);
	List<String> getUserEmailsForPurchasesPageWithLimit(int page, int paginationLimit);
}
