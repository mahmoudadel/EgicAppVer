package egic.com.recordversion;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mahmoud.Adel on 2/19/2018.
 */

public class ApiClient {
    //public static final String BASE_URL = "http://Endahly.egic.com.eg:8086/AndroidAppsVersionsBE/api/";
    public static final String BASE_URL = "http://10.110.10.132/AndroidAppsVersionsBE/api/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
