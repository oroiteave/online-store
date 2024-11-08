package com.magicbaits.core.facades.impl;

import static com.magicbaits.persistence.dto.RoleDto.CUSTOMER_ROLE_NAME;

import java.util.List;

import org.springframework.stereotype.Service;

import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.services.AffiliateMarketingService;
import com.magicbaits.persistence.dao.UserDao;
import com.magicbaits.persistence.dto.converter.UserDtoToUserConverter;
import com.magicbaits.persistence.enteties.User;

@Service
public class DefaultUserFacade implements UserFacade{
	
	private final UserDao userDao;
	private final UserDtoToUserConverter userConverter;
	private final AffiliateMarketingService marketingService;
	
	public DefaultUserFacade(UserDao userDao, UserDtoToUserConverter userConverter,
			AffiliateMarketingService marketingService) {
		this.userDao = userDao;
		this.userConverter = userConverter;
		this.marketingService = marketingService;
	}

	@Override
	public void registerUser(User user,String referrerCode) {
		user.setRoleName(CUSTOMER_ROLE_NAME);
		user.setPartnerCode(marketingService.generateUniquePartnerCode());
		user.setReferrerUser(userConverter.convertUserDtoToUser(userDao.getUserByPartnerCode(referrerCode)));
		
		userDao.saveUser(userConverter.convertUserToUserDto(user));
	}

	@Override
	public User getUserByEmail(String email) {
		return userConverter.convertUserDtoToUser(userDao.getUserByEmail(email));
	}

	@Override
	public List<User> getUsers() {
		return userConverter.convertUserDtosToUsers(userDao.getUsers());
	}

	@Override
	public User getUserByid(int userId) {
		return userConverter.convertUserDtoToUser(userDao.getUserById(userId));
	}

	@Override
	public void updateUser(User referrerUser) {
		userDao.updateUser(userConverter.convertUserToUserDto(referrerUser));
	}

	@Override
	public List<User> getReferralsForUser(User loggedInUser) {
		return userConverter.convertUserDtosToUsers(userDao.getReferralsByUserId(loggedInUser.getId()));
	}

	@Override
	public List<String> getUserEmailsForPurchasesPageWithLimit(int page, int paginationLimit) {
		return userDao.getUserEmailsForPurchasesPaginationLimit(page, paginationLimit);
	}
}
