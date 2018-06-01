package com.ashima.android.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * To send network requests to an API, we need to use the Retrofit Builder class and specify the base URL for the service
 * SingleTon Class
 *
 * @author Ashima
 */

public class RetrofitApiClient {

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}