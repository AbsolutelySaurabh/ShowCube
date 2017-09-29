package com.example.absolutelysaurabh.popularmovies_bottombar.activity.more.singleMovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.base.SplashActivity;
import com.example.absolutelysaurabh.popularmovies_bottombar.other.Config;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.internal.framed.Header;

public class SingleMovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static String URL_VIDEOS_ONE = "https://api.themoviedb.org/3/movie/";
    private static String URL_VDEOS_TWO = "/videos?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1";
    private static String VIDEO_URL ="";

    public static ArrayList<String> al_video_urls;

    public static final String API_KEY = Config.DEVELOPER_KEY;
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

        al_video_urls  =new ArrayList<>();

        String movieId = getIntent().getStringExtra("movieId");
        int sectionPosition = getIntent().getIntExtra("sectionPosition", 0);
        int itemPosition = getIntent().getIntExtra("itemPosition", 0);

        VIDEO_URL = Config.Youtube_get_video_url_part_1 + movieId + Config.Youtube_get_video_url_part_2;
        getVideo(VIDEO_URL);

        String title  = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getTitle();
        String overview = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getOverview();
        String rating = String.valueOf(SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getPopularity());
        String release_date = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getReleaseDate();
        String imageUrl = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getPosterPath();

        final String url = "http://image.tmdb.org/t/p/w185/"+imageUrl;

//        TextView titleView = (TextView) this.findViewById(R.id.movie_title);
        TextView overviewView = (TextView) this.findViewById(R.id.movie_description);
        TextView dateView = (TextView) this.findViewById(R.id.movie_release_date);
        TextView ratingView = (TextView) this.findViewById(R.id.movie_rating);

//        titleView.setText(title);
        overviewView.setText(overview);
        dateView.setText(release_date);
        ratingView.setText(rating);


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
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY , this);
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
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

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

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


}
