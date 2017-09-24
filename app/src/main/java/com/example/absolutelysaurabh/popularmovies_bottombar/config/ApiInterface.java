package com.example.absolutelysaurabh.popularmovies_bottombar.config;

/**
 * Created by absolutelysaurabh on 23/9/17.
 */

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiInterface {

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@QueryMap Map<String, String> params);
    //movie/now_playing?api_key= " " & language = " ";

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMoviesSimpleQuery( @Query("api_key") String apiKey);
    //movie/now_playing?api_key= " " & language = " ";

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}