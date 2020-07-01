package com.jpetstore.jpetstore.domain.service.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jpetstore.jpetstore.domain.model.Category;
import com.jpetstore.jpetstore.domain.model.Item;
import com.jpetstore.jpetstore.domain.model.Product;

@Component
public class CatalogServiceFallback implements CatalogService {

	public List<Category> getCategoryList() {
		return new ArrayList<>();
	}

	public Category getCategory(String categoryId) {
		Category category = new Category();
		category.setCategoryId(categoryId);
		return category;
	}

	public Product getProduct(String productId) {
		Product product = new Product();
		product.setProductId(productId);
		return product;
	}

	public List<Product> getProductListByCategory(String categoryId) {
		return new ArrayList<>();
	}

	public List<Product> searchProductList(String keywords) {
		return new ArrayList<>();
	}

	public List<Item> getItemListByProduct(String productId) {
		return new ArrayList<>();
	}

	public Item getItem(String itemId) {

		Item item = new Item();
		item.setItemId(itemId);
		return item;
	}

	public boolean isItemInStock(String itemId) {
		return false;
	}
}
