package base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import base.filter.SecurityFilter;
import base.service.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityFilter securityFilter;

	@Autowired
	private InvalidUserAuthEntryPoint invalidUserAuthEntryPoint;

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userService)
				.passwordEncoder(passwordEncoder).and().build();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// URL Access Types
		httpSecurity
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/registerUser", "/loginUser").permitAll()
				.requestMatchers("/home").authenticated()
				.requestMatchers("/admin").hasAuthority("ADMIN")
				.requestMatchers("/emp").hasAuthority("EMPLOYEE")
				.requestMatchers("/std").hasAuthority("STUDENT")
				.anyRequest().authenticated()
				// Exception details
				.and().exceptionHandling().authenticationEntryPoint(invalidUserAuthEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// register filter for 2nd request onwards
				.and()
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
			

		return httpSecurity.build();
	}

}
