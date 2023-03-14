package uk.co.bluegecko.marine.wire.geo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Country {

	@NonNull
	Continent continent;

	@NonNull
	String code;

	@NonNull
	String name;

	@NonNull
	String nativeName;

}