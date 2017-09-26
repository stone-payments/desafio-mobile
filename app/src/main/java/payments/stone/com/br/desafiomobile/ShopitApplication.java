package payments.stone.com.br.desafiomobile;

import android.app.Application;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import payments.stone.com.br.desafiomobile.checkout.Cart;
import payments.stone.com.br.desafiomobile.data.ShopApi;
import payments.stone.com.br.desafiomobile.data.ShopRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class ShopitApplication extends Application {
    public static final String BASE_URL = "https://private-4451e-shopit2.apiary-mock.com/";
    public static ShopitApplication instance;
    private static Retrofit retrofit = null;
    private static ShopApi shopApi = null;
    private static Cart cart;


    public static ShopitApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;
        }

        Realm.init(this);
    }

    public Retrofit getClient() {
        if (retrofit == null) {

            // Define the interceptor, add authentication headers
            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("User-Agent", "Retrofit-Sample-App").build();
                    return chain.proceed(newRequest);
                }
            };

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Add the interceptor to OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.interceptors().add(interceptor);
            builder.addInterceptor(logging);
            OkHttpClient client = builder.build();

            // Set the custom client when building adapter
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }
        return retrofit;
    }

    public ShopApi provideApi() {
        if (shopApi == null) {
            shopApi = ShopitApplication
                    .getInstance()
                    .getClient()
                    .create(ShopApi.class);
        }

        return shopApi;
    }

    public RealmConfiguration provideRealmConfig() {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }
        return builder.build();
    }

    public Cart provideCart(){
        if(cart == null) {
            cart = new Cart();
        }

        return cart;
    }

    public Realm provideRealm(RealmConfiguration configuration){
        return Realm.getInstance(configuration);
    }

    public ShopRepository provideRepository(){
        return new ShopRepository(provideRealm(provideRealmConfig()));
    }
}
