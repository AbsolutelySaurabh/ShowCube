package com.dexter.innovation.labs.showcube.config.movie;

/**
 * Created by absolutelysaurabh on 23/9/17.
 */

import com.dexter.innovation.labs.showcube.config.VideoResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface MovieApiInterface {

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@QueryMap Map<String, String> params);
    //movie/now_playing?api_key= " " & language = " ";

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@QueryMap Map<String, String> params);;
    //movie/now_playing?api_key= " " & language = " ";

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@QueryMap Map<String, String> params);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@QueryMap Map<String, String> params);

  //  https://api.themoviedb.org/3/movie/461311/videos?api_key=5b4b359c1e593af429152b47752d4247&language=en-US

    @GET("movie/{id}/videos")
    Call<VideoResponse> getVideo(@Path("id") int id, @QueryMap Map<String, String> params);

    // https://api.themoviedb.org/3/movie/346364/recommendations?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1

    @GET("movie/{id}/recommendations")
    Call<MovieResponse> getRecommendedMovies(@Path("id") int id, @QueryMap Map<String, String> params);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}