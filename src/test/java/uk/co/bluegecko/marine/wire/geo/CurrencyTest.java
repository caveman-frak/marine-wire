package uk.co.bluegecko.marine.wire.geo;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.bluegecko.marine.test.jassert.Conditions.extracted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class CurrencyTest {

	@Test
	void testBuilder() {
		assertThat(Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£").build())
				.has(anyOf(extracted(Currency::code, "code", String::equals, "equal to", "GBP"),
						extracted(Currency::name, "code", String::equals, "equal to", "Pound Sterling"),
						extracted(Currency::numericCode, "numeric code", Integer::equals, "equal to", 826),
						extracted(Currency::minor, "minor", Integer::equals, "equal to", 2),
						extracted(Currency::symbol, "symbol", String::equals, "equal to", "£")));
	}

	@Test
	void testSerialise() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Currency ccy =
				Currency.builder().code("GBP").name("Pound Sterling").numericCode(826).minor(2).symbol("£").build();
		assertThat(objectMapper.writeValueAsString(ccy))
				.isEqualTo(
						"{\"code\":\"GBP\",\"name\":\"Pound Sterling\",\"numericCode\":826,\"minor\":2,\"symbol\":\"£\"}");
	}
}