package uk.co.bluegecko.marine.wire.geo;


import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record Continent(@NonNull String code, @NonNull String name) {

}