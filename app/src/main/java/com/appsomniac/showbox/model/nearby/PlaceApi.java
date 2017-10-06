package com.appsomniac.showbox.model.nearby;

import com.appsomniac.showbox.config.nearby.PlaceApiInterface;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by absolutelysaurabh on 6/10/17.
 */

public class PlaceApi implements Serializable {

    @SerializedName("place_id")
    private String theatre_id;
    @SerializedName("name")
    private String theatre_name;
    @SerializedName("rating")
    private String theatre_rating;

    @SerializedName("photos")
    private ArrayList<PlacePhoto> theatre_image_url;
    @SerializedName("vicinity")
    private String theatre_address;


    public PlaceApi(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                   String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                   Integer voteCount, Boolean video, Double voteAverage) {


    }

    public PlaceApi(String name){

        this.theatre_name = name;
    }

    public ArrayList<PlacePhoto> getTheatre_image_url(){
        return theatre_image_url;
    }

    public String getTheatre_name(){

        return theatre_name;
    }

    public String getTheatre_rating(){
        return theatre_rating;
    }

    public String getTheatre_id(){
        return theatre_id;
    }
    public String getTheatre_Address(){
        return theatre_address;
    }

}
