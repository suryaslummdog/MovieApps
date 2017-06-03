package com.ikadeksurya.movieapps.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by IKadekSurya on 28/05/2017.
 */

public class MovieResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<MovieList> results;

    @SerializedName("total_results")
    private int totalResults;

    public void setPage(int page){
        this.page = page;
    }

    public int getPage(){
        return page;
    }

    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public void setResults(List<MovieList> results){
        this.results = results;
    }

    public List<MovieList> getResults(){
        return results;
    }

    public void setTotalResults(int totalResults){
        this.totalResults = totalResults;
    }

    public int getTotalResults(){
        return totalResults;
    }

    @Override
    public String toString(){
        return
                "MovieResponse{" +
                        "page = '" + page + '\'' +
                        ",total_pages = '" + totalPages + '\'' +
                        ",results = '" + results + '\'' +
                        ",total_results = '" + totalResults + '\'' +
                        "}";
    }
}
