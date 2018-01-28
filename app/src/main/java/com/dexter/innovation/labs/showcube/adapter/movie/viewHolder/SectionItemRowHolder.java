package com.dexter.innovation.labs.showcube.adapter.movie.viewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.innovation.labs.showcube.R;
import com.dexter.innovation.labs.showcube.activity.activity.singleMovie.SingleMovieActivity;
import com.dexter.innovation.labs.showcube.model.Movie;

import java.util.ArrayList;

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

        //this.tvTitle = itemView.findViewById(R.id.media_title);
        this.itemImage = itemView.findViewById(R.id.media_poster);
        this.single_position = single_position;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(itemView.getContext(), SingleMovieActivity.class);

                String movieId = String.valueOf(itemsList.get(getAdapterPosition()).getId());
                intent.putExtra("movieId", movieId);
                intent.putExtra("videoId", VIDEO_ID);
                intent.putExtra("movieORtvORrecommended", "movie");
                intent.putExtra("sectionPosition", sectionPosition);
                intent.putExtra("itemPosition", getAdapterPosition());
                itemView.getContext().startActivity(intent);

            }
        });
    }
}