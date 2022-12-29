package base.config;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	private final String SECRET = "VOICERA_SECRET";

	public String generateToken(String subject) {
		try {
			return Jwts.builder().setId("VOICERA").setSubject(subject).setIssuer("VOICERA_ASSESSMENT_PORTAL_BACKEND")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)))
					.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(SECRET.getBytes())).compact();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(Base64.getEncoder().encode(SECRET.getBytes())).parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			System.out.println("token expired");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Date getExpDate(String token) {
		return getClaims(token) != null ? getClaims(token).getExpiration() : null;
	}

	public String getUserName(String token) {
		return getClaims(token) != null ? getClaims(token).getSubject() : null;
	}

	public boolean isValidToken(String token) {
		try {
			// extDate > CurrDate
			return getClaims(token) != null
					? getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()))
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean validateToken(String token, String userName) {
		String tokenUserName = getUserName(token);
		return userName.equalsIgnoreCase(tokenUserName) && isValidToken(token);
	}

}
