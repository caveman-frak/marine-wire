package uk.co.bluegecko.marine.wire.batch;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
@JsonInclude(value = Include.NON_NULL)
public record Batch(
		BatchType type,
		String fileName,
		String path,
		LocalDateTime fileCreated,
		LocalDateTime fileLastModified,
		String name,
		LocalDateTime uploaded,
		@JsonTypeInfo(use = Id.NAME)
		List<? extends Batchable> items,
		@JsonIgnore
		Map<String, Object> info,
		List<Log> logs) {

	public Batch(BatchType type, String fileName, String path, LocalDateTime fileCreated,
			LocalDateTime fileLastModified, String name, LocalDateTime uploaded,
			@JsonTypeInfo(use = Id.NAME) List<? extends Batchable> items,
			Map<String, Object> info, List<Log> logs) {
		this.type = type;
		this.fileName = fileName;
		this.path = path;
		this.fileCreated = fileCreated;
		this.fileLastModified = fileLastModified;
		this.name = name;
		this.uploaded = uploaded;
		this.items = items;
		this.info = info == null ? new HashMap<>() : info;
		this.logs = logs;
	}

	@JsonAnySetter
	public void addInfo(String key, Object value) {
		info.put(key, value);
	}

	@JsonAnyGetter
	public Map<String, Object> getInfo() {
		return info;
	}

	public static class BatchBuilder {

		public BatchBuilder file(Path file) throws IOException {
			BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
			fileName = file.getFileName().toString();
			path = file.getParent().toAbsolutePath().toString();
			fileCreated = LocalDateTime.ofInstant(attributes
					.creationTime().toInstant(), ZoneOffset.UTC);
			fileLastModified = LocalDateTime.ofInstant(attributes
					.lastModifiedTime().toInstant(), ZoneOffset.UTC);
			return this;
		}

		public BatchBuilder clock(Clock clock) {
			uploaded = LocalDateTime.now(clock);
			return this;
		}

	}

}