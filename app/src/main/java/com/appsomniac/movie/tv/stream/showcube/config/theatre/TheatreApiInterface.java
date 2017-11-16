package com.appsomniac.movie.tv.stream.showcube.config.theatre;

import com.appsomniac.movie.tv.stream.showcube.config.nearby.PlaceApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by absolutelysaurabh on 6/10/17.
 */

public interface TheatreApiInterface {

    @GET("json")
    Call<TheatreApiResponse> getTheatreDataById(@QueryMap Map<String, String> params);
}
