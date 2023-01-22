package com.kronusboss.cine.domain.user;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kronusboss.cine.domain.movie.MovieNote;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name="\"user\"")
@Table(name="user_cine")
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
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Integer> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Preferences preferences;
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Statistics statistics;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
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
	public User(String name, String email, String password,
			Preferences preferences, Statistics statistics, List<MovieNote> notes) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
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
