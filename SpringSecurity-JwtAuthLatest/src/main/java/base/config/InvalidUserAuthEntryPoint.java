package base.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InvalidUserAuthEntryPoint implements AuthenticationEntryPoint {
	private static final Logger LOG = LoggerFactory.getLogger(InvalidUserAuthEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		LOG.info("ERROR: InvalidUserAuthEntryPoint-Exception occured: {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"ERROR: InvalidUserAuthEntryPoint-Exception occured: " + authException.getMessage());

	}

}
