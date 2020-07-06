package com.jpetstore.jpetstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
public class RedisConfig {
	/**
	 * springboot 2.0이상부터는 auto-configuration으로 redisConnectionFactory,
	 * RedisTemplate, StringTemplate빈들이 자동으로 생성되기 때문에 굳이 Configuration을 만들지 않아도 즉시
	 * 사용가능하다.
	 */
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}
}