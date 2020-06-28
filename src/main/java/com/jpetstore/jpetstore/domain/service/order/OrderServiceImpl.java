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

package com.jpetstore.jpetstore.domain.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpetstore.jpetstore.domain.model.Order;
import com.jpetstore.jpetstore.domain.service.catalog.CatalogService;

/**
 * @author Eduardo Macarron
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${order.service.url}")
	private String ORDER_SERVICE_URL;

	@Autowired
	CatalogService catalogService;

	@Override
	public int insertOrder(Order order) {
		HttpEntity<Order> requestEntity = new HttpEntity<Order>(order);
		ResponseEntity<Integer> responseEntity = restTemplate.exchange(ORDER_SERVICE_URL + "/orders", HttpMethod.PUT, requestEntity, Integer.class);
		return responseEntity.getBody().intValue();
	}

	@Override
	public Order getOrder(int orderId) {
		return restTemplate.getForObject(ORDER_SERVICE_URL + "/orders/" + orderId, Order.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersByUsername(String username) {
		return restTemplate.getForObject(ORDER_SERVICE_URL + "/orders?username=" + username, List.class);
	}

}
