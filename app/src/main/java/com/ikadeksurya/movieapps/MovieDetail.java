package com.ikadeksurya.movieapps;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ikadeksurya.movieapps.adapter.TrailerAdapter;
import com.ikadeksurya.movieapps.model.MovieList;
import com.ikadeksurya.movieapps.model.TrailerList;
import com.ikadeksurya.movieapps.model.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity implements TrailerAdapter.TrailerItemClickListener{

    private static final String TAG = "MovieDetail";

    private MovieList listData;
    private Gson gson = new Gson();
    private String ApiURL;
    private List<TrailerList> listTrailer = new ArrayList<>();
    private TrailerAdapter trailerAdapter;

    @BindView(R.id.iv_cover)
    ImageView iv_cover;
    @BindView(R.id.card_thumbnail) ImageView cardThumbnail;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.tv_rating) TextView tvRating;
    @BindView(R.id.tv_release_date) TextView tvReleaseDate;
    @BindView(R.id.tv_noConnectionTrailer) TextView tvNoConnectionTrailer;
    @BindView(R.id.rv_trailer)
    RecyclerView rvTrailer;
    @BindView(R.id.btn_fav)
    Button btnFav;
    @BindView(R.id.btn_mfav) Button btnMfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnFav.setVisibility(View.VISIBLE);
        btnMfav.setVisibility(View.INVISIBLE);

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFav.setVisibility(View.INVISIBLE);
                btnMfav.setVisibility(View.VISIBLE);
            }
        });

        btnMfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFav.setVisibility(View.VISIBLE);
                btnMfav.setVisibility(View.INVISIBLE);
            }
        });

        String extraJsonData = getIntent().getStringExtra("data"); // mengambil data dari intent
        if (extraJsonData != null) {
            listData = gson.fromJson(extraJsonData, MovieList.class);
            bindData();
            initCollapsingToolbar();
        }

        getData(true);
        getData(false);
        setupRecyclerviewTrailer();
        trailerAdapter.setTrailerItemClickListener(this);

    }

    private void bindData() {

        Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w154" + listData.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(cardThumbnail);

        Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w500" + listData.getBackdropPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(iv_cover);

        tvTitle.setText(listData.getTitle());
        tvOverview.setText(listData.getOverview());
        tvRating.setText(listData.getVoteAverage() + "/10");
        tvReleaseDate.setText(listData.getReleaseDate());
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Movie Detail");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getData(final boolean key) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        if(key){
            ApiURL = getResources().getString(R.string.base_url) + "/movie/" + listData.getId() + "/videos?api_key=" + BuildConfig.API_KEY;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(key){
                    TrailerResponse trailerResponse = gson.fromJson(response, TrailerResponse.class);

                    listTrailer.clear();

                    // perulangan untuk object list di json
                    for (TrailerList trailerList: trailerResponse.getResults()){
                        listTrailer.add(trailerList);
                    }

                    if(trailerAdapter.getItemCount() == 0) {
                        tvNoConnectionTrailer.setVisibility(View.VISIBLE);
                        tvNoConnectionTrailer.setText("No Trailer");
                    }else{
                        tvNoConnectionTrailer.setVisibility(View.GONE);
                    }

                    trailerAdapter.notifyDataSetChanged();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvNoConnectionTrailer.setVisibility(View.VISIBLE);

                if (error != null)
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                else
                    Log.e(TAG, "onErrorResponse: Something wrong happened");
            }
        };

        StringRequest request = new StringRequest(
                Request.Method.GET,
                ApiURL,
                responseListener,
                errorListener
        );

        requestQueue.add(request);
    }

    private void setupRecyclerviewTrailer(){
        trailerAdapter = new TrailerAdapter(this, listTrailer);
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(trailerAdapter);
    }

    @Override
    public void onTrailerItemClick(TrailerList data) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.getKey())));
    }

}
