package com.appsomniac.movie.tv.showcube.config.nearby;

import com.appsomniac.movie.tv.showcube.model.Video;
import com.appsomniac.movie.tv.showcube.model.nearby.PlaceApi;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by absolutelysaurabh on 6/10/17.
 */


public class PlaceApiResponse {

    @SerializedName("results")
    private ArrayList<PlaceApi> results;

    @SerializedName("status")
    private String status;

    public String getPage() {
        return status;
    }

    public ArrayList<PlaceApi> getResults() {
        return results;
    }

}
