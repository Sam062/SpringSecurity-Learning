package base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails sam = User.withUsername("SAM").password(passwordEncoder().encode("SAM")).authorities("ADMIN", "USER", "STUDENT", "EMPLOYEE").build();
		UserDetails ad = User.withUsername("AD").password(passwordEncoder().encode("AD")).authorities("USER").build();
		UserDetails mak = User.withUsername("MAK").password(passwordEncoder().encode("MAK")).authorities("STUDENT").build();
		UserDetails sonal = User.withUsername("SONAL").password(passwordEncoder().encode("SONAL")).authorities("EMPLOYEE")
				.build();

		return new InMemoryUserDetailsManager(sam, ad, mak, sonal);

	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// URL Access Types
		httpSecurity.authorizeHttpRequests()
//			.requestMatchers("/home").permitAll()
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
				.and().exceptionHandling().accessDeniedPage("/denied"); // instead of showing whitelabel error page, we
																		// can show our own customized page.

		return httpSecurity.build();
	}

}
