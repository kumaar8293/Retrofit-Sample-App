package com.mukesh.retrofitsampleapp.repository;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    /**
     * Per the release notes, OkHttp requires that you enable Java 8
     * in your builds to function as of 3.13 and newer.
     * You can learn more about how to enable this at
     * https://developer.android.com/studio/write/java8-support.
     */
    private static Retrofit retrofit;
    //BASE url must be end with / else it will give illegal exception
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getRetrofit() {

        if (retrofit == null) {

            synchronized (ApiConfig.class) {
                if (retrofit == null) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    // Here we can change levels depending on us BODY
                    //DEFAULT
                    //HEADERS etc
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         //Simple Retrofit without HEADER
                   /* OkHttpClient okHttpClient = new OkHttpClient
                            .Builder()
                            .addInterceptor(loggingInterceptor)
                            .build();
*/
                   //Request with Global Header

                    OkHttpClient okHttpClient = new OkHttpClient
                            .Builder()
                            .addInterceptor(new Interceptor() {
                                @NonNull
                                @Override
                                public Response intercept(@NonNull Chain chain) throws IOException {
                                    Request originalRequest = chain.request();
                                    Request newRequest = originalRequest.newBuilder()
                                            .header("Global-Header", "VALUE")
                                            .build();
                                    return chain.proceed(newRequest);
                                }
                            })
                            .addInterceptor(loggingInterceptor)
                            .build();


                    //This custom Gson data will ignore null values ,needs to add in to
                    // addConverterFactory(GsonConverterFactory.create(gson))method only
                    //Basically used for patch request
                    Gson gson = new GsonBuilder().serializeNulls().create();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();

                }
            }
        }
        return retrofit;
    }
}
