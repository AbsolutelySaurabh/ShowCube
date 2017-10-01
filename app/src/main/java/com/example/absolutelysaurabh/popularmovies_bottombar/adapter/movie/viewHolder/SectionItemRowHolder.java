package com.example.absolutelysaurabh.popularmovies_bottombar.adapter.movie.viewHolder;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.activity.more.singleMovie.SingleMovieActivity;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.recommended.adapter.Radapter;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.VideoResponse;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.movie.MovieApiClient;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.movie.MovieApiInterface;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.movie.MovieResponse;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Movie;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Video;
import com.example.absolutelysaurabh.popularmovies_bottombar.other.Config;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by absolutelysaurabh on 30/9/17.
 */

public class SectionItemRowHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public ImageView itemImage;
    public int single_position;
    public String VIDEO_URL;
    public static ArrayList<String> al_video_urls;
    public static  String VIDEO_ID;
    public static String movieId;

    public SectionItemRowHolder(LayoutInflater inflater, ViewGroup parent, int single_position, final ArrayList<Movie> itemsList, final int sectionPosition) {

        super(inflater.inflate(R.layout.list_single_card, parent, false));

        this.tvTitle = itemView.findViewById(R.id.media_title);
        this.itemImage = itemView.findViewById(R.id.media_poster);
        this.single_position = single_position;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(itemView.getContext(), SingleMovieActivity.class);

                String movieId = String.valueOf(itemsList.get(getAdapterPosition()).getId());
                intent.putExtra("movieId", movieId);
                intent.putExtra("videoId", VIDEO_ID);
                intent.putExtra("movieORtv", "movie");
                intent.putExtra("sectionPosition", sectionPosition);
                intent.putExtra("itemPosition", getAdapterPosition());
                itemView.getContext().startActivity(intent);

            }
        });
    }
}