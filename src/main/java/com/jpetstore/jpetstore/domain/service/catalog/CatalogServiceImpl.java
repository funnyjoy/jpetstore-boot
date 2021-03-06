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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpetstore.jpetstore.domain.model.Category;
import com.jpetstore.jpetstore.domain.model.Item;
import com.jpetstore.jpetstore.domain.model.Product;

/**
 * @author Eduardo Macarron
 */
@RefreshScope
@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${product.service.url}")
	private String PRODUCT_SERVICE_URL;

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryList() {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/categories", List.class);
	}

	@Override
	public Category getCategory(String categoryId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/categories/" + categoryId, Category.class);
	}

	@Override
	public Product getProduct(String productId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/" + productId, Product.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductListByCategory(String categoryId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/categories/" + categoryId + "/products", List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchProductList(String keywords) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products?keywords=" + keywords, List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItemListByProduct(String productId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/" + productId + "/items", List.class);
	}

	@Override
	public Item getItem(String itemId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/items/" + itemId, Item.class);
	}

	@Override
	public boolean isItemInStock(String itemId) {
		return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/items/" + itemId + "/instock", Boolean.class);
	}
}
