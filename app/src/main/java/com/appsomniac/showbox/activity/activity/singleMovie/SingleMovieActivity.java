package com.appsomniac.showbox.activity.activity.singleMovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.appsomniac.showbox.R;
import com.appsomniac.showbox.adapter.recommended.adapter.Radapter;
import com.appsomniac.showbox.base.SplashActivity;
import com.appsomniac.showbox.config.VideoResponse;
import com.appsomniac.showbox.config.movie.MovieApiClient;
import com.appsomniac.showbox.config.movie.MovieApiInterface;
import com.appsomniac.showbox.config.movie.MovieResponse;
import com.appsomniac.showbox.config.tv.TvApiClient;
import com.appsomniac.showbox.config.tv.TvApiInterface;
import com.appsomniac.showbox.config.tv.TvResponse;
import com.appsomniac.showbox.model.Movie;
import com.appsomniac.showbox.model.Tv;
import com.appsomniac.showbox.model.Video;
import com.appsomniac.showbox.other.Config;
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
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleMovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static String VIDEO_URL ="";

    public static ArrayList<String> al_video_urls;

    public static final String API_KEY = Config.DEVELOPER_KEY;
    public static  String VIDEO_ID = "c25GKl5VNeY";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayer youTubePlayer;
    private YouTubePlayerFragment youTubePlayerFragment;
    private static final int RQS_ErrorDialog = 1;
    private MyPlayerStateChangeListener myPlayerStateChangeListener;
    private MyPlaybackEventListener myPlaybackEventListener;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        al_video_urls  =new ArrayList<>();

        String movieORtvORrecommended = getIntent().getStringExtra("movieORtvORrecommended");
        if(movieORtvORrecommended.equals("movie")){

            int sectionPosition = getIntent().getIntExtra("sectionPosition", 1);
            int itemPosition = getIntent().getIntExtra("itemPosition", 1);
            String movieId = getIntent().getStringExtra("movieId");

            fab = findViewById(R.id.fab);

            VIDEO_URL = Config.Youtube_movie_get_video_url_part_1 + movieId + Config.Youtube_get_video_url_part_2;
            getVideoMovie(movieId);
            getMediaDetails(movieId, "movie");

            String title  = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getTitle();
            String overview = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getOverview();
            String rating = String.valueOf(SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getVoteAverage());
            String release_date = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getReleaseDate();
            String imageUrl = SplashActivity.allMovieSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getPosterPath();

            TextView overviewView = (TextView) this.findViewById(R.id.media_overview);
            TextView ratingView = (TextView) this.findViewById(R.id.media_rating);
            ImageView media_poster = (ImageView) this.findViewById(R.id.media_poster);

            RelativeLayout date_placeholder = (RelativeLayout) this.findViewById(R.id.date_placeholder);
            TextView dateView = (TextView) this.findViewById(R.id.media_release_date);
            dateView.setVisibility(View.VISIBLE);
            date_placeholder.setVisibility(View.VISIBLE);

            overviewView.setText(overview);
            dateView.setText(release_date);
            ratingView.setText(rating);

            String posterBaseUrl = "http://image.tmdb.org/t/p/w185/"+ imageUrl;
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.superman);
            requestOptions.error(R.drawable.superman);

            Glide.with(this).load(posterBaseUrl)
                    .apply(requestOptions).thumbnail(0.5f).into(media_poster);

            getRecommeddedMovies(movieId);

        }else
            if(movieORtvORrecommended.equals("tv")){

                String tvId = getIntent().getStringExtra("tvId");
                int sectionPosition = getIntent().getIntExtra("sectionPosition", 0);
                int itemPosition = getIntent().getIntExtra("itemPosition", 0);

                VIDEO_URL = Config.Youtube_tv_get_video_url_part_1 + tvId + Config.Youtube_get_video_url_part_2;
                getVideoTv(tvId);
                getMediaDetails(tvId, "tv");

                String title  = SplashActivity.allTvSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getTitle();
                String overview = SplashActivity.allTvSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getOverview();
                String vote_average = String.valueOf(SplashActivity.allTvSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getVoteAverage());
                String imageUrl = SplashActivity.allTvSampleData.get(sectionPosition).getAllItemsInSection().get(itemPosition).getPosterPath();

                TextView overviewView = (TextView) this.findViewById(R.id.media_overview);
                TextView ratingView = (TextView) this.findViewById(R.id.media_rating);
                ImageView media_poster = (ImageView) this.findViewById(R.id.media_poster);


                RelativeLayout date_placeholder = (RelativeLayout) this.findViewById(R.id.date_placeholder);
                TextView dateView = (TextView) this.findViewById(R.id.media_release_date);
                dateView.setVisibility(View.GONE);
                date_placeholder.setVisibility(View.GONE);

                overviewView.setText(overview);
                ratingView.setText(vote_average);

                String posterBaseUrl = "http://image.tmdb.org/t/p/w185/"+ imageUrl;
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.superman);
                requestOptions.error(R.drawable.superman);

                Glide.with(this).load(posterBaseUrl)
                        .apply(requestOptions).thumbnail(0.5f).into(media_poster);

                getRecommeddedTvs(tvId);
                
            }else
                if(movieORtvORrecommended.equals("recommended_movie")){

                    String overView = getIntent().getStringExtra("movieOverView");
                    String rating = getIntent().getStringExtra("movieRating");
                    String release_date = getIntent().getStringExtra("movieReleaseDate");
                    String posterUrl = getIntent().getStringExtra("moviePosterUrl");
                    String movieId = getIntent().getStringExtra("movieId");

                    VIDEO_URL = Config.Youtube_tv_get_video_url_part_1 + movieId + Config.Youtube_get_video_url_part_2;
                    getVideoMovie((movieId));
                    getMediaDetails(movieId, "movie");

                    TextView overviewView = (TextView) this.findViewById(R.id.media_overview);
                    TextView ratingView = (TextView) this.findViewById(R.id.media_rating);
                    ImageView media_poster = (ImageView) this.findViewById(R.id.media_poster);


                    RelativeLayout date_placeholder = (RelativeLayout) this.findViewById(R.id.date_placeholder);
                    TextView dateView = (TextView) this.findViewById(R.id.media_release_date);
                    dateView.setVisibility(View.VISIBLE);
                    date_placeholder.setVisibility(View.VISIBLE);

                    //titleView.setText(title);
                    overviewView.setText(overView);
                    dateView.setText(release_date);
                    ratingView.setText(rating);

                    String posterBaseUrl = "http://image.tmdb.org/t/p/w185/"+ posterUrl;
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.superman);
                    requestOptions.error(R.drawable.superman);

                    Glide.with(this).load(posterBaseUrl)
                            .apply(requestOptions).thumbnail(0.5f).into(media_poster);

                    getRecommeddedMovies(String.valueOf(movieId));

                }else
                    if(movieORtvORrecommended.equals("recommended_tv")) {

                        {
                            String overView = getIntent().getStringExtra("tvOverview");
                            String rating = getIntent().getStringExtra("tvRating");
                            String posterUrl = getIntent().getStringExtra("tvPosterUrl");
                            String tvId = getIntent().getStringExtra("tvId");

                            VIDEO_URL = Config.Youtube_tv_get_video_url_part_1 + tvId + Config.Youtube_get_video_url_part_2;
                            getVideoTv(tvId);
                            getMediaDetails(tvId, "tv");

                            TextView overviewView = (TextView) this.findViewById(R.id.media_overview);
                            TextView ratingView = (TextView) this.findViewById(R.id.media_rating);
                            ImageView media_poster = (ImageView) this.findViewById(R.id.media_poster);

                            RelativeLayout date_placeholder = (RelativeLayout) this.findViewById(R.id.date_placeholder);
                            TextView dateView = (TextView) this.findViewById(R.id.media_release_date);
                            dateView.setVisibility(View.GONE);
                            date_placeholder.setVisibility(View.GONE);

                            //titleView.setText(title);
                            overviewView.setText(overView);
                            ratingView.setText(rating);

                            String posterBaseUrl = "http://image.tmdb.org/t/p/w185/" + posterUrl;
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.superman);
                            requestOptions.error(R.drawable.superman);

                            Glide.with(this).load(posterBaseUrl)
                                    .apply(requestOptions).thumbnail(0.5f).into(media_poster);

                            getRecommeddedMovies(tvId);
                            
                        }
                    }
    }


    public void getMediaDetails(String media_id, String movieORtv){

        String MEDIA_REQUEST_URL = null;
        if(movieORtv.equals("movie")){

            MEDIA_REQUEST_URL = "https://api.themoviedb.org/3/movie/" + media_id + "?api_key=" + Config.API_KEY + "&language=en-US\n";

        }else
            if(movieORtv.equals("tv")){

                MEDIA_REQUEST_URL = "https://api.themoviedb.org/3/tv/" + media_id + "?api_key=" + Config.API_KEY + "&language=en-US\n";
            }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, MEDIA_REQUEST_URL , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseObject) {
                try {

                    TextView genre_1 = findViewById(R.id.genre_1);
                    TextView genre_2 = findViewById(R.id.genre_2);

                    JSONArray al_genres = responseObject.getJSONArray("genres");
                    ArrayList<String> al_genres_name = new ArrayList<String>();

                    for(int i=0;i<al_genres.length();i++){

                        JSONObject obj = al_genres.getJSONObject(i);

                        al_genres_name.add(obj.getString("name"));
                    }

                    try {
                        genre_1.setText(al_genres_name.get(0));
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }catch(IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }

                    try{

                        genre_2.setText(al_genres_name.get(1));
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }catch(IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }

                    fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v="+ VIDEO_ID);
                            startActivity(Intent.createChooser(intent, "Share with"));
                        }
                    });


                } catch (JSONException e){
                    Log.e("QueryUtils", "Problem parsing the News JSON results", e);
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
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

    public void getVideoMovie(String movieId){

        MovieApiInterface apiService = MovieApiClient.getClient().create(MovieApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<VideoResponse> call = apiService.getVideo(Integer.parseInt(movieId), params);

        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse>call, Response<VideoResponse> response) {

                ArrayList<Video> videos;
                videos = response.body().getResults();

                try {
                    if(!videos.get(0).equals("null")){

                        try {
                            VIDEO_ID = videos.get(0).getKey();
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }
                    }else{

                        try {
                            VIDEO_ID = videos.get(1).getKey();
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }

                            if(VIDEO_ID.equals("null")){

                                try {
                                    VIDEO_ID = videos.get(2).getKey();
                                }catch(NullPointerException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }catch(IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }catch(NullPointerException e) {

                    e.printStackTrace();
                }

                youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
                youTubePlayerFragment.initialize(API_KEY, SingleMovieActivity.this);

                myPlayerStateChangeListener = new MyPlayerStateChangeListener();
                myPlaybackEventListener = new MyPlaybackEventListener();

            }
            @Override
            public void onFailure(Call<VideoResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

    public void getVideoTv(String tvId){

        TvApiInterface apiService = TvApiClient.getClient().create(TvApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<VideoResponse> call = apiService.getVideo(Integer.parseInt(tvId), params);

        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse>call, Response<VideoResponse> response) {

                ArrayList<Video> videos;
                videos = response.body().getResults();

                try {
                    if(!videos.get(0).equals("null")){

                        try {
                            VIDEO_ID = videos.get(0).getKey();
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }
                    }else{

                        try {
                            VIDEO_ID = videos.get(1).getKey();
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }

                        if(VIDEO_ID.equals("null")){

                            try {
                                VIDEO_ID = videos.get(2).getKey();
                            }catch(NullPointerException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }catch(IndexOutOfBoundsException e){
                    e.printStackTrace();
                }catch(NullPointerException e){
                    e.printStackTrace();
                }


                youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
                youTubePlayerFragment.initialize(API_KEY, SingleMovieActivity.this);

                myPlayerStateChangeListener = new MyPlayerStateChangeListener();
                myPlaybackEventListener = new MyPlaybackEventListener();

            }
            @Override
            public void onFailure(Call<VideoResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }


    public void getRecommeddedMovies(final String movieId){

        MovieApiInterface apiService = MovieApiClient.getClient().create(MovieApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<MovieResponse> call = apiService.getRecommendedMovies(Integer.parseInt(movieId), params);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {

                ArrayList<Movie> movies;
                movies = response.body().getResults();

                RecyclerView my_recycler_view = findViewById(R.id.my_recycler_view);
                my_recycler_view.setHasFixedSize(true);
                my_recycler_view.setNestedScrollingEnabled(true);

                Radapter adapter = new Radapter(getApplicationContext(), movies, "movie");
                my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                my_recycler_view.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

    public void getRecommeddedTvs(String tvId){

        TvApiInterface apiService = MovieApiClient.getClient().create(TvApiInterface.class);

       // https://api.themoviedb.org/3/tv/1418/recommendations?api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en");

        Call<TvResponse> call = apiService.getRecommendedTvs(Integer.parseInt(tvId), params);

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse>call, Response<TvResponse> response) {

                ArrayList<Tv> movies;
                movies = response.body().getResults();

                RecyclerView my_recycler_view = findViewById(R.id.my_recycler_view);
                my_recycler_view.setHasFixedSize(true);
                my_recycler_view.setNestedScrollingEnabled(true);

                Radapter adapter = new Radapter(movies, getApplicationContext(), "tv");
                my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                my_recycler_view.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<TvResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

}
