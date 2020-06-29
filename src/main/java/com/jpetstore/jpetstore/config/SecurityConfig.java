package com.jpetstore.jpetstore.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "userDetailsService")
	UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 페이지 권한 설정
				.antMatchers("/account/editAccount*").authenticated()
				.antMatchers("/order/**").authenticated()
				.antMatchers("/**").permitAll().and()
				.csrf().disable()
				.formLogin()
					.loginPage("/account/signonForm")
					.loginProcessingUrl("/account/signon")
					.usernameParameter("j_username")
					.passwordParameter("j_password")
					.failureUrl("/account/signonForm?error=true").and()
				.logout()
					.deleteCookies("JSESSIONID")
					.logoutUrl("/account/signoff")
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}