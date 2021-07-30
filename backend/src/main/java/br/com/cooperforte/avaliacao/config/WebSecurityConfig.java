package br.com.cooperforte.avaliacao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.cooperforte.avaliacao.api.ApiConstantes;
import br.com.cooperforte.avaliacao.api.security.CustomUserDetailsService;
import br.com.cooperforte.avaliacao.api.security.auth.RestAuthenticationEntryPoint;
import br.com.cooperforte.avaliacao.api.security.filter.AuthenticationFilter;
import br.com.cooperforte.avaliacao.api.security.filter.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
				.and().exceptionHandling() //
				.authenticationEntryPoint(restAuthenticationEntryPoint) //
				.and().authorizeRequests() //
				.antMatchers(HttpMethod.GET, "/").permitAll() //
				.antMatchers(HttpMethod.POST, ApiConstantes.API_VERSION + "/usuario").permitAll() //
				.antMatchers(HttpMethod.GET, ApiConstantes.API_VERSION + "/cep/**").permitAll() //
				.anyRequest().authenticated() //
				.and().addFilter(new AuthenticationFilter(authenticationManager())) //
				.addFilter(new AuthorizationFilter(authenticationManager()));

		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/resources/**");

		// Swagger
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");

		// H2
		web.ignoring().antMatchers("/h2-console/**");
	}

}
