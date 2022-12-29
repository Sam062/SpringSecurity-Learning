package base.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS_TAB")
public class UserEntity {

	@Id
	@GeneratedValue
	private Integer userId;
	private String userName;
	private String userEmail;
	private String pwd;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles_Tab", joinColumns = @JoinColumn(name = "uid"))
	private List<String> roles;

}
