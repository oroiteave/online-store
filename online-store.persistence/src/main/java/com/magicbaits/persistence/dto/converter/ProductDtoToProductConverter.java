package com.magicbaits.persistence.dto.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.magicbaits.persistence.dto.ProductDto;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.impl.DefaultProduct;

@Component
public class ProductDtoToProductConverter {
	
	@Autowired	
	private CategoryDtoToCategoryConverter categoryConverter;

	public List<Product> convertProductDtosToProducts(List<ProductDto> productDtos) {
		List<Product> products = new ArrayList<>();
		
		if (productDtos != null) {
			for (ProductDto productDto : productDtos) {
				products.add(convertProductDtoToProduct(productDto));
			}
		}	
		
		return products;
	}

	public Product convertProductDtoToProduct(ProductDto productDto) {
		Product product = new DefaultProduct();
		if (productDto != null) {
			product.setId(productDto.getId());
			product.setPrice(productDto.getPrice().doubleValue());
			product.setProductName(productDto.getProductName());
			
			if (productDto.getCategoryDto() != null) {
				product.setCategoryName(productDto.getCategoryDto().getCategoryName());
			}
			
			product.setImgName(productDto.getImgName());
			product.setDescription(productDto.getDescription());
			product.setStock(productDto.getStock());
		}
		return product;
	}

	public List<ProductDto> convertProductsToProductDtos(List<Product> products) {
		List<ProductDto> productDtos = new ArrayList<>();
		
		for (Product product : products) {
			productDtos.add(convertProductToProductDto(product));
		}
		
		return productDtos;
	}

	public ProductDto convertProductToProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setPrice(BigDecimal.valueOf(product.getPrice()));
		productDto.setCategoryDto(categoryConverter.convertCategoryNameToCategoryDtoWithOnlyName(product.getCategoryName()));
		productDto.setProductName(product.getProductName());
		productDto.setImgName(product.getImgName());
		productDto.setDescription(product.getDescription());
		productDto.setStock(product.getStock());
		return productDto;
	}
}
