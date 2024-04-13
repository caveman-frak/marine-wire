package uk.co.bluegecko.marine.wire.geo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import uk.co.bluegecko.marine.wire.batch.Batchable;

@Builder(toBuilder = true)
@JsonInclude(value = Include.NON_NULL)
@Schema(title = "Country", description = "Simple representation of a Country")
public record Country(
		@Range(min = 0, max = 99999)
		Integer line,
		@Schema(description = "Country Code (2 character ISO)", example = "GB")
		@NonNull @Length(min = 2, max = 2)
		String code,
		@Schema(description = "The principle continent this country exists in")
		@NonNull
		Continent continent,
		@Schema(description = "International name of country", example = "France")
		@NonNull @Length(min = 3)
		String name,
		@Schema(description = "Native name of country", example = "Česká republika")
		@NonNull @Length(min = 3)
		String nativeName) implements Batchable {

}