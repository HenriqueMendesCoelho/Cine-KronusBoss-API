package com.kronusboss.cine.user.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.kronusboss.cine.movie.domain.MovieNote;

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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="\"user\"")
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Column(unique=true, length = 100, nullable = false)
	private String email;
	
	@Column(length = 70, nullable = false)
	private String password;
	
	@CollectionTable
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<Integer> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Preferences preferences;
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Statistics statistics;
	
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MovieNote> notes;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;
	
	public User() {
		addRole(Role.USER);
	}
	
	@Builder
	public User(String name, String email, String password,
			Preferences preferences, Statistics statistics, List<MovieNote> notes) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.preferences = preferences;
		this.statistics = statistics;
		this.notes = notes;
		addRole(Role.USER);
	}
	
	public Set<Role> getRoles(){
		return roles.stream().map(i -> Role.toEnum(i)).collect(Collectors.toSet());
	}
	
	public void addRole(Role role) {
		roles.add(role.getCode());
	}
	
	public void removeRole(Role role) {
		roles.remove(role.getCode());
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	public int hashCode() {
		return Objects.hash(id);
	}
	
}
