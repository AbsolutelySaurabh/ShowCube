package com.dexter.innovation.labs.showcube.config.nearby;

import com.dexter.innovation.labs.showcube.model.Video;
import com.dexter.innovation.labs.showcube.model.nearby.PlaceApi;
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
