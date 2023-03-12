package uk.co.bluegecko.marine.wire.geo;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.beans.JavaBean;

@Value
@Builder
@JavaBean
public class Continent {

	@NonNull
	String code;

	@NonNull
	String name;

}
