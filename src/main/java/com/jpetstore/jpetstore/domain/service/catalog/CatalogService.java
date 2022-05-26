package com.jpetstore.jpetstore.domain.service.catalog;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jpetstore.jpetstore.domain.model.Item;
import com.jpetstore.jpetstore.domain.model.Product;

@RefreshScope
@FeignClient(name = "product", fallback = CatalogServiceFallback.class, url = "${feign.url.product}")
@Service
public interface CatalogService {

	@GetMapping("/categories/{categoryId}/products")
	List<Product> getProductListByCategory(@PathVariable("categoryId") String categoryId);

	@GetMapping("/items/{itemId}")
	Item getItem(@PathVariable("itemId") String itemId);

	@GetMapping("/items/{itemId}/instock")
	boolean isItemInStock(@PathVariable("itemId") String itemId);

}
