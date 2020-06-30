/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.jpetstore.jpetstore.domain.service.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpetstore.jpetstore.domain.model.Category;
import com.jpetstore.jpetstore.domain.model.Item;
import com.jpetstore.jpetstore.domain.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author Eduardo Macarron
 */
@EnableCircuitBreaker
@RefreshScope
@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${product.service.url}")
	private String PRODUCT_SERVICE_URL;

	@SuppressWarnings("unchecked")
	@HystrixCommand(fallbackMethod = "getCategoryListFallback")
	@Override
	public List<Category> getCategoryList() {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/categories", List.class);
	}

	@SuppressWarnings("unused")
	private List<Category> getCategoryListFallback() {
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "getCategoryFallback",
			commandProperties = {
		            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
		            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
		            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
		            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
		            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
		        })
	@Override
	public Category getCategory(String categoryId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/categories/" + categoryId, Category.class);
	}

	@SuppressWarnings("unused")
	private Category getCategoryFallback(String categoryId) {
		Category category = new Category();
		category.setCategoryId(categoryId);
		return category;
	}

	@HystrixCommand(fallbackMethod = "getProductFallback")
	@Override
	public Product getProduct(String productId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/" + productId, Product.class);
	}

	@SuppressWarnings("unused")
	private Product getProductFallback(String productId) {
		Product product = new Product();
		product.setProductId(productId);
		return product;
	}

	@HystrixCommand(fallbackMethod = "getProductListByCategoryFallback",
			commandProperties = {
		            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
		            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
		            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
		            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
		            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
		        })
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductListByCategory(String categoryId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/categories/" + categoryId + "/products", List.class);
	}

	@SuppressWarnings("unused")
	private List<Product> getProductListByCategoryFallback(String categoryId) {
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "searchProductListFallback")
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchProductList(String keywords) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products?keywords=" + keywords, List.class);
	}

	@SuppressWarnings("unused")
	private List<Product> searchProductListFallback(String keywords) {
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "getItemListByProductFallback")
	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItemListByProduct(String productId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/" + productId + "/items", List.class);
	}

	@SuppressWarnings("unused")
	private List<Item> getItemListByProductFallback(String productId) {
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "getItemFallback")
	@Override
	public Item getItem(String itemId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/items/" + itemId, Item.class);
	}

	@SuppressWarnings("unused")
	private Item getItemFallback(String itemId) {

		Item item = new Item();
		item.setItemId(itemId);
		return item;
	}

	@HystrixCommand(fallbackMethod = "isItemInStockFallback")
	@Override
	public boolean isItemInStock(String itemId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/items/" + itemId + "/instock", Boolean.class);
	}

	@SuppressWarnings("unused")
	private boolean isItemInStockFallback(String itemId) {
		return false;
	}
}
