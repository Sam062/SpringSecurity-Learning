package base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

	@RequestMapping("/home")
	public String homePage() {
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
