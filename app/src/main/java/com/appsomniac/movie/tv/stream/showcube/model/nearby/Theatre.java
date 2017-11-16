package com.appsomniac.movie.tv.stream.showcube.model.nearby;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by absolutelysaurabh on 6/10/17.
 */

public class Theatre {

    @SerializedName("formatted_phone_number")
    private String theatre_phone_number;
    @SerializedName("id")
    private String theatre_id;
    @SerializedName("name")
    private String theatre_name;
    @SerializedName("rating")
    private String theatre_rating;
    @SerializedName("reference")
    private String theatre_image_url;
    @SerializedName("url")
    private String theatre_map_url;
    @SerializedName("vicinity")
    private String theatre_address;
    @SerializedName("website")
    private String theatre_website;
    //@SerializedName("reviews")
   // private ArrayList<ArrayList<String>> theatre_reviews;

    public Theatre(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                 String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                 Integer voteCount, Boolean video, Double voteAverage) {
//        this.posterPath = posterPath;
//        this.adult = adult;
//        this.overview = overview;
//        this.releaseDate = releaseDate;
//        this.genreIds = genreIds;
//        this.id = id;
//        this.originalTitle = originalTitle;

    }

    public Theatre(){

    }

    public Theatre(String name){

        this.theatre_name = name;
    }

    public String getTheatre_image_url(){
        return theatre_image_url;
    }
    public String getTheatre_phone_number(){
        return theatre_phone_number;
    }

    public String getTheatre_name(){

        return theatre_name;
    }

    public String getTheatre_rating(){
        return theatre_rating;
    }

    public String getTheatre_map_url(){
        return theatre_map_url;
    }

    public String getTheatre_website(){
        return theatre_website;
    }

    public String getTheatre_id(){
        return theatre_id;
    }
    public String getTheatre_Address(){
        return theatre_address;
    }

}
