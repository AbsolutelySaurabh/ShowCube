package com.appsomniac.movie.tv.stream.showcube.config.tv;

/**
 * Created by absolutelysaurabh on 23/9/17.
 */

import com.appsomniac.movie.tv.stream.showcube.model.Tv;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class TvResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Tv> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Tv> getResults() {
        return results;
    }

    public void setResults(ArrayList<Tv> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}