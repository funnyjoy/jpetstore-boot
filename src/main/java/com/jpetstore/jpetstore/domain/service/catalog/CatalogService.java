package com.jpetstore.jpetstore.domain.service.catalog;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpetstore.jpetstore.domain.model.Category;
import com.jpetstore.jpetstore.domain.model.Item;
import com.jpetstore.jpetstore.domain.model.Product;

@RefreshScope
@FeignClient(name = "catalog-api", url = "${product.service.url}", fallback = CatalogServiceFallback.class)
@Service
public interface CatalogService {

	@GetMapping("/categories")
	List<Category> getCategoryList();

	@GetMapping("/categories/{categoryId}")
	Category getCategory(@PathVariable("categoryId") String categoryId);

	@GetMapping("/products/{productId}")
	Product getProduct(@PathVariable("productId") String productId);

	@GetMapping("/categories/{categoryId}/products")
	List<Product> getProductListByCategory(@PathVariable("categoryId") String categoryId);

	@GetMapping("/products")
	List<Product> searchProductList(@RequestParam("keywords") String keywords);

	@GetMapping("/products/{productId}/items")
	List<Item> getItemListByProduct(@PathVariable("productId") String productId);

	@GetMapping("/items/{itemId}")
	Item getItem(@PathVariable("itemId") String itemId);

	@GetMapping("/items/{itemId}/instock")
	boolean isItemInStock(@PathVariable("itemId") String itemId);

}
