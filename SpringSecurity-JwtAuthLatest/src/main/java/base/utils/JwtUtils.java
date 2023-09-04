package base.utils;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	private String secret = "secret";
	private String issuer = "issuer";

	private String getToken(String subject) {
		return (subject == null || subject.isEmpty()) ? null
				: Jwts.builder().setSubject(subject).setIssuer(issuer).setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12)))
						.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public String generateToken(Authentication authentication) {
		UserDetails principal = (UserDetails) authentication.getPrincipal();

		return getToken(principal.getUsername());
	}

	public String generateToken(String subject) {
		try {
			return getToken(subject);
		} catch (Exception e) {
			LOGGER.error("Error: couldn't generate token");
			e.printStackTrace();
			return null;
		}
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			LOGGER.error("Invalid signature!");
			return null;
		} catch (MalformedJwtException e) {
			LOGGER.error("token malformed!");
			return null;
		} catch (ExpiredJwtException e) {
			LOGGER.error("token expired!");
			e.printStackTrace();
			return null;
		} catch (UnsupportedJwtException e) {
			LOGGER.error("token unsupported!");
			return null;
		} catch (IllegalArgumentException e) {
			LOGGER.error("Claims String is empty!");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getUsernameFromToken(String token) {
		try {
			if (token != null && !token.isEmpty() && isValidToken(token)) {
				String subject = getClaims(token).getSubject();

//				LOGGER.info("Email from token : {}", subject);
				return subject;
			} else
				return null;
		} catch (ExpiredJwtException e) {
			LOGGER.error("ERROR:ExpiredJwtException Getting email from TOKEN, {} ", e.getMessage());
//			throw new TokenExpiredException(e.getMessage());
			return null;
		} catch (Exception e) {
			LOGGER.error("ERROR:Exception Getting email from TOKEN, {}", e.getMessage());
			return null;
		}
	}

	public boolean isValidToken(String token) {
		try {
			Claims claims = getClaims(token);
			// extDate > CurrDate
			return (claims != null && claims.getExpiration().after(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean validateToken(String token) {
		try {
			return isValidToken(token);
		} catch (SignatureException e) {
			LOGGER.error("Invalid signature!");
			return false;
		} catch (MalformedJwtException e) {
			LOGGER.error("token malformed!");
			return false;
		} catch (ExpiredJwtException e) {
			LOGGER.error("Token Expired!");
			e.printStackTrace();
			throw new RuntimeException("Token Expired. Please login again.");
		} catch (UnsupportedJwtException e) {
			LOGGER.error("token unsupported!");
			return false;
		} catch (IllegalArgumentException e) {
			LOGGER.error("Claims String is empty!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
