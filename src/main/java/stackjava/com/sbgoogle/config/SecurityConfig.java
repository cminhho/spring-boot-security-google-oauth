package stackjava.com.sbgoogle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password("{noop}admin")
				.roles("ADMIN")
				.and()
				.withUser("user").password("{noop}user").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

		http.authorizeRequests().antMatchers("/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.httpBasic()
				.and()
					.exceptionHandling()
					.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

		http.authorizeRequests().and().formLogin()//
				.loginProcessingUrl("/j_spring_security_login")//
				.loginPage("/login")//
				.defaultSuccessUrl("/user")//
				.failureUrl("/login?message=error")//
				.usernameParameter("username")//
				.passwordParameter("password")
				.and().logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login?message=logout");

	}
}
