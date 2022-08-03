package base.security;

import static base.security.ApplicationPermissions.ADMIN_READ;
import static base.security.ApplicationPermissions.ADMIN_WRITE;
import static base.security.ApplicationPermissions.USER_READ;
import static base.security.ApplicationPermissions.USER_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationRoles {
	USER(Sets.newHashSet()), ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE, USER_READ, USER_WRITE)),
	ADMIN_TRAINEE(Sets.newHashSet(ADMIN_READ, USER_READ));

	private final Set<ApplicationPermissions> permissions;

	private ApplicationRoles(Set<ApplicationPermissions> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationPermissions> getPermissions() {
		return permissions;
	}

	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> grantedAuthorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());

		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

		return grantedAuthorities;
	}

}
