package com.appsomniac.showbox.activity.more;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsomniac.showbox.R;
import com.appsomniac.showbox.activity.more.singleMovie.SingleMovieActivity;
import com.appsomniac.showbox.adapter.theatre.TheatreAdapter;
import com.appsomniac.showbox.base.MainActivity;
import com.appsomniac.showbox.config.VideoResponse;
import com.appsomniac.showbox.config.movie.MovieApiClient;
import com.appsomniac.showbox.config.movie.MovieApiInterface;
import com.appsomniac.showbox.config.nearby.PlaceApiClient;
import com.appsomniac.showbox.config.nearby.PlaceApiInterface;
import com.appsomniac.showbox.config.nearby.PlaceApiResponse;
import com.appsomniac.showbox.config.theatre.TheatreApiClient;
import com.appsomniac.showbox.config.theatre.TheatreApiInterface;
import com.appsomniac.showbox.config.theatre.TheatreApiResponse;
import com.appsomniac.showbox.fragments.NearbyFragment;
import com.appsomniac.showbox.model.Video;
import com.appsomniac.showbox.model.nearby.PlaceApi;
import com.appsomniac.showbox.model.nearby.Theatre;
import com.appsomniac.showbox.other.Config;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheatreActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    TheatreAdapter adapter;
    RecyclerView recyclerView;
    View listItemView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.theatre_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.theatre_recycler_view);

        adapter = new TheatreAdapter(recyclerView.getContext(), NearbyFragment.al_theatres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

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
