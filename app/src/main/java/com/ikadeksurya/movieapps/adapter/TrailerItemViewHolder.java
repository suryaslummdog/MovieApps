package com.ikadeksurya.movieapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ikadeksurya.movieapps.R;
import com.ikadeksurya.movieapps.model.TrailerList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IKadekSurya on 04/06/2017.
 */

public class TrailerItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_trailer)
    ImageView ivTrailer;

    public TrailerItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(TrailerList data, Context context) {
        Glide
                .with(context)
                .load("http://img.youtube.com/vi/" + data.getKey() + "/mqdefault.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(ivTrailer);
    }
}
