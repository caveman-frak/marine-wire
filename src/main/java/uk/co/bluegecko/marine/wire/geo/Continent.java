package uk.co.bluegecko.marine.wire.geo;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Continent {

	@NonNull
	String code;

	@NonNull
	String name;

}
