package base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import base.entity.ERole;

@Configuration
public class SecurityConfig {

	@Bean
	public AuthenticationEntryPoint invalidUserAuthEntryPoint() {
		return new InvalidUserAuthEntryPoint();
	}

	@Bean
	public OncePerRequestFilter authTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public LogoutHandler logoutHandlerService() {
		return new LogoutHandlerService();
	}

	@Bean
	public PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncode());

		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/", "/auth/*").permitAll();
			auth.requestMatchers("/user/*").hasAnyAuthority(ERole.SUPER_USER.toString(), ERole.ADMIN.toString(),
					ERole.USER.toString());
			auth.requestMatchers("/su/*").hasAuthority(ERole.SUPER_USER.toString());
			auth.requestMatchers("/admin/*").hasAnyAuthority(ERole.SUPER_USER.toString(), ERole.ADMIN.toString())
					.anyRequest().authenticated();

		})
//				.httpBasic(Customizer.withDefaults()) // HTTP BASIC

//				.exceptionHandling(exception -> exception.authenticationEntryPoint(invalidUserAuthEntryPoint()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// register filter for 2nd request onwards
				.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class).logout(logout -> {
					logout.logoutUrl("/api/logout");
					logout.addLogoutHandler(logoutHandlerService());
					logout.logoutSuccessHandler(
							(request, response, authentication) -> SecurityContextHolder.clearContext());
				}).build();
	}

}
