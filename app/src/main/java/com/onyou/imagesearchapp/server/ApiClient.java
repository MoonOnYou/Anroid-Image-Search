package com.onyou.imagesearchapp.server;

import com.google.gson.JsonObject;
import com.onyou.imagesearchapp.utill.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private ApiInterface apiInterface;

    public ApiClient() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Type.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }
    }

    public Call<JsonObject> getSearchResults(String authorization, String searchKeyword){
        return apiInterface.getSearchResults(authorization, searchKeyword);
    }
}
