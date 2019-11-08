package com.onyou.imagesearchapp.server;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/v2/search/image")
    Call <JsonObject> getSearchResults(
            @Header("Authorization") String authorization,
            @Query("query") String searchKeyword
    );
}
