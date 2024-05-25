package online_store.util.comparators;

import java.util.Comparator;

import online_store.entities.Product;

public class CustomProductComparator implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		int sortByCategory = o1.getCategoryName().compareToIgnoreCase(o2.getCategoryName());
        int sortByNumber = Double.compare(o1.getPrice(),o2.getPrice());
        int sortByName = o1.getProductName().compareToIgnoreCase(o2.getProductName());
        
        return (sortByCategory!=0) ? sortByCategory:(sortByNumber!=0) ? sortByNumber:sortByName;
	}

}
