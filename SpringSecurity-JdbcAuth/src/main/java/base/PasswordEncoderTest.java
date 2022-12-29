package base;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	public static void main(String[] args) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		String sam = encode.encode("SAM");
		System.out.println(sam);
	}

}
