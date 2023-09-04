package base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.entity.Role;
import base.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String helloUser() {
		return "User level access";
	}

	@PostMapping("/saveUserRole")
	public ResponseEntity<Role> saveUserRole(@RequestBody Role roleInput) {
		Role role = userService.saveUserRole(roleInput);

		if (role != null) {
			return new ResponseEntity<>(role, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
