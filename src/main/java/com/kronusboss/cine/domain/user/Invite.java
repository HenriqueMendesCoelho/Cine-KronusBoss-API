package com.kronusboss.cine.domain.user;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_invites")
@Data
public class Invite {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String code;

	public Invite() {
		Random r = new Random();
		int minimum = 10000000;
		int maximum = 999999999;
		int result = r.nextInt(maximum-minimum) + minimum;
		
		code = String.format("KB-%s", result);
	}
}
