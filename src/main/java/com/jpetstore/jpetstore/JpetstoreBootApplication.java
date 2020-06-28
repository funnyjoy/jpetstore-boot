package com.jpetstore.jpetstore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.jpetstore.jpetstore.config.MyJspConfigDescriptor;

@SpringBootApplication
public class JpetstoreBootApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JpetstoreBootApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.getJspConfigDescriptor().getJspPropertyGroups()
				.addAll((new MyJspConfigDescriptor()).getJspPropertyGroups());
	}

	public static void main(String[] args) {
		SpringApplication.run(JpetstoreBootApplication.class, args);
	}

}
