package base.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import base.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndPassword(String username, String password);

}
