package com.magicbaits.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.magicbaits.persistence.dao.CategoryDao;
import com.magicbaits.persistence.dao.ProductDao;
import com.magicbaits.persistence.dto.ProductDto;
import com.magicbaits.persistence.utils.DBUtils;

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
					return parseProductDtoFromResultSet(rs);
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
					products.add(parseProductDtoFromResultSet(rs));
				}
				return products;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductDto> getProductsLikeName(String searchQuery) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%',?,'%'))")){
			List<ProductDto> products = new ArrayList<>();
			ps.setString(1,searchQuery);
			
			try(var rs = ps.executeQuery()){
				while(rs.next()) {
					products.add(parseProductDtoFromResultSet(rs));
				}
				return products;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductDto> getProductsByCategoryId(int id) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product WHERE category_id = ?")){
			List<ProductDto> products = new ArrayList<>();
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				while(rs.next()) {
					products.add(parseProductDtoFromResultSet(rs));
				}
				return products;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductDto> getProductsByCategoryIdPaginationLimit(int categoryId, int page, int paginationLimit) {
		try (var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product WHERE category_id = ? LIMIT ?, ?")) {

			ps.setInt(1, categoryId);
			ps.setInt(2, ((paginationLimit * page) - paginationLimit)); // offset - number of pages multiplied by limit and minus items on the page to show products from current page
			ps.setInt(3, paginationLimit);

			try (var rs = ps.executeQuery()) {
				List<ProductDto> products = new ArrayList<>();

				while (rs.next()) {
					ProductDto product = parseProductDtoFromResultSet(rs);
					products.add(product);
				}

				return products;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getProductCountForCategory(int categoryId) {
		try (var conn = DBUtils.getConnection();
				var ps = conn
						.prepareStatement("SELECT  COUNT(id) as amount FROM product WHERE category_id=?");) {

			ps.setInt(1, categoryId);

			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("amount");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getProductCountForSearch(String searchQuery) {
		try (var conn = DBUtils.getConnection();
				var ps = conn
						.prepareStatement("SELECT COUNT(id) as amount FROM product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%',?,'%'));");) {

			ps.setString(1, searchQuery);

			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("amount");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ProductDto> getProductsLikeNameForPageWithLimit(String searchQuery, int page, int paginationLimit) {
		try (var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%',?,'%')) LIMIT ?, ?");) {

			ps.setString(1, searchQuery);
			ps.setInt(2, ((paginationLimit * page) - paginationLimit)); // offset - number of pages multiplied by limit and minus items on the page to show products from current page
			ps.setInt(3, paginationLimit);

			try (var rs = ps.executeQuery()) {
				List<ProductDto> products = new ArrayList<>();

				while (rs.next()) {
					ProductDto product = parseProductDtoFromResultSet(rs);
					products.add(product);
				}

				return products;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ProductDto parseProductDtoFromResultSet(ResultSet rs) throws SQLException{
		ProductDto product = new ProductDto();
		
		product.setId(rs.getInt("id"));
		product.setCategoryDto(categoryDao.getCategoryByCategoryId(rs.getInt("category_id")));
		product.setProductName(rs.getString("product_name"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setImgName(rs.getString("image_name"));
		product.setDescription(rs.getString("description"));
		
		return product;
	}
}
