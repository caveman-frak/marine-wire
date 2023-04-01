package uk.co.bluegecko.marine.wire.geo;

import lombok.Builder;

@Builder(toBuilder = true)
public record Currency(String code, String name, int numericCode, int minor, String symbol) {

}