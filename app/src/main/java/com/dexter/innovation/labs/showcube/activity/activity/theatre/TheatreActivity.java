package com.dexter.innovation.labs.showcube.activity.activity.theatre;

import android.media.audiofx.LoudnessEnhancer;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dexter.innovation.labs.showcube.R;
import com.dexter.innovation.labs.showcube.adapter.theatre.TheatreAdapter;
import com.dexter.innovation.labs.showcube.config.nearby.PlaceApiClient;
import com.dexter.innovation.labs.showcube.config.nearby.PlaceApiInterface;
import com.dexter.innovation.labs.showcube.config.nearby.PlaceApiResponse;
import com.dexter.innovation.labs.showcube.config.theatre.TheatreApiClient;
import com.dexter.innovation.labs.showcube.config.theatre.TheatreApiInterface;
import com.dexter.innovation.labs.showcube.config.theatre.TheatreApiResponse;
import com.dexter.innovation.labs.showcube.model.nearby.PlaceApi;
import com.dexter.innovation.labs.showcube.model.nearby.Theatre;
import com.dexter.innovation.labs.showcube.model.nearby.TheatreObject;
import com.dexter.innovation.labs.showcube.other.Config;
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

public class TheatreActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    TheatreAdapter adapter;
    RecyclerView recyclerView;
    View listItemView;
    public static ArrayList<TheatreObject> al_single_theatre_data;

    public static ArrayList<PlaceApi> al_theatres;
    LinearLayout placeholder;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.theatre_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");
        al_single_theatre_data = new ArrayList<TheatreObject>();

        Log.e("latitude: ", latitude);
        Log.e("longitude: ", longitude);

        recyclerView = (RecyclerView) findViewById(R.id.theatre_recycler_view);
        placeholder = (LinearLayout) findViewById(R.id.theatre_placeholder);
        progressBar = (ProgressBar) findViewById(R.id.theatre_progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        getPlaceApiResponse(latitude, longitude);

        // Adding Floating Action Button to bottom right of main view
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_search);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

    }

    public void getPlaceApiResponse(String latitude, String longitude){

        PlaceApiInterface apiService = PlaceApiClient.getClient().create(PlaceApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("location",latitude+","+longitude);
        params.put("radius", String.valueOf(5000));
        params.put("type", "movie_theater");
        params.put("key", Config.Places_api_key);

        Call<PlaceApiResponse> call = apiService.getNearbyTheatres(params);

        call.enqueue(new Callback<PlaceApiResponse>() {
            @Override
            public void onResponse(Call<PlaceApiResponse>call, Response<PlaceApiResponse> response) {

                //In this arrayList we have the id's of the particular theatres.
                al_theatres = response.body().getResults();

                if(al_theatres.size()==0) {

                    progressBar.setVisibility(View.GONE);
                    placeholder.setVisibility(View.VISIBLE);
                }else{

                    progressBar.setVisibility(View.GONE);
                    adapter = new TheatreAdapter(recyclerView.getContext(), al_theatres);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }
            }
            @Override
            public void onFailure(Call<PlaceApiResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
                progressBar.setVisibility(View.GONE);
                placeholder.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_theatre_activity, menu);

        MenuItem search = menu.findItem(R.id.theatre_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.theatre_search:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){

        finish();
        return true;
    }

}
