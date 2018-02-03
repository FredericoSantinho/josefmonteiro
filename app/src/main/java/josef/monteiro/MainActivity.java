package josef.monteiro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.schedulers.Schedulers;
import josef.monteiro.ws.JosefMonteiroEndpointFactory;
import josef.monteiro.ws.OffersEndpoint;
import josef.monteiro.ws.response.offers.Exchange;
import josef.monteiro.ws.response.offers.Offers;
import josef.monteiro.ws.response.offers.ResponseExample;
import monteiro.josef.pt.josefmonteiro.R;

public class MainActivity extends AppCompatActivity {

  private OffersEndpoint offersEndpoint;
  private TextView resultTv;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    resultTv = findViewById(R.id.result);

    offersEndpoint = new JosefMonteiroEndpointFactory().newOffersEndpoint();
  }

  public void orders(View view) {
    String symbol = "ETH/BTC";
    double total = 1000d;
    offersEndpoint.call(symbol, total)
        .subscribeOn(Schedulers.io())
        .subscribe(this::onNext, this::onError);
  }

  private void onError(Throwable throwable) {
    Toast.makeText(this, "Error calling ws!", Toast.LENGTH_LONG)
        .show();
    throwable.printStackTrace();
  }

  private void onNext(Offers offers) {
    StringBuilder resultBuilder = new StringBuilder();

    ResponseExample response = offers.responseExample.get(0);
    for (Exchange exchange : response.exchanges) {

      try {
        String name = exchange.name;
        Float buyQnt = exchange.orders.buy.tokens.quantity.base;
        Float buyFee = exchange.orders.buy.tokens.quantity.fee;
        Float sellQnt = exchange.orders.sell.tokens.quantity.base;
        Float sellFee = exchange.orders.sell.tokens.quantity.fee;

        String symbol = exchange.orders.sell.tokens.symbol;

        resultBuilder.append(name)
            .append(System.lineSeparator());

        resultBuilder.append("Buy: ")
            .append(symbol)
            .append(": ")
            .append(buyQnt)
            .append(" + ")
            .append(buyFee)
            .append(" fee")
            .append(System.lineSeparator());

        resultBuilder.append("Sell: ")
            .append(symbol)
            .append(": ")
            .append(sellQnt)
            .append(" + ")
            .append(sellFee)
            .append(" fee")
            .append(System.lineSeparator());
      } catch (NullPointerException e) {
        // Ignore
        runOnUiThread(() -> Toast.makeText(this, "Pqp o zé lol", Toast.LENGTH_LONG)
            .show());
      }
    }

    String result = resultBuilder.toString();

    runOnUiThread(() -> resultTv.setText(result));
  }
}
