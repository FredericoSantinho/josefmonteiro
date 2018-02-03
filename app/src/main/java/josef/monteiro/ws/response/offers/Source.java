package josef.monteiro.ws.response.offers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL) @JsonPropertyOrder({
    "quantity", "symbol"
}) public class Source {

  @JsonProperty("quantity") public Integer quantity;
  @JsonProperty("symbol") public String symbol;
}
