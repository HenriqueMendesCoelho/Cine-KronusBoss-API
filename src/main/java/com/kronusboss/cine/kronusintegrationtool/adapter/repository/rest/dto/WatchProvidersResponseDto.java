package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;
import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders.Country.CountryBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WatchProvidersResponseDto {

	private Long id;
	private Map<String, CountryResponseDto> results;

	public WatchProviders toDomain() {

		return WatchProviders.builder()
				.id(id)
				.results(results.entrySet()
						.stream()
						.collect(Collectors.toMap(Map.Entry::getKey, r -> r.getValue().toDomain())))
				.build();
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class CountryResponseDto {
		private String link;
		private List<ProviderResponseDto> flatrate;
		private List<ProviderResponseDto> rent;
		private List<ProviderResponseDto> buy;

		public WatchProviders.Country toDomain() {
			CountryBuilder result = WatchProviders.Country.builder().link(link);
			if (flatrate != null) {
				result.flatrate(flatrate.stream()
						.map(WatchProvidersResponseDto.ProviderResponseDto::toDomain)
						.collect(Collectors.toList()));
			}
			if (rent != null) {
				result.rent(rent.stream()
						.map(WatchProvidersResponseDto.ProviderResponseDto::toDomain)
						.collect(Collectors.toList()));
			}
			if (buy != null) {
				result.buy(buy.stream()
						.map(WatchProvidersResponseDto.ProviderResponseDto::toDomain)
						.collect(Collectors.toList()));
			}
			return result.build();

		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class ProviderResponseDto {
		private String logoPath;
		private Long providerId;
		private String providerName;
		private int displayPriority;

		public WatchProviders.Provider toDomain() {
			return WatchProviders.Provider.builder()
					.logoPath(logoPath)
					.providerId(providerId)
					.providerName(providerName)
					.displayPriority(displayPriority)
					.build();
		}
	}

}
