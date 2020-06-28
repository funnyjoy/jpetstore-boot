package com.jpetstore.jpetstore.app.order;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jpetstore.jpetstore.domain.model.Account;
import com.jpetstore.jpetstore.domain.model.Cart;
import com.jpetstore.jpetstore.domain.model.Order;
import com.jpetstore.jpetstore.domain.model.UserDetails;
import com.jpetstore.jpetstore.domain.service.order.OrderService;

@Component
public class OrderHelper {
	@Inject
	protected OrderService orderService;

	@Inject
	protected Mapper beanMapper;

	public Order newOrder(OrderForm orderForm, Cart cart) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = userDetails.getAccount();

		Order order = new Order();
		order.initOrder(account, cart);
		beanMapper.map(orderForm, order);
		int orderId = orderService.insertOrder(order);
		order.setOrderId(orderId);
		return order;
	}
}
