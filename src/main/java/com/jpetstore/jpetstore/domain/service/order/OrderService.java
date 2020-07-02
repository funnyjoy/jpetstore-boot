package com.jpetstore.jpetstore.domain.service.order;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpetstore.jpetstore.domain.model.Order;

@RefreshScope
@FeignClient(name = "order-api", fallback = OrderServiceFallback.class)
@Service
public interface OrderService {

	@PutMapping("/orders")
	int insertOrder(Order order);

	@GetMapping("/orders/{orderId}")
	Order getOrder(@PathVariable("orderId") int orderId);

	@GetMapping("/orders")
	List<Order> getOrdersByUsername(@RequestParam("username") String username);

}
