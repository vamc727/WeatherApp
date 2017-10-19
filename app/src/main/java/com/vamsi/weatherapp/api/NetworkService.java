package com.vamsi.weatherapp.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vamsi.weatherapp.util.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

public class NetworkService {
    private static NetworkService INSTANCE = null;
    private WeatherAPI mWeatherAPI;

    private NetworkService(@NonNull Context context) {
        checkNotNull(context);
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("APPID", "965ce4ece8bac15343d5d88e5ecd9020")
                        .addQueryParameter("lang", "en")
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mWeatherAPI = retrofit.create(WeatherAPI.class);
    }

    public static NetworkService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkService(context);
        }
        return INSTANCE;
    }

    public WeatherAPI getWeatherAPI() {
        return mWeatherAPI;
    }
}
