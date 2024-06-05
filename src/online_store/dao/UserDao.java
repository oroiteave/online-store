package online_store.dao;

import java.util.List;

import online_store.dto.UserDto;

public interface UserDao {
	
	boolean saveUser(UserDto user);
	
	List<UserDto> getUsers();

	UserDto getUserByEmail(String userEmail);

	UserDto getUserById(int id);

}
