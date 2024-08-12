package com.magicbaits.persistence.dao.impl;

import java.io.PrintWriter;
import java.sql.SQLException;

import com.magicbaits.persistence.dao.AddressDao;
import com.magicbaits.persistence.dao.UserDao;
import com.magicbaits.persistence.dto.AddressDto;
import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.utils.DBUtils;

public class MySqlJdbcAddressDao implements AddressDao{
	UserDao userDao;
	
	{
		userDao = new MySqlJdbcUserDao();
	}

	@Override
	public boolean saveAddress(AddressDto address) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("INSERT INTO address (fk_adress_user, shipping_company, direction_1, direction_2, city, number, postal_code,"
						+ " extra_message, phone_number) VALUES(?,?,?,?,?,?,?,?,?);")){
			
			if(address.getUser() != null) {
				ps.setInt(1, address.getUser().getId());
			}else {ps.setNull(1, java.sql.Types.NULL);}
			
			ps.setString(2, address.getShippingCompany());
			
			if(address.getFirstDirection()!=null) {
				ps.setString(3, address.getFirstDirection());
			}else {ps.setNull(3, java.sql.Types.NULL);}
			
			if(address.getSecondDirection()!=null) {
				ps.setString(4, address.getSecondDirection());
			}else {ps.setNull(4, java.sql.Types.NULL);}
			
			ps.setString(5, address.getCity());
			ps.setInt(6, address.getHouseNumber());
			ps.setInt(7, address.getPostalCode());
			
			if(address.getExtraMessage()!=null) {
				ps.setString(8, address.getExtraMessage());
			}else {ps.setNull(8, java.sql.Types.NULL);}
			
			if(address.getPhoneNumber()!=null) {
				ps.setString(9, address.getPhoneNumber());
			}else {ps.setNull(9, java.sql.Types.NULL);}
			
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public AddressDto getAddressByPurchaseId(int purchaseId) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT a.* FROM address a JOIN purchase p ON p.fk_purchase_address = a.id WHERE p.id = ? ;")){
			
			ps.setInt(1, purchaseId);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					AddressDto addressDto = new AddressDto();
					addressDto.setId(rs.getInt("id"));
					addressDto.setUser(userDao.getUserById(rs.getInt("fk_adress_user")));
					addressDto.setShippingCompany(rs.getString("shipping_company"));
					addressDto.setFirstDirection(rs.getString("direction_1"));
					addressDto.setSecondDirection(rs.getString("direction_2"));
					addressDto.setCity(rs.getString("city"));
					addressDto.setHouseNumber(rs.getInt("number"));
					addressDto.setPostalCode(rs.getInt("postal_code"));
					addressDto.setExtraMessage(rs.getString("extra_message"));
					addressDto.setPhoneNumber(rs.getString("phone_number"));
					return addressDto;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
