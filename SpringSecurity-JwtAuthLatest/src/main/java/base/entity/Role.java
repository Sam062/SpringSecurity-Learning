package base.entity;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue
	private Integer roleId;

	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private ERole authority;

	@Override
	public String getAuthority() {
		return authority.toString();
	}
}
