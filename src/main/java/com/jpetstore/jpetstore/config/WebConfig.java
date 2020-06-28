package com.jpetstore.jpetstore.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jpetstore.jpetstore.app.common.session.HttpSessionEventLoggingListener;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public HttpSessionEventLoggingListener httpSessionEventLoggingListener() {
		return new HttpSessionEventLoggingListener();
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new CharacterEncodingFilter("UTF-8", true));
		registration.addUrlPatterns("/*");
		registration.setName("CharacterEncodingFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HiddenHttpMethodFilter());
		registration.addUrlPatterns("/*");
		registration.setName("HttpMethodFilter");
		registration.setOrder(2);
		return registration;
	}

	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new DelegatingFilterProxy());
		registration.addUrlPatterns("/*");
		registration.setName("DelegatingFilterProxy");
		registration.setOrder(2);
		return registration;
	}

}
