package com.example.absolutelysaurabh.popularmovies_bottombar.config.tv;

/**
 * Created by absolutelysaurabh on 23/9/17.
 */

import com.example.absolutelysaurabh.popularmovies_bottombar.config.VideoResponse;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.movie.MovieResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface TvApiInterface {

    @GET("tv/airing_today")
    Call<TvResponse> getTvAiringToday(@QueryMap Map<String, String> params);
    //movie/now_playing?api_key= " " & language = " ";

    @GET("tv/on_the_air")
    Call<TvResponse> getTvOnTheAir(@QueryMap Map<String, String> params);

    @GET("tv/top_rated")
    Call<TvResponse> getTvTopRated(@QueryMap Map<String, String> params);

    @GET("tv/popular")
    Call<TvResponse> getTvPopular(@QueryMap Map<String, String> params);

//    https://api.themoviedb.org/3/tv/1418/videos?api_key=5b4b359c1e593af429152b47752d4247&language=en-US
    @GET("tv/{id}/videos")
    Call<VideoResponse> getVideo(@Path("id") int id, @QueryMap Map<String, String> params);

    //https://api.themoviedb.org/3/tv/1418/recommendations?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1
    @GET("tv/{id}/recommendations")
    Call<TvResponse> getRecommendedTvs(@Path("id") int id, @QueryMap Map<String, String> params);
}