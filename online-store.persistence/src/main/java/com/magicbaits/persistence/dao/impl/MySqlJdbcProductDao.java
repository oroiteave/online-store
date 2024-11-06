package com.magicbaits.persistence.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public boolean saveProduct(ProductDto product,int categoryId) {
		try(var conn = DBUtils.getConnection();
				var psProduct = conn.prepareStatement("INSERT INTO product (product_name, price, category_id, image_name, description) VALUES (?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS)){
			psProduct.setString(1, product.getProductName());
			psProduct.setBigDecimal(2, product.getPrice());
			psProduct.setInt(3,categoryId);
			psProduct.setString(4, product.getImgName());
			psProduct.setString(5, product.getDescription());
			
			int rowsAffected = psProduct.executeUpdate();
	        if (rowsAffected == 0) {
	            throw new SQLException("Failed to insert product, no rows affected.");
	        }

	        try (var generatedKeys = psProduct.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int productId = generatedKeys.getInt(1);

	                var psInventory = conn.prepareStatement(
	                    "INSERT INTO inventory (product_id, stock) VALUES (?, ?);"
	                );
	                psInventory.setInt(1, productId);
	                psInventory.setInt(2, product.getStock());
	                psInventory.executeUpdate();
	            } else {
	                throw new SQLException("Failed to retrieve product ID.");
	            }
	        }
			
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
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
					products.add(parseProductDtoFromResultSet(rs));
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
					products.add(parseProductDtoFromResultSet(rs));
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
		try (var conn = DBUtils.getConnection();
		         var psInventory = conn.prepareStatement(
		                 "SELECT i.stock FROM inventory i WHERE i.product_id = ?")) {
		        psInventory.setInt(1, product.getId());

		        try (var rsInventory = psInventory.executeQuery()) {
		            if (rsInventory.next()) {
		                product.setStock(rsInventory.getInt("stock"));
		            }
		        }
		    }
		return product;
	}

	@Override
	public List<ProductDto> getProductsByPaginationLimit(int page, int paginationLimit) {
		try (var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM product LIMIT ?, ?")) {

			ps.setInt(1, ((paginationLimit * page) - paginationLimit)); // offset - number of pages multiplied by limit and minus items on the page to show products from current page
			ps.setInt(2, paginationLimit);

			try (var rs = ps.executeQuery()) {
				List<ProductDto> products = new ArrayList<>();

				while (rs.next()) {
					products.add(parseProductDtoFromResultSet(rs));
				}

				return products;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getProductCount() {
		try (var conn = DBUtils.getConnection();
				var ps = conn
						.prepareStatement("SELECT  COUNT(id) as amount FROM product");) {

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
	public boolean updateProduct(ProductDto product) {
		Connection conn = null;
	    try {
	        conn = DBUtils.getConnection();
	        conn.setAutoCommit(false);

	        try (var psProduct = conn.prepareStatement(
	                "UPDATE product SET product_name = ?, price = ?, description = ? WHERE id = ?")) {
	            psProduct.setString(1, product.getProductName());
	            psProduct.setBigDecimal(2, product.getPrice());
	            psProduct.setString(3, product.getDescription());
	            psProduct.setInt(4, product.getId());
	            psProduct.executeUpdate();
	        }

	        try (var psInventory = conn.prepareStatement(
	                "UPDATE inventory SET stock = ? WHERE product_id = ?")) {
	            psInventory.setInt(1, product.getStock());
	            psInventory.setInt(2, product.getId());
	            psInventory.executeUpdate();
	        }

	        conn.commit();
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
	    }
	    return false;
	}

	@Override
	public boolean deleteProduct(int productId) {
		Connection conn = null;
	    try {
	        conn = DBUtils.getConnection();
	        conn.setAutoCommit(false);

	        try (var psInventory = conn.prepareStatement("DELETE FROM inventory WHERE product_id = ?")) {
	        	psInventory.setInt(1, productId);
	        	psInventory.executeUpdate();
	        }
	        
	        try (var psProduct = conn.prepareStatement("DELETE FROM product WHERE id = ?")) {
	            psProduct.setInt(1, productId);
	            psProduct.executeUpdate();
	        }


	        conn.commit();
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
	    }
	    return false;
	}
}
