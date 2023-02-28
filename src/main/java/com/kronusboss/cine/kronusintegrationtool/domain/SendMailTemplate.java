package com.kronusboss.cine.kronusintegrationtool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMailTemplate {

	private static final String WELCOME_TEMPLATEID = "d-c1f102718d3c4a5da8ea81e418676469";
	private static final String FORGOT_PASSWORD_TEMPLATEID = "d-c7f944c5af3d413d9acfa82be63222a0";
	private static final String BLOQUED_ACCOUNT_TEMPLATEID = "d-0a10cc53f5df49a6822321c209ad6b69";
	private static final String FROM = "noreply-cine@kronusboss.com";

	private String from;
	private String to;
	private String templateId;
	private String subject;
	private String username;

	public static SendMailTemplate welcomeMail(String to, String username) {
		return SendMailTemplate.builder()
				.from(FROM)
				.to(to)
				.subject(String.format("%s, bem-vindo ao Cineminha!", username))
				.username(username)
				.templateId(WELCOME_TEMPLATEID)
				.build();
	}

	public static SendMailTemplate forgotPasswordMail(String to, String username) {
		return SendMailTemplate.builder()
				.from(FROM)
				.to(to)
				.subject(String.format("%s, sua solicitação de nova senha!", username))
				.username(username)
				.templateId(FORGOT_PASSWORD_TEMPLATEID)
				.build();
	}

	public static SendMailTemplate accountBloquedMail(String to, String username) {
		return SendMailTemplate.builder()
				.from(FROM)
				.to(to)
				.subject(String.format("%s, sua conta foi bloqueada!", username))
				.username(username)
				.templateId(BLOQUED_ACCOUNT_TEMPLATEID)
				.build();
	}

}
