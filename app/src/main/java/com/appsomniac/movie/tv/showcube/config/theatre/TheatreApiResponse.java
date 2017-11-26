package com.appsomniac.movie.tv.showcube.config.theatre;

import com.appsomniac.movie.tv.showcube.model.nearby.PlaceApi;
import com.appsomniac.movie.tv.showcube.model.nearby.Theatre;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by absolutelysaurabh on 6/10/17.
 */

public class TheatreApiResponse {

    @SerializedName("result")
    private Theatre result;

    @SerializedName("status")
    private String status;

    public String getPage() {
        return status;
    }

    public Theatre getResults() {
        return result;
    }
}
