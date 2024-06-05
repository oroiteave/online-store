package backendTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import online_store.dao.UserDao;
import online_store.dao.impl.MySqlJdbcUserDao;
import online_store.dto.UserDto;

class DBTtest {
	
	private UserDao userDao;
	
	{
		userDao = new MySqlJdbcUserDao();
	}

	@Test
	void testUsers() {
		//GIVEN 
		List<UserDto> users = new ArrayList<>();
		
		//WHEN
		users = userDao.getUsers();
		
		for(UserDto u: users) {
			if(u.getRoleDto() != null) {
				System.out.println(u.getId() + " -> " +u.getFirstName() + " " + u.getLastName() + " -> " + u.getEmail() + " -> " + u.getRoleDto().getRoleName());
			}else {
				System.out.println(u.getId() + " -> " +u.getFirstName() + " " + u.getLastName() + " -> " + u.getEmail() + " -> role is null");
			}
		}
	}
}
