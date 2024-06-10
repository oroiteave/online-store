package backendTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import online_store.dao.ProductDao;
import online_store.dao.PurchaseDao;
import online_store.dao.UserDao;
import online_store.dao.impl.MySqlJdbcProductDao;
import online_store.dao.impl.MySqlJdbcPurchaseDao;
import online_store.dao.impl.MySqlJdbcUserDao;
import online_store.dto.ProductDto;
import online_store.dto.PurchaseDto;
import online_store.dto.UserDto;
import online_store.dto.converter.ProductDtoToProductConverter;
import online_store.entities.Product;

class DBTtest {
	
	private UserDao userDao;
	private PurchaseDao purchaseDao;
	private ProductDao productDao;
	private ProductDtoToProductConverter converterProducts;
	
	{
		userDao = new MySqlJdbcUserDao();
		purchaseDao = new MySqlJdbcPurchaseDao();
		productDao = new MySqlJdbcProductDao();
		converterProducts = new ProductDtoToProductConverter(); 
	}

	@Test
	void testUsers() {
		//GIVEN 
		List<UserDto> users = new ArrayList<>();
		
		//WHEN
		users = userDao.getUsers();
		
		for(UserDto u: users) {
			if(u.getRoleDto() != null) {
				System.out.println(u.getId() + " -> " +u.getFirstName() + " " + u.getLastName() + " -> " + u.getEmail() + " -> " + u.getRoleDto().getRoleName());
			}else {
				System.out.println(u.getId() + " -> " +u.getFirstName() + " " + u.getLastName() + " -> " + u.getEmail() + " -> role is null");
			}
		}
	}
	
	@Test
	void testPurchase() {
		//GIVEN
		List<PurchaseDto> purchases = new ArrayList<>();
		
		//WHEN
		purchases = purchaseDao.getPurchaces();
		
		//THEN
		for(PurchaseDto p : purchases) {
			if(p.getUserDto() != null) {
				System.out.println(p.getId() + " " + p.getUserDto().getId());
			}else {
				System.out.println(p.getId() + " User doesn't exist anymore");
			}
		}
		
	}
	
	@Test
	void testProducts() {
		//GIVEN
		List<ProductDto> products = new ArrayList<>();
		List<Product> productss = new ArrayList<>();
		//WHEN
		products = productDao.getProducts();
		productss = converterProducts.convertProductDtosToProducts(products);
		//THEN
		for(Product p: productss) {
			System.out.println(p.getId());
		}
	}
}
