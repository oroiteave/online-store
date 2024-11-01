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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.impl.DefaultCategoryFacade;
import com.magicbaits.core.facades.impl.DefaultProductFacade;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.impl.DefaultProduct;

@RestController
@RequestMapping("/product")
public class ProductController {
	private final int PAGINATION_LIMIT = 6;
	private ProductFacade productFacade;
	private CategoryFacade categoryFacade;
	
	{
		productFacade = DefaultProductFacade.getInstance();
		categoryFacade = DefaultCategoryFacade.getInstance();
	}
	
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
	
	@GetMapping("/category")
	public Map<String,Object> getProductsByCategory(@RequestParam String id,@RequestParam String page){
		Map<String,Object> response = new HashMap<>();
		response.put("products", productFacade.getProductsByCategoryIdForPageWithLimit(Integer.parseInt(id), Integer.parseInt(page), PAGINATION_LIMIT));
		response.put("numberOfPages", productFacade.getNumberOfPagesForCategory(Integer.parseInt(id), PAGINATION_LIMIT));
		return response;
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestParam String productName, @RequestParam String price, @RequestParam MultipartFile productImage,
			@RequestParam String description, @RequestParam String category) {
		
		try {
			Product product = new DefaultProduct();
			
			String imageName = saveImage(productImage);
			
			product.setProductName(productName);
			product.setPrice(Double.parseDouble(price));
			product.setCategoryName(categoryFacade.getCategoryById(Integer.parseInt(category)).getCategoryName());
			product.setDescription(description);
			product.setImgName(imageName);
			System.out.println(product.toString());
			
			productFacade.addProduct(product,Integer.parseInt(category));
			
			return ResponseEntity.ok("Producto agregado con éxito");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto");
		}
	}
	
	private String saveImage(MultipartFile imageFile)throws IOException {
		String folder = "C:/Users/oroi/proyectos/webs/backend/product-images/";
		
		 // Obtener el nombre original del archivo
        String originalFilename = imageFile.getOriginalFilename();

        // Generar un nombre de archivo único para evitar colisiones
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // Ruta completa al archivo
        Path path = Paths.get(folder + uniqueFilename);

        // Crear el directorio si no existe
        Files.createDirectories(path.getParent());

        // Guardar el archivo en el sistema de archivos
        Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Retornar el nombre del archivo para guardarlo en el producto
        return uniqueFilename;
	}
}