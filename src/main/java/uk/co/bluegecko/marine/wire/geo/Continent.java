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
@Schema(description = "Simple representation of a continent")
public record Continent(
		@Range(min = 0, max = 99999)
		Integer line,
		@Schema(description = "Continent code", example = "EU")
		@NonNull @Length(min = 2, max = 2)
		String code,
		@Schema(description = "Name of continent", example = "Europe")
		@NonNull @Length(min = 3)
		String name) implements Batchable {

}