package com.kronusboss.cine.kronusintegrationtool.domain;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Value;

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

	private static String FRONTEND_URL;

	private static final String WELCOME_TEMPLATEID = "d-c1f102718d3c4a5da8ea81e418676469";
	private static final String FORGOT_PASSWORD_TEMPLATEID = "d-c7f944c5af3d413d9acfa82be63222a0";
	private static final String BLOCKED_ACCOUNT_TEMPLATEID = "d-0a10cc53f5df49a6822321c209ad6b69";
	private static final String FROM = "noreply-cine@kronusboss.com";

	private String from;
	private String to;
	private String templateId;
	private String subject;
	private boolean ignoreNotifyPreferences;
	private LinkedHashMap<String, String> params;

	public static SendMailTemplate welcomeMail(String to, String username) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("user", username);
		map.put("cine_url", FRONTEND_URL);
		return SendMailTemplate.builder()
				.from(FROM)
				.to(to)
				.subject(String.format("%s, bem-vindo ao Cineminha!", username))
				.params(map)
				.templateId(WELCOME_TEMPLATEID)
				.ignoreNotifyPreferences(true)
				.build();
	}

	public static SendMailTemplate forgotPasswordMail(String to, String username, String recoverLink) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("user", username);
		map.put("link", recoverLink);
		return SendMailTemplate.builder()
				.from(FROM)
				.to(to)
				.subject(String.format("%s, sua solicitação de recuperação de senha!", username))
				.params(map)
				.templateId(FORGOT_PASSWORD_TEMPLATEID)
				.ignoreNotifyPreferences(true)
				.build();
	}

	public static SendMailTemplate accountBlockedMail(String to, String username) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("user", username);
		return SendMailTemplate.builder()
				.from(FROM)
				.to(to)
				.subject(String.format("%s, sua conta foi bloqueada!", username))
				.params(map)
				.templateId(BLOCKED_ACCOUNT_TEMPLATEID)
				.ignoreNotifyPreferences(true)
				.build();
	}

	@Value("${url.front}")
	public static void setFRONTEND_URL(String fRONTEND_URL) {
		FRONTEND_URL = fRONTEND_URL;
	}
}
