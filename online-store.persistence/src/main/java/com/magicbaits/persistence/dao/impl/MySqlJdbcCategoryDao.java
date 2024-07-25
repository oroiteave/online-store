package com.magicbaits.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.magicbaits.persistence.dao.CategoryDao;
import com.magicbaits.persistence.dto.CategoryDto;
import com.magicbaits.persistence.utils.DBUtils;


public class MySqlJdbcCategoryDao implements CategoryDao{
	
	@Override
	public CategoryDto getCategoryByCategoryId(int id) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM category WHERE id = ?")){
				
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					CategoryDto categoryDto = new CategoryDto();
					categoryDto.setId(rs.getInt("id"));
					categoryDto.setCategoryName(rs.getString("category_name"));
					categoryDto.setImgName(rs.getString("image_name"));
					return categoryDto;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CategoryDto> getCategories() {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM category");
				var rs = ps.executeQuery()){
			
			List<CategoryDto> categories = new ArrayList<>();
			
			while(rs.next()) {
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setId(rs.getInt("id"));
				categoryDto.setCategoryName(rs.getString("category_name"));
				categoryDto.setImgName(rs.getString("image_name"));
				categories.add(categoryDto);
			}
			return categories;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
