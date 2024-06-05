package online_store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import online_store.dao.ProductDao;
import online_store.dao.PurchaseDao;
import online_store.dao.UserDao;
import online_store.dto.ProductDto;
import online_store.dto.PurchaseDto;
import online_store.util.db.DBUtils;

public class MySqlJdbcPurchaseDao implements PurchaseDao{
	
	private ProductDao product;
	private UserDao user;
	
	{
		product = new MySqlJdbcProductDao();
		user = new MySqlJdbcUserDao();
	}

	@Override
	public void savePurchase(PurchaseDto purchase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PurchaseDto> getPurchaces() {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT u.id AS user_id, p.id AS product_id FROM purchase p JOIN user u ON u.id  = p.fk_purchase_user")){
			
			try(var rs = ps.executeQuery()){
				List<PurchaseDto> purchases = new ArrayList<>();
				while(rs.next()) {
					PurchaseDto purchase = new PurchaseDto();
					purchase.setId(rs.getInt("purchase_id"));
					purchase.setUserDto(user.getUserById(rs.getInt("user_id")));
					
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
	public PurchaseDto getPurchasesByUserId(int id) {
		try(var conn = DBUtils.getConnection();
				var ps = conn.prepareStatement("SELECT u.id AS user_id, p.id AS purchase_id FROM purchase p JOIN user u ON u.id  = p.fk_purchase_user WHERE u.id = ?")){
			
			ps.setInt(1, id);
			
			try(var rs = ps.executeQuery()){
				if(rs.next()) {
					PurchaseDto purchase = new PurchaseDto();
					purchase.setId(rs.getInt("purchase_id"));
					purchase.setUserDto(user.getUserById(rs.getInt("user_id")));
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
					return purchase;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}