package uk.co.bluegecko.marine.wire.geo;


import lombok.Builder;
import lombok.NonNull;
import uk.co.bluegecko.marine.wire.batch.Batchable;

@Builder(toBuilder = true)
public record Continent(@NonNull String code, @NonNull String name) implements Batchable {

}