package com.jpetstore.jpetstore.domain.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jpetstore.jpetstore.domain.model.Order;

@Component
public class OrderServiceFallback implements OrderService {

	@Override
	public int insertOrder(Order order) {
		return -1;
	}

	@Override
	public Order getOrder(int orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		return order;
	}

	@Override
	public List<Order> getOrdersByUsername(String username) {
		return new ArrayList<>();
	}

}
