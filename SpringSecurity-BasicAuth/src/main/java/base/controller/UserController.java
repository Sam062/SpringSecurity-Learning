package base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/")
public class UserController {

	static List<String> userList = List.of("SHIVAM", "MAK", "ADARSH", "ABC", "XYCZ");

	@GetMapping("/getAllUsers")
	public List<String> getAllUsers() {
		return userList;
	}

	@PostMapping("/registerNewUser")
	public String registerNewUser() {
		int x = (int) (Math.random() * 100);
		String newUser = "NEW_USER" + x;
		ArrayList<String> arrayList = new ArrayList<>(userList);
		arrayList.add(newUser);

		userList = arrayList;

		return newUser;
	}

	@DeleteMapping("/deleteUser")
	public String deleteUser() {
		ArrayList<String> al = new ArrayList<>(userList);
		String remove = al.remove(al.size() - 1);

		userList = al;

		return remove;
	}

	@PutMapping("/updateUser")
	public List<String> updateUser() {
		ArrayList<String> al = new ArrayList<>(userList);
		al.set(0, "UPDATES USERXXXX");

		userList = al;

		return userList;
	}

}
