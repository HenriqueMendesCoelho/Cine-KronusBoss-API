package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendMailTemplateRequestEntity {

	private String from;
	private String to;
	private String templateId;
	private String subject;
	private String username;

	public SendMailTemplateRequestEntity(SendMailTemplate sendMailTemplate) {
		from = sendMailTemplate.getFrom();
		to = sendMailTemplate.getTo();
		templateId = sendMailTemplate.getTemplateId();
		subject = sendMailTemplate.getSubject();
		username = sendMailTemplate.getUsername();
	}
}
