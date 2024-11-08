package com.magicbaits.persistence.dto.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.magicbaits.persistence.dto.UserDto;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.persistence.enteties.impl.DefaultUser;

@Component
public class UserDtoToUserConverter {
	@Autowired
	private RoleDtoToRoleConverter roleConverter;

	public UserDto convertUserIdToUserDtoWithOnlyId(int customerId) {
		UserDto userDto = new UserDto();
		userDto.setId(customerId);
		return userDto;
	}
	
	public User convertUserDtoToUser(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		User user = new DefaultUser();
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		if (userDto.getRoleDto() != null) {
			user.setRoleName(userDto.getRoleDto().getRoleName());
		}
		user.setMoney(userDto.getMoney().doubleValue());
		user.setPartnerCode(userDto.getPartnerCode());
		
		return user;
	}
	
	public UserDto convertUserToUserDto(User user) {
		if(user == null) {
			return null;
		}
		
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setPassword(user.getPassword());
		userDto.setRoleDto(roleConverter.convertRoleNameToRoleDtoWithOnlyRoleName(user.getRoleName()));
		userDto.setMoney(BigDecimal.valueOf(user.getMoney()));
		userDto.setPartnerCode(user.getPartnerCode());
		userDto.setReferrerUser(convertUserToUserDto(user.getReferrerUser()));
		
		return userDto;
	}

	public List<User> convertUserDtosToUsers(List<UserDto> userDtos) {
		List<User> users = new ArrayList<>();
		
		for (UserDto userDto : userDtos) {
			users.add(convertUserDtoToUser(userDto));
		}
		return users;
	}

}
