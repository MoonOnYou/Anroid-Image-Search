package com.onyou.imagesearchapp.adapter_or_holder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onyou.imagesearchapp.R;

import java.util.ArrayList;

public class MainThumbnailAdapter extends RecyclerView.Adapter<MainThumbnailHolder> {
    private Context context;
    private ArrayList<String> thumbnailList ;

    public MainThumbnailAdapter(Context context, ArrayList<String> thumbnailList){
        this.context = context;
        this.thumbnailList = thumbnailList;
    }
    @NonNull
    @Override
    public MainThumbnailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_thumbnail_view, parent, false);
        return new MainThumbnailHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainThumbnailHolder holder, int position) {
        holder.bind(thumbnailList.get(position));
    }

    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }
}
