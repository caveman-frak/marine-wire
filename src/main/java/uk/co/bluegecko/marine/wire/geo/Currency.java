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
@Schema(title = "Currency", description = "Simple representation of currency details")
public record Currency(
		@Range(min = 0, max = 99999)
		Integer line,
		@Schema(description = "ISO Currency code", example = "GBP")
		@NonNull @Length(min = 3, max = 3)
		String code,
		@Schema(description = "Currency name", example = "Pound Sterling")
		@NonNull @Length(min = 3)
		String name,
		@Schema(description = "Numeric currency code", example = "826")
		@Range(min = 0, max = 999)
		int numericCode,
		@Schema(description = "Minor currency units (decimal places)", example = "2")
		@Range(min = 0, max = 4)
		int minor,
		@Schema(description = "Currency symbol", example = "£")
		String symbol) implements Batchable {

}