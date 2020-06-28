package com.jpetstore.jpetstore.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.jpetstore.jpetstore.app.common.session.HttpSessionEventLoggingListener;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	ResourceLoader resourceLoader;

	@Bean
	public HttpSessionEventLoggingListener httpSessionEventLoggingListener() {
		return new HttpSessionEventLoggingListener();
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

//	@Bean
//	public FilterRegistrationBean characterEncodingFilter() {
//
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(new CharacterEncodingFilter("UTF-8", true));
//		registration.addUrlPatterns("/*");
//		registration.setName("CharacterEncodingFilter");
//		registration.setOrder(1);
//		return registration;
//	}

	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HiddenHttpMethodFilter());
		registration.addUrlPatterns("/*");
		registration.setName("HttpMethodFilter");
		registration.setOrder(2);
		return registration;
	}

//	@Bean
//	public FilterRegistrationBean delegatingFilterProxy() {
//
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(new DelegatingFilterProxy());
//		registration.addUrlPatterns("/*");
//		registration.setName("DelegatingFilterProxy");
//		registration.setOrder(2);
//		return registration;
//	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new PageableHandlerMethodArgumentResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("locale");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		DozerBeanMapper bean = new DozerBeanMapper();

		List<String> mappingFiles = Arrays.asList("classpath*:/dozer/**/*-mapping.xml");
		bean.setMappingFiles(mappingFiles);
		return bean;
	}

	@Bean
	public RestTemplate getCustomRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(5000);
		httpRequestFactory.setReadTimeout(5000);
		return new RestTemplate(httpRequestFactory);
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage("com.jpetstore.jpetstore.domain.repository");
		return configurer;
	}
}
