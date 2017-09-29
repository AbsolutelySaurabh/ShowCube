package com.example.absolutelysaurabh.popularmovies_bottombar.viewHolder.more;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Movie;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MoreActivityViewHolder extends RecyclerView.ViewHolder {

    public ImageView picture;
    public TextView title;
    public TextView releaseYear;
    public Context context;
    ImageButton like_button;
    private int position;

    public MoreActivityViewHolder(LayoutInflater inflater, ViewGroup parent, int position) {

        super(inflater.inflate(R.layout.content_more_recyclerview, parent, false));
        this.context = inflater.getContext();
        this.position = position;

        picture = itemView.findViewById(R.id.media_poster);
        title =  itemView.findViewById(R.id.media_title);
       // releaseYear = itemView.findViewById(R.id.media_release_year);
      //  like_button = itemView.findViewById(R.id.media_like_button);

//        ImageButton share_button = itemView.findViewById(R.id.media_share_button);
//        share_button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Snackbar.make(v, "Sharing....", Snackbar.LENGTH_SHORT).show();
//            }
//        });
    }
}