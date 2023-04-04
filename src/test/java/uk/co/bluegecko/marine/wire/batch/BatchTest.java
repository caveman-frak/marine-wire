package uk.co.bluegecko.marine.wire.batch;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.bluegecko.marine.wire.geo.Continent;
import uk.co.bluegecko.marine.wire.geo.Currency;

class BatchTest {

	private ObjectMapper objectMapper;
	private Batch batch;
	private String json;

	@BeforeEach
	void setUp() {
		objectMapper = JsonMapper.builder().defaultLeniency(true).build();
		batch = Batch.builder()
				.type(BatchType.CONTINENT)
				.name("Country Batch 001")
				.items(List.of(
						Continent.builder().code("EU").name("Europe").build(),
						Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£")
								.build()
				))
				.info(Map.of("extent", "[0 0,10 10]"))
				.logs(List.of())
				.build();
		json = """
				{"type":"CONTINENT","name":"Country Batch 001","items":[\
				{"@type":"Continent","code":"EU","name":"Europe"},\
				{"@type":"Currency","code":"GBP","name":"Pound Sterling","numericCode":826,"minor":2,"symbol":"£"}],\
				"logs":[],"extent":"[0 0,10 10]"}""";
	}

	@Test
	void testSerialise() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(batch))
				.isEqualTo(json);
	}

	@Test
	void testDeserialise() throws JsonProcessingException {
		assertThat(objectMapper.readValue(json, Batch.class))
				.isEqualTo(batch);
	}

	@Test
	void testFileBuilder() throws IOException {
		Path file = Files.createTempFile("test", ".txt");
		try {
			LocalDateTime time = LocalDateTime.now().plusSeconds(1);

			assertThat(Batch.builder().file(file)
					.items(List.of(Continent.builder().code("EU").name("Europe").build()))
					.uploaded(time)
					.build())
					.has(allOf(extracted(Batch::fileName, "filename", String::startsWith, "starts with", "test"),
							extracted(Batch::fileName, "filename", String::endsWith, "ends with", ".txt"),
							extracted(Batch::fileName, "path", Objects::nonNull, "not null"),
							extracted(Batch::fileCreated, "file created", d -> time.isAfter(d), "is after"),
							extracted(Batch::fileLastModified, "file modified", d -> time.isAfter(d), "is after"),
							extracted(Batch::uploaded, "uploaded", d -> time.isEqual(d), "is same"),
							extracted(Batch::info, "info", Objects::nonNull, "not null"),
							extracted(Batch::logs, "info", Objects::isNull, "is null")
					));
		} finally {
			Files.deleteIfExists(file);
		}
	}

}