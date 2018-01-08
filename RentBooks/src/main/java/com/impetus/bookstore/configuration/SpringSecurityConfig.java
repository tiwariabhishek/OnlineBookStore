package com.impetus.bookstore.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	MyUrlAuthenticationSuccessHandler mySuccessHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**", "/removeFromCart/**",
						"/isAlreadyRegistered", "/changePassword",
						"/signup/**", "/aboutus", "/",
						"/getSubscriptionDetails/", "/register", "/viewCart",
						"/requestBook", "/addToCart/**", "/logout",
						"/getSuggestions", "/search", "/store/**", "/error")
				.permitAll().antMatchers("/admin/**")
				.access("hasRole('ROLE_ADMIN')").antMatchers("/user/**")
				.access("hasRole('ROLE_USER')").anyRequest().authenticated()
				.and().formLogin().loginPage("/").failureUrl("/error")
				.successHandler(mySuccessHandler).loginProcessingUrl("/login")
				.usernameParameter("email").passwordParameter("password").and()
				.logout().logoutSuccessUrl("/").logoutUrl("/logout").and()
				.exceptionHandling().accessDeniedPage("/error").and().csrf();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
