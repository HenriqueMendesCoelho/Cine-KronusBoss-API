package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto;

import java.util.List;

import com.kronusboss.cine.kronusintegrationtool.domain.Credit;
import com.kronusboss.cine.kronusintegrationtool.domain.Credit.Cast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditResponseDto {

	List<CastResponseDto> cast;
	List<CastResponseDto> crew;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CastResponseDto {

		private String knownForDepartment;
		private String name;
		private String originalName;
		private String profilePath;
		private String character;
		private String department;
		private String job;

		public Cast toDomain() {
			return Cast.builder()
					.knownForDepartment(knownForDepartment)
					.name(name)
					.originalName(originalName)
					.profilePath(profilePath)
					.character(character)
					.department(department)
					.job(job)
					.build();
		}

	}

	public Credit toDomain() {
		return Credit.builder()
				.cast(cast.stream().map(CastResponseDto::toDomain).toList())
				.crew(crew.stream().map(CastResponseDto::toDomain).toList())
				.build();
	}

}
