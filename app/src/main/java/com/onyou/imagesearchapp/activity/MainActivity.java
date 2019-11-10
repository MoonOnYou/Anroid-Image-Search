package com.onyou.imagesearchapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.onyou.imagesearchapp.R;
import com.onyou.imagesearchapp.adapter_or_holder.MainThumbnailAdapter;
import com.onyou.imagesearchapp.server.ApiClient;
import com.onyou.imagesearchapp.utill.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewThumbnail;
    private EditText searchEditText;
    private ApiClient apiClient;
    private ArrayList<String> thumbnailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSearchEditText();


    }

    private void init(){
        recyclerViewThumbnail = findViewById(R.id.main_recycler_view_thumbnail);
        recyclerViewThumbnail.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        searchEditText = findViewById(R.id.main_edit_text_search);
        apiClient = new ApiClient();
        thumbnailList = new ArrayList<>();
    }

    private void setSearchEditText(){
        searchEditText.addTextChangedListener(new SearchIn2Seconds());

    }

    class SearchIn2Seconds implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getSearchResults();
                }
            }, 2000 );
        }
    }

    private void getSearchResults(){
        // 로딩등장
        String keyword = searchEditText.getText().toString().trim();
        apiClient.getSearchResults(Type.AUTHORIZATION ,keyword).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               try {
                   if (response.code() == 200){
                       if (thumbnailList != null){
                           thumbnailList.clear();
                       }
                       JSONObject allResults = new JSONObject(String.valueOf(response.body()));
                       JSONArray criticalResourceArray = allResults.getJSONArray("documents");
                       Log.w("criticalResourceArray", "onResponse:" +criticalResourceArray);
                       for (int i =0; i < criticalResourceArray.length(); i++){
                           thumbnailList.add(criticalResourceArray.getJSONObject(i).getString("thumbnail_url"));
                           Log.w("thumbnailList", "onResponse:" +criticalResourceArray.getJSONObject(i).getString("thumbnail_url"));
                       }
                       //로딩사라짐
                       recyclerViewThumbnail.setAdapter(new MainThumbnailAdapter(MainActivity.this, thumbnailList));
                       Log.w("resultTest", "onResponse:" +thumbnailList);
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
