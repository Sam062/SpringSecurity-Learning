package base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import base.entity.Role;
import base.entity.User;
import base.repo.RolesRepo;
import base.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RolesRepo rolesRepo;


	public Role saveUserRole(Role role) {
		try {
			return rolesRepo.save(role);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User saveUser(User user) {
		try {
			return userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Inside user details service : loadUserByUsername");
		return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not valid"));

	}

}
