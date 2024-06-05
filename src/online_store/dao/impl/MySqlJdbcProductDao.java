package online_store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import online_store.dao.CategoryDao;
import online_store.dao.ProductDao;
import online_store.dto.ProductDto;
import online_store.util.db.DBUtils;

public class MySqlJdbcProductDao implements ProductDao{
	
private CategoryDao categoryDao;
	
	{
		categoryDao = new MySqlJdbcCategoryDao();
	}
	
	@Override
	public ProductDto getProductByProductId(int id) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product WHERE id = ?")){
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					ProductDto product = new ProductDto();
					product.setId(rs.getInt("id"));
					product.setCategoryDto(categoryDao.getCategoryByCategoryId(rs.getInt("category_id")));
					product.setProductName(rs.getString("product_name"));
					product.setPrice(rs.getBigDecimal("price"));
					return product;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductDto> getProducts() {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product")){
				List<ProductDto> products = new ArrayList<ProductDto>();
			try(var rs = ps.executeQuery()){
				while(rs.next()) {
					ProductDto product = new ProductDto();
					product.setId(rs.getInt("id"));
					product.setCategoryDto(categoryDao.getCategoryByCategoryId(rs.getInt("category_id")));
					product.setProductName(rs.getString("product_name"));
					product.setPrice(rs.getBigDecimal("price"));
					products.add(product);
				}
				return products;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
