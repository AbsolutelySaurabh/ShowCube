package com.appsomniac.showbox.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.appsomniac.showbox.R;
import com.appsomniac.showbox.config.movie.MovieApiClient;
import com.appsomniac.showbox.config.movie.MovieApiInterface;
import com.appsomniac.showbox.config.movie.MovieResponse;
import com.appsomniac.showbox.config.tv.TvApiClient;
import com.appsomniac.showbox.config.tv.TvApiInterface;
import com.appsomniac.showbox.config.tv.TvResponse;
import com.appsomniac.showbox.model.Movie;
import com.appsomniac.showbox.model.Tv;
import com.appsomniac.showbox.model.section.Movie_SectionDataModel;
import com.appsomniac.showbox.model.section.Tv_SectionDataModel;
import com.appsomniac.showbox.other.Config;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity{


    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    public static String currentLocation;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    private TextView mTextMessage;
    public static ArrayList<Movie_SectionDataModel> allMovieSampleData;
    public static ArrayList<Movie_SectionDataModel> allMovieSampleData_afterDeletion;

    public static ArrayList<Tv_SectionDataModel> allTvSampleData;
    public static ArrayList<Tv_SectionDataModel> allTvSampleData_afterDeletion;
    public static ArrayList<Movie> al_movie;
    public static ArrayList<Tv> al_tv;

    public static final int REQUEST_LOCATION_CODE = 99;

    ArrayList<String> permissions=new ArrayList<>();

    boolean isPermissionGranted;

    private static final String TAG = SplashActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API

    private GoogleApiClient mGoogleApiClient;
    double latitude;
    double longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        allMovieSampleData = new ArrayList<Movie_SectionDataModel>();
        allMovieSampleData_afterDeletion = new ArrayList<Movie_SectionDataModel>();

        allTvSampleData  =new ArrayList<>();
        allTvSampleData_afterDeletion  =new ArrayList<>();

        al_movie = new ArrayList<>();
        al_tv = new ArrayList<>();

        //locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       // getLocation();

        SharedPreferences.Editor editor = getSharedPreferences("user_data", MODE_PRIVATE).edit();

        getSetMovieFragmentUi();
        getSetTvFragmentUi();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    void getLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.e("FIRST NOT AVAILABLE:","");

            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Log.e("Latitude: ", String.valueOf(latitude));
                Log.e("Longitude: ", String.valueOf(longitude));


            } else {
                Log.e("Location NOT AVAILABLE:","");

            }
        }
        
        getAddress();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }



    public void getAddress()
    {

        Address locationAddress=getAddress(latitude,longitude);

        if(locationAddress!=null)
        {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+="\n"+address1;

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+="\n"+city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=" - "+postalCode;
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+="\n"+postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                    currentLocation+="\n"+country;

            }
            
            Log.e("currentLocation: ", currentLocation);

        }

    }

    public Address getAddress(double latitude,double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void getNowPlayingMovies(final Movie_SectionDataModel dm){

        MovieApiInterface apiService = MovieApiClient.getClient().create(MovieApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("region", "IN");

        Call<MovieResponse> call = apiService.getNowPlayingMovies(params);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {

                ArrayList<Movie> movies;
                movies = response.body().getResults();

               // Log.e("DATA NOW_PLAYING: ", movies.get(0).getTitle());

                //movies.subList(8, movies.size()).clear();

                dm.setAllItemsInSection(movies);
                allMovieSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of movies received: " + String.valueOf(movies.size()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

    public void getTopRatedMovies(final Movie_SectionDataModel dm){

        MovieApiInterface apiService = MovieApiClient.getClient().create(MovieApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("region", "IN");

        Call<MovieResponse> call = apiService.getTopRatedMovies(params);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {

                ArrayList<Movie> movies;
                movies = response.body().getResults();

               // Log.e("Data Top Rated...: ", movies.get(0).getTitle());
                //movies.subList(8, movies.size()).clear();

                dm.setAllItemsInSection(movies);
                allMovieSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of Top Rated movies received: " + String.valueOf(movies.size()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }


    public void getUpcomingMovies(final Movie_SectionDataModel dm){

        MovieApiInterface apiService = MovieApiClient.getClient().create(MovieApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("region", "IN");

        Call<MovieResponse> call = apiService.getUpcomingMovies(params);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {

                ArrayList<Movie> movies;
                movies = response.body().getResults();

                //Log.e("Data UPCOMING...: ", movies.get(0).getTitle());
                //movies.subList(8, movies.size()).clear();

                dm.setAllItemsInSection(movies);
                allMovieSampleData.add(dm);

                //setNowPlayingMovies();

                Log.e("SUCCESS: ", "Number of Top Rated movies received: " + String.valueOf(movies.size()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }


    public void getPopularMovies(final Movie_SectionDataModel dm){

        MovieApiInterface apiService = MovieApiClient.getClient().create(MovieApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("region", "IN");

        Call<MovieResponse> call = apiService.getPopularMovies(params);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {

                ArrayList<Movie> movies;
                movies = response.body().getResults();

               // Log.e("Data POPULAR...: ", movies.get(0).getTitle());
                //movies.subList(8, movies.size()).clear();

                dm.setAllItemsInSection(movies);
                allMovieSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of Top Rated movies received: " + String.valueOf(movies.size()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

    public void getAiringTodayTvs(final Tv_SectionDataModel dm){

        TvApiInterface apiService = TvApiClient.getClient().create(TvApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<TvResponse> call = apiService.getTvAiringToday(params);

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse>call, Response<TvResponse> response) {

                ArrayList<Tv> tvs;
                tvs = response.body().getResults();

                Log.e("TV AIRING TODAY...: ", tvs.get(0).getTitle());
                //tvs.subList(8, tvs.size()).clear();

                dm.setAllItemsInSection(tvs);
                allTvSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of AIRING Tvs received: " + String.valueOf(tvs.size()));

            }
            @Override
            public void onFailure(Call<TvResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

    public void getOnTheAirTvs(final Tv_SectionDataModel dm){

        TvApiInterface apiService = TvApiClient.getClient().create(TvApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<TvResponse> call = apiService.getTvOnTheAir(params);

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse>call, Response<TvResponse> response) {

                ArrayList<Tv> tvs;
                tvs = response.body().getResults();

                //tvs.subList(8, tvs.size()).clear();

                dm.setAllItemsInSection(tvs);
                allTvSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of On THE AIR TVs received: " + String.valueOf(tvs.size()));

            }
            @Override
            public void onFailure(Call<TvResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }


    public void getPopularTvs(final Tv_SectionDataModel dm){

        TvApiInterface apiService = TvApiClient.getClient().create(TvApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<TvResponse> call = apiService.getTvPopular(params);

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse>call, Response<TvResponse> response) {

                ArrayList<Tv> tvs;
                tvs = response.body().getResults();

               // tvs.subList(8, tvs.size()).clear();

                dm.setAllItemsInSection(tvs);
                allTvSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of Popular TVs received: " + String.valueOf(tvs.size()));

            }
            @Override
            public void onFailure(Call<TvResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }


    public void getTopRatedTvs(final Tv_SectionDataModel dm){

        TvApiInterface apiService = TvApiClient.getClient().create(TvApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("language", "en-US");

        Call<TvResponse> call = apiService.getTvTopRated(params);

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse>call, Response<TvResponse> response) {

                ArrayList<Tv> tvs;
                tvs = response.body().getResults();

               // tvs.subList(8, tvs.size()).clear();

                dm.setAllItemsInSection(tvs);
                allTvSampleData.add(dm);

                Log.e("SUCCESS: ", "Number of Top Rated TVs received: " + String.valueOf(tvs.size()));

            }
            @Override
            public void onFailure(Call<TvResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });
    }

    public void getSetTvFragmentUi() {
        for (int i = 0; i < 4; i++) {

            Tv_SectionDataModel dm = new Tv_SectionDataModel();

            if(i==0){

                dm.setHeaderTitle("Airing Today");
                getAiringTodayTvs(dm);
                // setNowPlayingMovies();

            }else
            if(i==1){

                dm.setHeaderTitle("On The Air");
                getOnTheAirTvs(dm);
                // setNowPlayingMovies();
            }else
            if(i==2){
                dm.setHeaderTitle("Top Rated");
                getTopRatedTvs(dm);
                //setNowPlayingMovies();

            }else
            if(i==3){

                dm.setHeaderTitle("Popular");
                getPopularTvs(dm);
            }

        }
    }


    public void getSetMovieFragmentUi() {
        for (int i = 0; i < 4; i++) {

            Movie_SectionDataModel dm = new Movie_SectionDataModel();

            if(i==0){

                dm.setHeaderTitle("Upcoming");
                getUpcomingMovies(dm);
                // setNowPlayingMovies();

            }else
            if(i==1){

                dm.setHeaderTitle("Top Rated");
                getTopRatedMovies(dm);
                // setNowPlayingMovies();
            }else
            if(i==2){
                dm.setHeaderTitle("Popular");
                getPopularMovies(dm);
                //setNowPlayingMovies();

            }else
            if(i==3){

                dm.setHeaderTitle("Now Playing");
                getNowPlayingMovies(dm);
            }

        }
    }

}
