package uk.co.bluegecko.marine.wire.geo;

import lombok.Builder;
import uk.co.bluegecko.marine.wire.batch.Batchable;

@Builder(toBuilder = true)
public record Currency(String code, String name, int numericCode, int minor, String symbol) implements Batchable {

}