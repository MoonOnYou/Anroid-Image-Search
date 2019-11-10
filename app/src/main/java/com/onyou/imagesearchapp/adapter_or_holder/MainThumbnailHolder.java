package com.onyou.imagesearchapp.adapter_or_holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.onyou.imagesearchapp.R;

public class MainThumbnailHolder extends RecyclerView.ViewHolder {
    private Context context;
    private ImageView thumbnailImageView;

    public MainThumbnailHolder(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.thumbnailImageView = itemView.findViewById(R.id.main_image_view_thumbnail);
    }

    public void bind(String thumbnailURL){
        Glide.with(context).load(thumbnailURL).into(thumbnailImageView);
    }
}
