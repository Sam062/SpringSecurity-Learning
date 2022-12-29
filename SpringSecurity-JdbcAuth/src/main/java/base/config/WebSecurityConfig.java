package base.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	
	@Autowired
	private DataSource datasource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public UserDetailsManager userDetailsManager() {

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(datasource);
		users.setUsersByUsernameQuery("select uname, urole, uenabled from usertab where uname=?");
		users.setAuthoritiesByUsernameQuery("select uname, upwd, uenabled from usertab where uname=?");

		return users;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// URL Access Types
		 httpSecurity.authorizeHttpRequests()
			.requestMatchers("/home").permitAll()
			.requestMatchers("/welcome").authenticated()
			.requestMatchers("/admin").hasAuthority("ADMIN")
			.requestMatchers("/emp").hasAuthority("EMPLOYEE")
			.requestMatchers("/std").hasAuthority("STUDENT")
			.anyRequest().authenticated()

				// Login Form Details
				.and().formLogin() // For Pre-defined spring boot form
				.defaultSuccessUrl("/welcome", true)

				// Logout details
				.and().logout() // By default the url is /logout, if you want to change it, use below method
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				// Exception details
				.and().exceptionHandling().accessDeniedPage("/denied"); 
		// instead of showing whitelabel error page, we can show our own customized page.

		 return httpSecurity.build();
	}

}
