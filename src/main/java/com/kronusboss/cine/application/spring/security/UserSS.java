package com.kronusboss.cine.application.spring.security;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kronusboss.cine.domain.user.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private UUID userid;
	@Getter
	private String name;
	private String email;
	private String password;
	@Getter
	private Set<String> roles;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS(UUID userid, String name, String email, String password, Set<Role> roles) {
		super();
		this.userid = userid;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = roles.stream().map(i -> new SimpleGrantedAuthority(i.getDescription())).collect(Collectors.toList());
		this.roles = roles.stream().map(r -> r.getDescription()).collect(Collectors.toSet());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
