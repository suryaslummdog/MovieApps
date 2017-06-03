package com.ikadeksurya.movieapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikadeksurya.movieapps.R;
import com.ikadeksurya.movieapps.model.MovieList;

import java.util.ArrayList;
import java.util.List;

import static com.ikadeksurya.movieapps.R.id.list_item;
import static com.ikadeksurya.movieapps.R.id.parent;

/**
 * Created by IKadekSurya on 28/05/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MovieList> listData = new ArrayList<>();
    MovieItemClickListener mMovieItemClickListener;

    public MovieAdapter(List<MovieList> listData, Context context){
        this.listData = listData;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new MovieItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MovieItemViewHolder movieItemViewHolder = (MovieItemViewHolder) holder;
        final MovieList data = listData.get(position);
        movieItemViewHolder.bind(data, context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovieItemClickListener != null)
                    mMovieItemClickListener.onMovieItemClick(data, movieItemViewHolder.ivCardThumbnail, movieItemViewHolder.tvCardTitle);
                else
                    Log.e("MovieAdapter", "Error onClickListener" );
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setMovieItemClickListener(MovieItemClickListener clickListener){
        if (clickListener != null)
            mMovieItemClickListener = clickListener;
    }

    public interface MovieItemClickListener{
        void onMovieItemClick(MovieList data, ImageView ivCardThumnail, TextView tvTitle);
    }
}
