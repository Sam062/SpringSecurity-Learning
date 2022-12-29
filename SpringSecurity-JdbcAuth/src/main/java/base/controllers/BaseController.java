package base.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
	
	@Autowired
	private DataSource dataSource;
	

	@RequestMapping("/home")
	public String homePage() throws SQLException {
		
//		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
//		String sam = encode.encode("SAM");
//		System.out.println(sam);
//		
//		Connection connection = dataSource.getConnection();
//		PreparedStatement statement = connection.prepareCall("INSERT INTO usertab values (20, \'SAM\',"+"\'"+sam+"\', \'ADMIN\', 1)");
//		int executeUpdate = statement.executeUpdate();
//		System.out.println(executeUpdate);
		
		return "HomePage";
	}

	@RequestMapping("/welcome")
	public String welcomePage() {
		return "WelcomePage";
	}

	@RequestMapping("/admin")
	public String adminPage() {
		return "AdminPage";
	}

	@RequestMapping("/emp")
	public String empPage() {
		return "EmpPage";
	}

	@RequestMapping("/std")
	public String stdPage() {
		return "StdPage";
	}

	@RequestMapping("/logout")
	public String logoutPage() {
		return "LogoutPage";
	}

	@RequestMapping("/denied")
	public String deniedPage() {
		return "DeniedPage";
	}

}
