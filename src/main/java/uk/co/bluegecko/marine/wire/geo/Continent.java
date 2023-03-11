package uk.co.bluegecko.marine.wire.geo;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Continent {

	@NonNull
	String code;

	@NonNull
	String name;

}
