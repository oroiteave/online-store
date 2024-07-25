package com.magicbaits.persistence.dao.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.magicbaits.persistence.dao.ProductDao;
import com.magicbaits.persistence.dao.PurchaseDao;
import com.magicbaits.persistence.dao.UserDao;
import com.magicbaits.persistence.dto.ProductDto;
import com.magicbaits.persistence.dto.PurchaseDto;
import com.magicbaits.persistence.utils.DBUtils;

public class MySqlJdbcPurchaseDao implements PurchaseDao{
	
	private ProductDao product;
	private UserDao user;
	
	{
		product = new MySqlJdbcProductDao();
		user = new MySqlJdbcUserDao();
	}

	@Override
	public void savePurchase(PurchaseDto purchase) {
		try (var conn = DBUtils.getConnection(); 
				var ps = conn.prepareStatement("INSERT INTO purchase (fk_purchase_user) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
				var psPurchaseProduct = conn.prepareStatement("INSERT INTO purchase_product (purchase_id, product_id) VALUES (?, ?)")) {
			
			ps.setInt(1, purchase.getUserDto().getId());
			ps.executeUpdate();
			
			try (var generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	
	            	for (ProductDto product : purchase.getProductDtos()) {
	            		psPurchaseProduct.setLong(1, generatedKeys.getLong(1));
	            		psPurchaseProduct.setInt(2, product.getId());
	            		psPurchaseProduct.addBatch();
	            	}
	            	
	            	psPurchaseProduct.executeBatch();
	            }
	            else {
	                throw new SQLException("Creating purchase failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<PurchaseDto> getPurchaces() {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase")){
			
			List<PurchaseDto> purchases = new ArrayList<>();
			
			try(var rs = ps.executeQuery()){
				while(rs.next()) {
					PurchaseDto purchase = new PurchaseDto();
					purchase.setId(rs.getInt("id"));
					purchase.setUserDto(user.getUserById(rs.getInt("fk_purchase_user")));
					
					try(var psProducts = conn.prepareStatement("SELECT product_id FROM purchase_product WHERE purchase_id = " + purchase.getId());
							var rsProducts = psProducts.executeQuery()){
						List<ProductDto> products = new ArrayList<>();
						
						while(rsProducts.next()) {
							products.add(product.getProductByProductId(rsProducts.getInt("product_id")));
						}
						purchase.setProductDtos(products);
						purchases.add(purchase);
					}
				}
				return purchases;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PurchaseDto> getPurchasesByUserId(int id) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase WHERE fk_purchase_user = ?")){
			
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				
				List<PurchaseDto> purchases = new ArrayList<>();
				while(rs.next()) {
					
					PurchaseDto purchase = new PurchaseDto();
					purchase.setId(rs.getInt("id"));
					purchase.setUserDto(user.getUserById(id));
					
					try(var psProducts = conn.prepareStatement("SELECT product_id FROM purchase_product WHERE purchase_id = " + purchase.getId());
							var rsProducts = psProducts.executeQuery()){
						
						List<ProductDto> products = new ArrayList<>();
						while(rsProducts.next()) {
							
							ProductDto productDto = new ProductDto();
							productDto = product.getProductByProductId(rsProducts.getInt("product_id"));
							products.add(productDto);
						}
						purchase.setProductDtos(products);
					}
					purchases.add(purchase);
				}
				return purchases;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}