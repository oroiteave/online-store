package com.magicbaits.core.services.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magicbaits.core.exceptions.InsufficientStockException;
import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.PurchaseFacade;
import com.magicbaits.core.services.PurchaseService;
import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.impl.DefaultPurchase;

@Service
public class DefaultPurchaseService implements PurchaseService{

	@Autowired
    private ProductFacade productFacade;

	@Autowired
    private PurchaseFacade purchaseFacade;
    
    @Transactional(rollbackFor = Exception.class)
	@Override
	public void purchaseProduct(int userId, int productId, Address address, String shippingCompany,
			String extraMessage) {
		boolean stockDecremented = productFacade.decrementStock(productId);
        if (!stockDecremented) {
            throw new InsufficientStockException("El producto está agotado");
        }

        // Obtener el producto actualizado
        Product product = productFacade.getProductById(productId);

        // Crear la compra
        Purchase purchase = new DefaultPurchase();
        purchase.setCustomerId(userId);
        purchase.setShippingCompany(shippingCompany);
        purchase.setExtraMessage(extraMessage);
        purchase.setStatus("CONFIRMADO");
        purchase.setProducts(Collections.singletonList(product));

        if (address != null) {
            purchase.setAddress(address);
        }

        if (!purchaseFacade.addPurchase(purchase)) {
            throw new RuntimeException("No se pudo registrar la compra");
        }
	}
}
