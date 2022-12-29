package base.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import base.entity.UserEntity;
import base.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	public UserEntity saveUser(UserEntity user) {
		user.setPwd(passwordEncoder.encode(user.getPwd()));
		return userRepo.save(user);
	}

	public Optional<UserEntity> findByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> optUser = findByUserName(username);
		if (optUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found.");
		}
		UserEntity user = optUser.get();
		return new User(username, user.getPwd(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
	}

}
