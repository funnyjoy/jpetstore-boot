package com.jpetstore.jpetstore.domain.service.order;

import java.util.List;

import com.jpetstore.jpetstore.domain.model.Order;

public interface OrderService {

	int insertOrder(Order order);

	Order getOrder(int orderId);

	List<Order> getOrdersByUsername(String username);

}
