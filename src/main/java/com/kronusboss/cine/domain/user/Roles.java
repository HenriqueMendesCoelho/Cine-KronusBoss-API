package com.kronusboss.cine.domain.user;

import lombok.Getter;

@Getter
public enum Roles {
	ADM(1, "ROLE_ADM"), USER(2, "ROLE_USER");

	private int code;
	private String description;

	private Roles(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public static Roles toEnum(Integer code) {

		if (code == null) {
			return null;
		}

		for (Roles i : Roles.values()) {
			if (code.equals(i.getCode())) {
				return i;
			}
		}

		throw new IllegalArgumentException("User type invalid " + code);

	}

}
