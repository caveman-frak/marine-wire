package uk.co.bluegecko.marine.wire.geo;

import lombok.Builder;
import lombok.NonNull;
import uk.co.bluegecko.marine.wire.batch.Batchable;

@Builder(toBuilder = true)
public record Country(@NonNull String code, @NonNull Continent continent,
                      @NonNull String name, @NonNull String nativeName) implements Batchable {

}