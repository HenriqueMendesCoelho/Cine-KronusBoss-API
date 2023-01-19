package com.kronusboss.cine.domain.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kronusboss.cine.domain.movie.MovieNote;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;
	
	@NotBlank
	@Column(length = 100, nullable = false)
	private String name;
	
	@NotBlank
	@Column(unique=true, length = 100, nullable = false)
	private String email;
	
	@NotBlank
	@Column(length = 70, nullable = false)
	private String password;
	
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="roles")
	private Set<Integer> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Preferences preferences;
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Statistics statistics;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<MovieNote> notes;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;
	
	public User() {
		addRole(Roles.USER);
	}
	
	@Builder
	public User(String name, String email, String password, Set<Integer> roles,
			Preferences preferences, Statistics statistics, List<MovieNote> notes) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.preferences = preferences;
		this.statistics = statistics;
		this.notes = notes;
		addRole(Roles.USER);
	}
	
	public Set<Roles> getRoles(){
		return roles.stream().map(i -> Roles.toEnum(i)).collect(Collectors.toSet());
	}
	
	public void addRole(Roles role) {
		roles.add(role.getCode());
	}
	
	public void removeRole(Roles role) {
		roles.remove(role.getCode());
	}

}
