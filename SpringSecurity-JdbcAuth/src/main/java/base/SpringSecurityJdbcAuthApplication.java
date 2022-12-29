package base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityJdbcAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJdbcAuthApplication.class, args);
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		String sam = encode.encode("SAM");
		System.out.println(sam);
	}

}
