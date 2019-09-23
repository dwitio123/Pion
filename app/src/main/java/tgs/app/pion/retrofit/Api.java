package tgs.app.pion.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static OkHttpClient getRequestHeader() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }
    private static Retrofit retrofit;
    public static Retrofit getUrl(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://smkn2smi.sch.id/pion/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getRequestHeader())
                .build();
        return retrofit;
    }
}
