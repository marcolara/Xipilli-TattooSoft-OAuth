package com.tattoosoft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/valid/**", "/valid/**/**").permitAll().and()
				.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		// .and().authorizeRequests()
		// .anyRequest().permitAll()
		// .antMatchers(HttpMethod.POST, "/valid/**",
		// "/valid/**/**").permitAll()
		// .and().csrf().disable();
	}

//	@Override
//	public void configure(final HttpSecurity http) throws Exception {
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().authorizeRequests()
//				.anyRequest().permitAll().antMatchers(HttpMethod.POST, "/valid/**").permitAll().and().csrf().disable();
//	}

	// // Remote token service
	// @Primary
	// @Bean
	// public RemoteTokenServices tokenService() {
	// final RemoteTokenServices tokenService = new RemoteTokenServices();
	// tokenService.setCheckTokenEndpointUrl("http://localhost:8081/tattoosoft-oauth-server/oauth/check_token");
	// tokenService.setClientId("fooClientIdPassword");
	// tokenService.setClientSecret("secret");
	// return tokenService;
	// }

	@Bean
	@Primary
	public ResourceServerTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

	// JWT token store
	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
	}

	// JDBC token store configuration
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
}
