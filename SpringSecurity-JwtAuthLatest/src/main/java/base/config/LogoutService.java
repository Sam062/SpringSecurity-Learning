package base.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			response.setStatus(400);
			return;
		}
		String jwt = StringUtils.hasText(authHeader.split(" ")[1]) ? authHeader.split(" ")[1] : null;
		if (jwt == null || jwt.isEmpty()) {
			response.setStatus(400);
			return;
		}

		try {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
