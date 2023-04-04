package uk.co.bluegecko.marine.wire.batch;

import org.springframework.boot.logging.LogLevel;

public record Log(LogLevel level, int line, Integer position, String description, String raw) {

}