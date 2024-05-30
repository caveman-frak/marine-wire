package uk.co.bluegecko.marine.wire.batch;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xmlunit.assertj3.XmlAssert;
import uk.co.bluegecko.marine.test.base.MapperTest;
import uk.co.bluegecko.marine.wire.geo.Continent;
import uk.co.bluegecko.marine.wire.geo.Currency;

class BatchTest extends MapperTest {

	private Batch batch;

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
	}

	@Nested
	class Json {

		private String json;

		@BeforeEach
		void setUp() {
			json = """
					{"type":"CONTINENT","name":"Country Batch 001","uploaded":"2000-06-15T12:30:10","items":[\
					{"@type":"Continent","line":1,"code":"EU","name":"Europe"},\
					{"@type":"Currency","line":2,"code":"GBP","name":"Pound Sterling","numericCode":826,"minor":2,"symbol":"£"}],\
					"logs":[],"extent":"[0 0,10 10]"}""";
		}

		@Nested
		class Serialise {

			private String str;

			@BeforeEach
			void setUp() throws JsonProcessingException {
				str = jsonMapper().writeValueAsString(batch);
			}

			@Test
			void serialiseToJson() {
				assertThat(str).isEqualTo(json);
			}

			@Test
			void typeIsMapped() {
				assertJson(str).at("/type").isText("CONTINENT");
			}

			@Test
			void nameIsMapped() {
				assertJson(str).at("/name").isText("Country Batch 001");
			}

			@Test
			void updatedIsMapped() {
				assertJson(str).at("/uploaded")
						.isText(LocalDateTime.of(date(), time())
								.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			}

			@Test
			void itemsAreMapped() {
				assertJson(str).at("/items").isArray()
						.at("/items").hasSize(2)
						.at("/items/0/@type").isText("Continent")
						.at("/items/1/@type").isText("Currency");
			}

			@Test
			void logsAreMapped() {
				assertJson(str).at("/logs").isEmpty();
			}

			@Test
			void infoExtentIsMapped() {
				assertJson(str).at("/extent").isText("[0 0,10 10]");
			}

			@Test
			void continentLineIsMapped() {
				assertJson(str).at("/items/0/line").hasValue(1);
			}

			@Test
			void continentCodeIsMapped() {
				assertJson(str).at("/items/0/code").isText("EU");
			}

			@Test
			void continentNameIsMapped() {
				assertJson(str).at("/items/0/name").isText("Europe");
			}

			@Test
			void currencyLineIsMapped() {
				assertJson(str).at("/items/1/line").hasValue(2);
			}

			@Test
			void currencyCodeIsMapped() {
				assertJson(str).at("/items/1/code").isText("GBP");
			}

			@Test
			void currencyNameIsMapped() {
				assertJson(str).at("/items/1/name").isText("Pound Sterling");
			}

			@Test
			void currencyNumericCodeIsMapped() {
				assertJson(str).at("/items/1/numericCode").hasValue(826);
			}

			@Test
			void currencyMinorIsMapped() {
				assertJson(str).at("/items/1/minor").hasValue(2);
			}

			@Test
			void currencySymbolIsMapped() {
				assertJson(str).at("/items/1/symbol").isText("£");
			}

			@Test
			void fooIsNotMapped() {
				assertJson(str).at("/foo").isMissing();
			}

		}

		@Nested
		class Deserialise {

			@Test
			void deserialiseFromJson() throws JsonProcessingException {
				assertThat(jsonMapper().readValue(json, Batch.class)).isEqualTo(batch);
			}

		}

	}

	@Nested
	class Xml {

		private String xml;

		@BeforeEach
		void setUp() {
			xml = """
					<Batch><type>CONTINENT</type><name>Country Batch 001</name><uploaded>2000-06-15T12:30:10</uploaded><items>\
					<items _type="Continent"><line>1</line><code>EU</code><name>Europe</name></items>\
					<items _type="Currency"><line>2</line><code>GBP</code><name>Pound Sterling</name>\
					<numericCode>826</numericCode><minor>2</minor><symbol>£</symbol></items></items><logs/>\
					<extent>[0 0,10 10]</extent></Batch>""";
		}

		@Nested
		class Serialise {

			private String str;

			@BeforeEach
			void setUp() throws JsonProcessingException {
				str = xmlMapper().writeValueAsString(batch);
			}

			@Test
			void serialiseToXml() {
				assertThat(str).isEqualTo(xml);
			}

			@Test
			void typeIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/type").isEqualTo("CONTINENT");
			}

			@Test
			void nameIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/name").isEqualTo("Country Batch 001");
			}

			@Test
			void updatedIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/uploaded")
						.isEqualTo(LocalDateTime.of(date(), time())
								.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			}

			@Test
			void itemsAreMapped() {
				XmlAssert.assertThat(str).valueByXPath("count(/Batch/items/items)").isEqualTo(2);
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[1]/@_type").isEqualTo("Continent");
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/@_type").isEqualTo("Currency");
			}

			@Test
			void logsAreMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/logs").isEmpty();
			}

			@Test
			void infoExtentIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/extent").isEqualTo("[0 0,10 10]");
			}

			@Test
			void continentLineIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[1]/line").isEqualTo(1);
			}

			@Test
			void continentCodeIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[1]/code").isEqualTo("EU");
			}

			@Test
			void continentNameIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[1]/name").isEqualTo("Europe");
			}

			@Test
			void currencyLineIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/line").isEqualTo(2);
			}

			@Test
			void currencyCodeIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/code").isEqualTo("GBP");
			}

			@Test
			void currencyNameIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/name").isEqualTo("Pound Sterling");
			}

			@Test
			void currencyNumericCodeIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/numericCode").isEqualTo(826);
			}

			@Test
			void currencyMinorIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/minor").isEqualTo(2);
			}

			@Test
			void currencySymbolIsMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/items/items[2]/symbol").isEqualTo("£");
			}

			@Test
			void fooIsNotMapped() {
				XmlAssert.assertThat(str).valueByXPath("/Batch/foo").isNullOrEmpty();
			}

		}

		@Nested
		class Deserialise {

			@Test
			void deserialiseFromXml() throws JsonProcessingException {
				assertThat(xmlMapper().readValue(xml, Batch.class)).isEqualTo(batch);
			}

		}

	}

	@Test
	void testFileBuilder(@TempDir Path dir) throws IOException {
		Path file = Files.createFile(dir.resolve("test.txt"));
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
	}

}