package base.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import base.config.JwtUtils;
import base.entity.UserEntity;
import base.model.UserRequest;
import base.model.UserResponse;
import base.service.UserService;

@RestController
public class BaseController {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/registerUser")
	public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
		System.out.println(user);

		UserEntity saveStatus = service.saveUser(user);

		return ResponseEntity.ok(saveStatus);
	}

	@PostMapping("/loginUser")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {

		// validate user details

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassword()));

		// generate JWT token
		String token = jwtUtils.generateToken(userRequest.getUserName());

		return ResponseEntity.ok(new UserResponse(token, "Success! Generated by Voicera."));

	}
	
	
	@PostMapping("/access")
	public ResponseEntity<String> accessData(Principal principal){
		return ResponseEntity.ok("Hello User : "+principal.getName());
	}
	

	@GetMapping("/admin")
	public ResponseEntity<String> admin() {
		return ResponseEntity.ok("ADMION PAGE");
	}

	@GetMapping("/home")
	public ResponseEntity<String> home() {
		return ResponseEntity.ok("HOME PAGE");
	}

	@GetMapping("/emp")
	public ResponseEntity<String> emp() {
		return ResponseEntity.ok("EMPLOYEE PAGE");
	}

	@GetMapping("/std")
	public ResponseEntity<String> std() {
		return ResponseEntity.ok("STUDENT PAGE");
	}

}
