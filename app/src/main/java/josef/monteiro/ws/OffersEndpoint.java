package josef.monteiro.ws;

import io.reactivex.Single;
import josef.monteiro.ws.response.offers.Offers;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OffersEndpoint {

  @GET("offers") Single<Offers> call(@Query("symbol") String symbol, @Query("total") Double total);
}
