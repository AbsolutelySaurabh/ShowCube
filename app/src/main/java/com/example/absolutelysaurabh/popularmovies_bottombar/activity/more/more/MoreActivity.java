package com.example.absolutelysaurabh.popularmovies_bottombar.activity.more.more;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.more.MoreActivityContentAdapter;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.other.RecyclerViewDataAdapter;

public class MoreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoreActivityContentAdapter adapter;
    CollapsingToolbarLayout collaspingToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        String intent_message = getIntent().getStringExtra(RecyclerViewDataAdapter.EXTRA_MESSAGE);
        int intent_position = getIntent().getIntExtra("position", 0);

        collaspingToolBar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setCollaspingToolBar(collaspingToolBar, intent_message);

        adapter = new MoreActivityContentAdapter(recyclerView.getContext(), intent_position);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    public void setCollaspingToolBar(CollapsingToolbarLayout collaspingToolBar, String intent_message){

        if(intent_message.equals("Top Rated")){

            collaspingToolBar.setTitle("Top Rated Movies");
            collaspingToolBar.setCollapsedTitleTextColor(getResources().getColor(R.color.blue_grey_900));
            ImageView newsImage = (ImageView) findViewById(R.id.image);
            Glide.with(this).load(R.drawable.kaabil).thumbnail(0.5f).into(newsImage);

        }else

        if(intent_message.equals("Upcoming")){
            collaspingToolBar.setTitle("Upcoming Movies");
            collaspingToolBar.setCollapsedTitleTextColor(getResources().getColor(R.color.blue_grey_900));
            ImageView newsImage = (ImageView) findViewById(R.id.image);
            Glide.with(this).load(R.drawable.kaabil).thumbnail(0.5f).into(newsImage);
        }else
        if(intent_message.equals("Now Playing")){
            collaspingToolBar.setTitle("Now Playing Movies");
            collaspingToolBar.setCollapsedTitleTextColor(getResources().getColor(R.color.blue_grey_900));
            ImageView newsImage = (ImageView) findViewById(R.id.image);
            Glide.with(this).load(R.drawable.kaabil).thumbnail(0.5f).into(newsImage);
        }else
        if(intent_message.equals("Popular")){
            collaspingToolBar.setTitle("Popular Movies");
            collaspingToolBar.setCollapsedTitleTextColor(getResources().getColor(R.color.blue_grey_900));
            ImageView newsImage = (ImageView) findViewById(R.id.image);
            Glide.with(this).load(R.drawable.kaabil).thumbnail(0.5f).into(newsImage);
        }

    }

    @Override
    public boolean onSupportNavigateUp(){

        finish();
        return true;
    }
}
