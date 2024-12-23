package com.magicbaits.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magicbaits.persistence.dao.AddressDao;
import com.magicbaits.persistence.dao.ProductDao;
import com.magicbaits.persistence.dao.PurchaseDao;
import com.magicbaits.persistence.dao.UserDao;
import com.magicbaits.persistence.dto.ProductDto;
import com.magicbaits.persistence.dto.PurchaseDto;
import com.magicbaits.persistence.utils.DBUtils;

@Repository
public class MySqlJdbcPurchaseDao implements PurchaseDao{
	
	@Autowired
    private DBUtils dbUtils;

	@Autowired
	private ProductDao product;
	
	@Autowired
	private UserDao user;
	
	@Autowired
	private AddressDao address;

	@Override
	public boolean savePurchase(PurchaseDto purchase) {
		try (var conn = dbUtils.getConnection(); 
				var ps = conn.prepareStatement("INSERT INTO purchase (fk_purchase_user,fk_purchase_address,shipping_company,extra_message,status) VALUES (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
				var psPurchaseProduct = conn.prepareStatement("INSERT INTO purchase_product (purchase_id, product_id) VALUES (?, ?)")) {
			
			ps.setInt(1, purchase.getUserDto().getId());
			
			if(purchase.getAddressDto()!=null) {
				ps.setInt(2, purchase.getAddressDto().getId());
			}else {ps.setNull(2, java.sql.Types.NULL);}
			
			ps.setString(3, purchase.getShippingCompany());
			
			if(purchase.getExtraMessage()!=null) {
				ps.setString(4, purchase.getExtraMessage());
			}else {ps.setNull(4, java.sql.Types.NULL);}
			
			ps.setString(5, purchase.getStatus());
			
			ps.executeUpdate();
			
			try (var generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	
	            	for (ProductDto product : purchase.getProductDtos()) {
	            		psPurchaseProduct.setLong(1, generatedKeys.getLong(1));
	            		psPurchaseProduct.setInt(2, product.getId());
	            		psPurchaseProduct.addBatch();
	            	}
	            	
	            	psPurchaseProduct.executeBatch();
	            	return true;
	            }
	            else {
	                throw new SQLException("Creating purchase failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PurchaseDto> getPurchaces() {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase")){
			
			List<PurchaseDto> purchases = new ArrayList<>();
			
			try(var rs = ps.executeQuery()){
				while(rs.next()) {
					PurchaseDto purchase = parsePurchaseDtoFromResultSet(rs);
					
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
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase WHERE fk_purchase_user = ?")){
			
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				
				List<PurchaseDto> purchases = new ArrayList<>();
				while(rs.next()) {
					
					PurchaseDto purchase = parsePurchaseDtoFromResultSet(rs);
					
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

	@Override
	public boolean updatePurchase(PurchaseDto purchase) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("UPDATE purchase SET status = ? WHERE id = ?")){
			
			ps.setString(1, purchase.getStatus());
			ps.setInt(2, purchase.getId());
			
			ps.executeUpdate();
			
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public PurchaseDto getPurchaseById(int id) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase WHERE id = ?")){
			ps.setInt(1, id);
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					PurchaseDto purchase = parsePurchaseDtoFromResultSet(rs);
					
					try(var psProducts = conn.prepareStatement("SELECT product_id FROM purchase_product WHERE purchase_id = " + purchase.getId());
							var rsProducts = psProducts.executeQuery()){
						List<ProductDto> products = new ArrayList<>();
						
						while(rsProducts.next()) {
							products.add(product.getProductByProductId(rsProducts.getInt("product_id")));
						}
						purchase.setProductDtos(products);
					}
					return purchase;
					
				}
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<PurchaseDto> getPurchasePaginationLimit(int page, int paginationLimit) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase LIMIT ?,?;")){
			ps.setInt(1, ((paginationLimit * page) - paginationLimit));
			ps.setInt(2, paginationLimit);
			try(var rs = ps.executeQuery()){
				List<PurchaseDto> purchases = new ArrayList<>();
				while(rs.next()) {
					
					PurchaseDto purchase = parsePurchaseDtoFromResultSet(rs);
					
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
	
	@Override
	public int getPurchaseCount() {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT COUNT(id) as amount FROM purchase;")){
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					return rs.getInt("amount");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int getPurchaseCountByUserId(int id) {
		try(var conn =dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT COUNT(id) as amount FROM purchase WHERE fk_purchase_user = ?;")){
			ps.setInt(1, id);
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					return rs.getInt("amount");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<PurchaseDto> getPurchasePaginationLimitByUserId(int page, int paginationLimit, int userId) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("SELECT * FROM purchase WHERE fk_purchase_user = ? LIMIT ?,?;")){
			ps.setInt(1, userId);
			ps.setInt(2, ((paginationLimit * page) - paginationLimit));
			ps.setInt(3, paginationLimit);
			try(var rs = ps.executeQuery()){
				List<PurchaseDto> purchases = new ArrayList<>();
				while(rs.next()) {
					
					PurchaseDto purchase = parsePurchaseDtoFromResultSet(rs);
					
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
	
	private PurchaseDto parsePurchaseDtoFromResultSet(ResultSet rs) throws SQLException{
		PurchaseDto purchase = new PurchaseDto();
		purchase.setId(rs.getInt("id"));
		purchase.setUserDto(user.getUserById(rs.getInt("fk_purchase_user")));
		purchase.setAddressDto(address.getAddressByPurchaseId(purchase.getId()));
		purchase.setShippingCompany(rs.getString("shipping_company"));
		purchase.setExtraMessage(rs.getString("extra_message"));
		purchase.setStatus(rs.getString("status"));
		return purchase;
	}

	@Override
	public boolean deletePurchaseById(int id) {
		try(var conn = dbUtils.getConnection();
				var ps = conn.prepareStatement("DELETE FROM purchase WHERE id = ?")){
			
			ps.setInt(1, id);
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}