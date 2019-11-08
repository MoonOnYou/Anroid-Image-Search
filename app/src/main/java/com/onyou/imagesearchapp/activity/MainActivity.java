package com.onyou.imagesearchapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.onyou.imagesearchapp.R;
import com.onyou.imagesearchapp.adapter_or_holder.MainImageAdapter;
import com.onyou.imagesearchapp.server.ApiClient;
import com.onyou.imagesearchapp.utill.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewImage;
    private EditText searchEditText;
    private ApiClient apiClient;
    private ArrayList<String> thumbnailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getSearchResults();

    }

    private void init(){
        recyclerViewImage = findViewById(R.id.main_recycler_view_image);
        recyclerViewImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewImage.setAdapter(new MainImageAdapter());
        searchEditText = findViewById(R.id.main_edit_text_search);
        apiClient = new ApiClient();
        thumbnailList = new ArrayList<>();
    }

    private void getSearchResults(){
        String keyword = searchEditText.getText().toString().trim();
        apiClient.getSearchResults(Type.AUTHORIZATION ,"설현").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               try {
                   if (response.code() == 200){
                       JSONObject allResults = new JSONObject(String.valueOf(response.body()));
                       JSONArray temp = allResults.getJSONArray("documents");
                       ArrayList<String> thumbnails = new ArrayList<>();
                       for (int i =0; i < temp.length(); i++){
                           thumbnails.add(temp.getJSONObject(i).getString("thumbnail_url"));
                       }
                       Log.w("resultTest", "onResponse:" +thumbnails);
                   }else {
                       Log.w("resultTest", "else:");
                   }
               }catch (JSONException e){
                   Log.w("resultTest", "catch:");
               }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w("resultTest", "onFailure:" + t.getMessage());
            }
        });

    }
}
