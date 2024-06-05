package online_store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import online_store.dao.RoleDao;
import online_store.dao.UserDao;
import online_store.dto.UserDto;
import online_store.util.db.DBUtils;

public class MySqlJdbcUserDao implements UserDao{
	
	private RoleDao role;
	
	{
		role = new MySqlJdbcRoleDao();
	}

	@Override
	public boolean saveUser(UserDto user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserDto> getUsers() {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM user")){
			
			try(var rs = ps.executeQuery()){
				List<UserDto> users = new ArrayList<>();
				while(rs.next()) {
					UserDto user = new UserDto();
					user.setId(rs.getInt("id"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setEmail(rs.getString("email"));
					user.setMoney(rs.getBigDecimal("money"));
					user.setCreditCard(rs.getString("credit_card"));
					user.setRoleDto(role.getRoleById(rs.getInt("fk_user_role")));
					users.add(user);
				}
				return users;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDto getUserByEmail(String userEmail) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM user WHERE email = '?'")){
			
			ps.setString(1, userEmail);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					UserDto user = new UserDto();
					user.setId(rs.getInt("id"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setEmail(rs.getString("email"));
					user.setMoney(rs.getBigDecimal("money"));
					user.setCreditCard(rs.getString("credit_card"));
					user.setRoleDto(role.getRoleById(rs.getInt("fk_user_role")));
					return user;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDto getUserById(int id) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?")){
			
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					UserDto user = new UserDto();
					user.setId(rs.getInt("id"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setEmail(rs.getString("email"));
					user.setMoney(rs.getBigDecimal("money"));
					user.setCreditCard(rs.getString("credit_card"));
					user.setRoleDto(role.getRoleById(rs.getInt("fk_user_role")));
					return user;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
