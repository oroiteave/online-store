package online_store.dao;

import online_store.dto.CategoryDto;

public interface CategoryDao {

	CategoryDto getCategoryByCategoryId(int id);
}
