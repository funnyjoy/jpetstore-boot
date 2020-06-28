package com.jpetstore.jpetstore.domain.service.catalog;

import java.util.List;

import com.jpetstore.jpetstore.domain.model.Category;
import com.jpetstore.jpetstore.domain.model.Item;
import com.jpetstore.jpetstore.domain.model.Product;

public interface CatalogService {

	List<Category> getCategoryList();

	Category getCategory(String categoryId);

	Product getProduct(String productId);

	List<Product> getProductListByCategory(String categoryId);

	// TODO enable using more than one keyword
	List<Product> searchProductList(String keyword);

	List<Item> getItemListByProduct(String productId);

	Item getItem(String itemId);

	boolean isItemInStock(String itemId);

}
