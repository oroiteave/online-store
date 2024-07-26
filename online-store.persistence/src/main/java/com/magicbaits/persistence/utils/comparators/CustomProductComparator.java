package com.magicbaits.persistence.utils.comparators;

import java.util.Comparator;

import com.magicbaits.persistence.enteties.Product;


public class CustomProductComparator implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		int sortByCategory = o1.getCategoryName().compareToIgnoreCase(o2.getCategoryName());
        int sortByNumber = Double.compare(o1.getPrice(),o2.getPrice());
        int sortByName = o1.getProductName().compareToIgnoreCase(o2.getProductName());
        
        return (sortByCategory!=0) ? sortByCategory:(sortByNumber!=0) ? sortByNumber:sortByName;
	}

}
