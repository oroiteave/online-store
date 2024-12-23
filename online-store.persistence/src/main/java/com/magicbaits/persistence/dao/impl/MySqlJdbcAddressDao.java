package com.magicbaits.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magicbaits.persistence.dao.AddressDao;
import com.magicbaits.persistence.dao.UserDao;
import com.magicbaits.persistence.dto.AddressDto;
import com.magicbaits.persistence.utils.DBUtils;

@Repository
public class MySqlJdbcAddressDao implements AddressDao{
	@Autowired
    private DBUtils dbUtils;
	
	@Autowired
	private UserDao userDao;

	@Override
	public int saveAddress(AddressDto address) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("INSERT INTO address (fk_adress_user,  direction_1, direction_2, city, number, postal_code,"
						+ " phone_number) VALUES(?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS)){
			
			if(address.getUser() != null) {
				ps.setInt(1, address.getUser().getId());
			}else {ps.setNull(1, java.sql.Types.NULL);}
			
			
			if(address.getFirstDirection()!=null) {
				ps.setString(2, address.getFirstDirection());
			}else {ps.setNull(2, java.sql.Types.NULL);}
			
			if(address.getSecondDirection()!=null) {
				ps.setString(3, address.getSecondDirection());
			}else {ps.setNull(3, java.sql.Types.NULL);}
			
			ps.setString(4, address.getCity());
			ps.setInt(5, address.getHouseNumber());
			ps.setInt(6, address.getPostalCode());
			
			if(address.getPhoneNumber()!=null) {
				ps.setString(7, address.getPhoneNumber());
			}else {ps.setNull(7, java.sql.Types.NULL);}
			
			ps.executeUpdate();
			try(var generatedKeys = ps.getGeneratedKeys()){
				if(generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public AddressDto getAddressByPurchaseId(int purchaseId) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT a.* FROM address a JOIN purchase p ON p.fk_purchase_address = a.id WHERE p.id = ? ;")){
			
			ps.setInt(1, purchaseId);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					return parseAddressFromResultSet(rs);
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AddressDto getAddressByUserId(int userId) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM address WHERE fk_adress_user = ?;")){
			
			ps.setInt(1, userId);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					return parseAddressFromResultSet(rs);
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean updateAddress(AddressDto address) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("UPDATE address SET direction_1 = ?, direction_2 = ?,"
						+ "city = ?, number = ?, postal_code = ?, phone_number = ? WHERE id = ?")){
			
			
			if(address.getFirstDirection()!=null) {
				ps.setString(1, address.getFirstDirection());
			}else {ps.setNull(1, java.sql.Types.NULL);}
			
			if(address.getSecondDirection()!=null) {
				ps.setString(2, address.getSecondDirection());
			}else {ps.setNull(2, java.sql.Types.NULL);}
			
			ps.setString(3, address.getCity());
			ps.setInt(4, address.getHouseNumber());
			ps.setInt(5, address.getPostalCode());
			
			if(address.getPhoneNumber()!=null) {
				ps.setString(6, address.getPhoneNumber());
			}else {ps.setNull(6, java.sql.Types.NULL);}
			
			ps.setInt(7, address.getId());
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean existAddressByUserId(int userId) {
		int result = 0;
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT COUNT(*) AS address_amount FROM address WHERE fk_adress_user = ?")){
			ps.setInt(1, userId);
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					result = rs.getInt("address_amount");
					if(result>0) {
						return true;
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private AddressDto parseAddressFromResultSet(ResultSet rs) {
		AddressDto addressDto = new AddressDto();
		try {
			addressDto.setId(rs.getInt("id"));
			addressDto.setUser(userDao.getUserById(rs.getInt("fk_adress_user")));
			addressDto.setFirstDirection(rs.getString("direction_1"));
			addressDto.setSecondDirection(rs.getString("direction_2"));
			addressDto.setCity(rs.getString("city"));
			addressDto.setHouseNumber(rs.getInt("number"));
			addressDto.setPostalCode(rs.getInt("postal_code"));
			addressDto.setPhoneNumber(rs.getString("phone_number"));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return addressDto;
	}
}