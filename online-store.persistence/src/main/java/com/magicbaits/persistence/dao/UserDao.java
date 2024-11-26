package com.magicbaits.persistence.dao;

import java.util.List;

import com.magicbaits.persistence.dto.UserDto;

public interface UserDao {
	
	boolean saveUser(UserDto user);
	
	List<UserDto> getUsers();

	UserDto getUserByEmail(String userEmail);

	UserDto getUserById(int id);
	
	UserDto getUserByPartnerCode(String partnerCode);
	
	void updateUser(UserDto user);
	
	List<UserDto> getReferralsByUserId(int id);

	List <String> getUserEmailsForPurchasesPaginationLimit(int page,int paginationLimit);
}
