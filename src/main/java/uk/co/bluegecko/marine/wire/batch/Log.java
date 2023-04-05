package uk.co.bluegecko.marine.wire.batch;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.boot.logging.LogLevel;

@Builder
public record Log(@NonNull LogLevel level, int line, Integer position, @NonNull String description, String raw) {

	public static class LogBuilder {

		public LogBuilder text(@NonNull String text, Object... args) {
			description = text.formatted(args);
			return this;
		}

	}

}