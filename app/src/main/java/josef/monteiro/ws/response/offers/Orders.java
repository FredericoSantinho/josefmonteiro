package josef.monteiro.ws.response.offers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL) @JsonPropertyOrder({
    "sell", "buy"
}) public class Orders {

  @JsonProperty("sell") public Sell sell;
  @JsonProperty("buy") public Buy buy;

}
