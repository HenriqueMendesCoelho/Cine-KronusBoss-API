package com.kronusboss.cine.user.domain;

import java.io.Serializable;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_invites")
@Getter
@Setter
@EqualsAndHashCode
public class Invite implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(nullable = false, unique = true)
	private String code;

	public Invite() {
		super();
		this.code = createRandomInvite();
	}

	private String createRandomInvite() {
		Random r = new Random();
		int minimum = 10000000;
		int maximum = 999999999;
		int result = r.nextInt(maximum - minimum) + minimum;

		return String.format("KB-%s", result);
	}

	public Invite(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
}
