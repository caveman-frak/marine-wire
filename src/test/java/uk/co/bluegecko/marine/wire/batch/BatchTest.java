package uk.co.bluegecko.marine.wire.batch;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.bluegecko.marine.test.base.DatedTest;
import uk.co.bluegecko.marine.wire.geo.Continent;
import uk.co.bluegecko.marine.wire.geo.Currency;

class BatchTest extends DatedTest {

	private Batch batch;
	private String json;

	@BeforeEach
	void setUp() {
		batch = Batch.builder()
				.type(BatchType.CONTINENT)
				.name("Country Batch 001")
				.uploaded(LocalDateTime.now(clock()))
				.items(List.of(
						Continent.builder().line(1).code("EU").name("Europe").build(),
						Currency.builder().line(2).code("GBP").name("Pound Sterling")
								.numericCode(826).minor(2).symbol("£").build()
				))
				.info(Map.of("extent", "[0 0,10 10]"))
				.logs(List.of())
				.build();
		json = """
				{"type":"CONTINENT","name":"Country Batch 001","uploaded":"2000-06-15T12:30:00","items":[\
				{"@type":"Continent","line":1,"code":"EU","name":"Europe"},\
				{"@type":"Currency","line":2,"code":"GBP","name":"Pound Sterling","numericCode":826,"minor":2,"symbol":"£"}],\
				"logs":[],"extent":"[0 0,10 10]"}""";
	}

	@Test
	void testSerialise() throws JsonProcessingException {
		assertThat(objectMapper().writeValueAsString(batch))
				.isEqualTo(json);
	}

	@Test
	void testDeserialise() throws JsonProcessingException {
		assertThat(objectMapper().readValue(json, Batch.class))
				.isEqualTo(batch);
	}

	@Test
	void testFileBuilder() throws IOException {
		Path file = Files.createTempFile("test", ".txt");
		try {
			LocalDateTime time = LocalDateTime.now().plusSeconds(1);

			assertThat(Batch.builder().file(file)
					.type(BatchType.MIXED)
					.name("Test Batch 001")
					.items(List.of(Continent.builder().code("EU").name("Europe").build()))
					.uploaded(time)
					.build())
					.has(allOf(extracted(Batch::fileName, "filename", String::startsWith, "starts with", "test"),
							extracted(Batch::fileName, "filename", String::endsWith, "ends with", ".txt"),
							extracted(Batch::fileName, "path", Objects::nonNull, "not null"),
							extracted(Batch::fileCreated, "file created", time::isAfter, "is after"),
							extracted(Batch::fileLastModified, "file modified", time::isAfter, "is after"),
							extracted(Batch::uploaded, "uploaded", time::isEqual, "is same"),
							extracted(Batch::info, "info", Objects::nonNull, "not null"),
							extracted(Batch::logs, "info", Objects::isNull, "is null")
					));
		} finally {
			Files.deleteIfExists(file);
		}
	}

}