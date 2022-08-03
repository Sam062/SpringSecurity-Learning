package base.security.config;

import static base.security.ApplicationPermissions.USER_WRITE;
import static base.security.ApplicationRoles.ADMIN;
import static base.security.ApplicationRoles.ADMIN_TRAINEE;
import static base.security.ApplicationRoles.USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
					.csrf().disable()
					.authorizeRequests()
					.antMatchers("/user/**").hasAnyRole(USER.name(), ADMIN.name()) // ACCESS TO BOTH USER AND ADMIN
					.antMatchers("/").hasRole(ADMIN.name()) // ACCESS TO ONLY ADMIN
					.antMatchers(HttpMethod.DELETE, "/management/**").hasAnyAuthority(USER_WRITE.getPermission())
					.antMatchers(HttpMethod.PUT, "/management/**").hasAnyAuthority(USER_WRITE.getPermission())
					.antMatchers(HttpMethod.POST, "/management/**").hasAnyAuthority(USER_WRITE.getPermission())
					.antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(ADMIN.name(),  ADMIN_TRAINEE.name(), USER.name())
					.anyRequest().authenticated()
					.and()
					.httpBasic();

		return httpSecurity.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails adminSam = User.builder().username("SAM").password(passwordEncoder.encode("SAM"))
//				.roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();
		UserDetails userAdarsh = User.builder().username("ADARSH").password(passwordEncoder.encode("ADARSH"))
//				.roles(USER.name())
				.authorities(USER.getGrantedAuthorities())
				.build();
		UserDetails adminTraineeMak = User.builder().username("MAK").password(passwordEncoder.encode("MAK"))
//				.roles(USER.name())
				.authorities(ADMIN_TRAINEE.getGrantedAuthorities())
				.build();
		return new InMemoryUserDetailsManager(adminSam, userAdarsh,adminTraineeMak);
	}

}
