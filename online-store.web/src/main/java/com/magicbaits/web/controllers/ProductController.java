package com.magicbaits.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.impl.DefaultProduct;

@RestController
@RequestMapping("/product")
public class ProductController {
	private final int PAGINATION_LIMIT = 6;
	
	@Autowired
	private ProductFacade productFacade;
	
	@Autowired
	private CategoryFacade categoryFacade;
	
	@GetMapping
	public Product getProduct(@RequestParam String id) {
		return productFacade.getProductById(Integer.parseInt(id));
	}
	
	@GetMapping("/search")
	public List<Product> getProductsLikeSearch(@RequestParam String query){
		
		List<Product> products = productFacade.getProductsLikeName(query);
		for(Product p: products) {
			System.out.println(p.getProductName());
		}
		return products;
	}
	
	@GetMapping("/products")
	public Map<String,Object> getProductsByPage(@RequestParam String page){
		Map<String,Object> response = new HashMap<>();
		response.put("products", productFacade.getProductsForPageWithLimit(Integer.parseInt(page), PAGINATION_LIMIT));
		response.put("numberOfPages", productFacade.getNumberOfPagesForAllProducts(PAGINATION_LIMIT));
		return response;
	}
	
	@GetMapping("/category")
	public Map<String,Object> getProductsByCategory(@RequestParam String id,@RequestParam String page){
		Map<String,Object> response = new HashMap<>();
		response.put("products", productFacade.getProductsByCategoryIdForPageWithLimit(Integer.parseInt(id), Integer.parseInt(page), PAGINATION_LIMIT));
		response.put("numberOfPages", productFacade.getNumberOfPagesForCategory(Integer.parseInt(id), PAGINATION_LIMIT));
		return response;
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestParam String productName, @RequestParam String price, @RequestParam MultipartFile productImage,
			@RequestParam String description, @RequestParam String category, @RequestParam String productStock) {
		
		try {
			Product product = new DefaultProduct();
			
			String imageName = saveImage(productImage);
			
			product.setProductName(productName);
			product.setPrice(Double.parseDouble(price));
			product.setCategoryName(categoryFacade.getCategoryById(Integer.parseInt(category)).getCategoryName());
			product.setDescription(description);
			product.setImgName(imageName);
			product.setStock(Integer.parseInt(productStock));
			System.out.println(product.toString());
			
			productFacade.addProduct(product,Integer.parseInt(category));
			
			return ResponseEntity.ok("Producto agregado con éxito");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody DefaultProduct product) {
		try {
	        productFacade.updateProduct(product);
	        return ResponseEntity.ok("Producto actualizado con éxito");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto");
	    }
	}
	
	@DeleteMapping("/delete")
	public String deleteProduct(@RequestParam int id) {
		return (productFacade.deleteProduct(id)) ? "Producto eliminado con exito":"Error al eliminar el producto" ;
	}
	
	private String saveImage(MultipartFile imageFile)throws IOException {
		String folder = "C:/Users/oroi/proyectos/webs/backend/product-images/";
		
        String originalFilename = imageFile.getOriginalFilename();

        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path path = Paths.get(folder + uniqueFilename);

        Files.createDirectories(path.getParent());

        Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
	}
}