package com.example.absolutelysaurabh.showtime.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absolutelysaurabh.showtime.Fragment.Movies.Movie;
import com.example.absolutelysaurabh.showtime.Other.Config;
import com.example.absolutelysaurabh.showtime.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SingleMovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static String URL_VIDEOS_ONE = "https://api.themoviedb.org/3/movie/";
    private static String URL_VDEOS_TWO = "/videos?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1";
    private static String VIDEO_URL ="";
    public static ArrayList<String> al_video_urls;

    public static final String API_KEY = (new Config()).DEVELOPER_KEY;
    public static  String VIDEO_ID = "";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayer youTubePlayer;
    private YouTubePlayerFragment youTubePlayerFragment;
    private static final int RQS_ErrorDialog = 1;
    private MyPlayerStateChangeListener myPlayerStateChangeListener;
    private MyPlaybackEventListener myPlaybackEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        ScrollView sv = new ScrollView(this);
        Movie movie = (Movie) getIntent().getSerializableExtra("movies");

        String movie_id = movie.getMovieId();
        VIDEO_URL = URL_VIDEOS_ONE + movie_id + URL_VDEOS_TWO;
        al_video_urls = new ArrayList<String>();

        getVideo(VIDEO_URL);

        String title  = movie.getTitle();
        String overview = movie.getOverview();
        String rating = movie.getRating();
        String release_date = movie.getReleaseDate();
        String imageUrl = movie.getPoster_path_url();
        Log.e("movie id : ", movie_id);
        final String url = "http://image.tmdb.org/t/p/w185/"+imageUrl;

//        TextView titleView = (TextView) this.findViewById(R.id.movie_title);
        TextView overviewView = (TextView) this.findViewById(R.id.movie_description);
        TextView dateView = (TextView) this.findViewById(R.id.movie_release_date);
        TextView ratingView = (TextView) this.findViewById(R.id.movie_rating);

//        titleView.setText(title);
        overviewView.setText(overview);
        dateView.setText(release_date);
        ratingView.setText(rating);

        ImageView imageView = (ImageView) this.findViewById(R.id.movie_image);
        Picasso.with(this).load(url).into(imageView);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_KEY , this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtubeplayerfragment);
    }

    public final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(
                com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
            finish();
        }
        @Override
        public void onVideoStarted() {
        }
    }
    public class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener{
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    }

    public void getVideo(String video_url){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, video_url , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("Response VIDEOS GET: ", response.toString());

                try {
                    JSONArray array = response.getJSONArray("results");
                    for(int i=0; i< array.length();i++){

                        JSONObject object = array.getJSONObject(i);
                        String video_key = object.getString("key");
                        al_video_urls.add(video_key);

                    }

                    try {
                        if(!al_video_urls.get(0).equals("null")){

                            VIDEO_ID = al_video_urls.get(0);
                        }else{

                            VIDEO_ID = al_video_urls.get(1);
                        }
                    }catch(IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }

                    youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
                    youTubePlayerFragment.initialize(API_KEY, SingleMovieActivity.this);

                    myPlayerStateChangeListener = new MyPlayerStateChangeListener();
                    myPlaybackEventListener = new MyPlaybackEventListener();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ImageView trailer_1 = (ImageView) findViewById(R.id.trailer_1);
                ImageView trailer_2 = (ImageView) findViewById(R.id.trailer_2);
                ImageView trailer_3 = (ImageView) findViewById(R.id.trailer_3);
                LinearLayout trailer_1_linearLayout = (LinearLayout) findViewById(R.id.trailer_1_linearLayout);
                LinearLayout trailer_2_linearLayout = (LinearLayout) findViewById(R.id.trailer_2_linearLayout);
                LinearLayout trailer_3_linearLayout = (LinearLayout) findViewById(R.id.trailer_3_linearLayout);

                if(al_video_urls.size() > 1) {

                    trailer_2_linearLayout.setVisibility(View.VISIBLE);
                    trailer_2_linearLayout.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), TrailerActivity.class);
                            i.putExtra("video_id", al_video_urls.get(1));
                            startActivity(i);
                        }
                    });
                }

                if(al_video_urls.size() > 2) {

                    trailer_3_linearLayout.setVisibility(View.VISIBLE);
                    trailer_3_linearLayout.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), TrailerActivity.class);
                            i.putExtra("video_id", al_video_urls.get(2));
                            startActivity(i);
                        }
                    });
                }

                trailer_1_linearLayout.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Intent i = new Intent(getApplicationContext(), TrailerActivity.class);
                        i.putExtra("video_id", al_video_urls.get(0));
                        startActivity(i);

                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    protected void onResume() {

        Log.e("resumee: ","...");
        getVideo(VIDEO_URL);
        super.onResume();
    }
}
