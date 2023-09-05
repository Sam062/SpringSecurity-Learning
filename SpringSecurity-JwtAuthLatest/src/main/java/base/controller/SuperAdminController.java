package base.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/su")
@CrossOrigin("*")
public class SuperAdminController {
	
	@GetMapping("/")
	public String superAdminHome() {
		return "Super admin access";
	}

}
