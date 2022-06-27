package com.jpetstore.jpetstore.domain.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class OrderServiceFallbackFactory implements FallbackFactory<OrderService> {

	Logger logger = LoggerFactory.getLogger(OrderServiceFallbackFactory.class);

	private final OrderServiceFallback orderServiceFallback;

	public OrderServiceFallbackFactory() {
		this.orderServiceFallback = new OrderServiceFallback();
	}

	@Override
	public OrderService create(Throwable cause) {
		logger.error(cause.getMessage(), cause);
		return orderServiceFallback;
	}

}
