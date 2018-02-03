package josef.monteiro.ws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by neuro on 03-02-2018.
 */

public class JosefMonteiroEndpointFactory {

  public final String baseHost;

  public JosefMonteiroEndpointFactory() {
    this.baseHost = "http://crypto.nogo0d.com/";
  }

  public OffersEndpoint newOffersEndpoint() {
    return create(OffersEndpoint.class);
  }

  private OkHttpClient createDefaultClient() {

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    okHttpClientBuilder.readTimeout(45, TimeUnit.SECONDS);
    okHttpClientBuilder.writeTimeout(45, TimeUnit.SECONDS);

    File cacheDirectory = new File("/");
    int cacheMaxSize = 10 * 1024 * 1024;
    okHttpClientBuilder.cache(new Cache(cacheDirectory, cacheMaxSize)); // 10 MiB

    return okHttpClientBuilder.build();
  }

  protected final <T> T create(Class<T> aClass) {
    return createRetrofit(createOkHttpClient(), createDefaultConverter(),
        createCallAdapterFactory(), baseHost).create(aClass);
  }

  private OkHttpClient createOkHttpClient() {
    return createDefaultClient();
  }

  private Converter.Factory createDefaultConverter() {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    objectMapper.setDateFormat(df);

    return JacksonConverterFactory.create(objectMapper);
  }

  private CallAdapter.Factory createCallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  private Retrofit createRetrofit(OkHttpClient httpClient, Converter.Factory converterFactory,
      CallAdapter.Factory factory, String baseHost) {
    return new Retrofit.Builder().baseUrl(baseHost)
        .client(httpClient)
        .addCallAdapterFactory(factory)
        .addConverterFactory(converterFactory)
        .build();
  }
}
