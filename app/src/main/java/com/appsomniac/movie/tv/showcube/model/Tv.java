package com.appsomniac.movie.tv.showcube.model;

/**
 * Created by absolutelysaurabh on 29/9/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tv {

    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String title;
   // @SerializedName("origin_country")
    //private String originCountry;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("vote_average")
    private Double voteAverage;

    public Tv(String posterPath, String overview, List<Integer> genreIds, Integer id,String originalName,
              String originalLanguage, Double voteAverage) {

        this.posterPath = posterPath;
        this.overview = overview;
        this.genreIds = genreIds;
        this.id = id;
        this.title = originalName;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
    }

    public Tv(String title){

        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}