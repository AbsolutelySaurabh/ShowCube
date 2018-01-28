package com.dexter.innovation.labs.showcube.adapter.recommended.viewHolder;

import android.app.Activity;
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
import com.dexter.innovation.labs.showcube.model.Tv;

import java.util.ArrayList;

public class RviewHolder extends RecyclerView.ViewHolder {

    public ImageView media_poster;
    public TextView description;

    public RviewHolder(LayoutInflater inflater, final ViewGroup parent, final ArrayList<Movie> al_movies_recommended) {

        super(inflater.inflate(R.layout.recommended_single_card, parent, false));
        media_poster = itemView.findViewById(R.id.media_poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(itemView.getContext(), SingleMovieActivity.class);

               // String movieId = String.valueOf(al_movies.get(getAdapterPosition()).getId());
                //intent.putExtra("movieId", movieId);
                intent.putExtra("movieORtvORrecommended", "recommended_movie");
                //intent.putExtra("sectionPosition", sectionPosition);
                //intent.putExtra("al_movies_recommended", al_movies_recommended);


                intent.putExtra("movieId",  String.valueOf(al_movies_recommended.get(getAdapterPosition()).getId()));

                intent.putExtra("moviePosterUrl", al_movies_recommended.get(getAdapterPosition()).getPosterPath());
                intent.putExtra("movieOverView", al_movies_recommended.get(getAdapterPosition()).getOverview());
                intent.putExtra("movieReleaseDate", al_movies_recommended.get(getAdapterPosition()).getReleaseDate());
                intent.putExtra("movieRating", String.valueOf(al_movies_recommended.get(getAdapterPosition()).getVoteAverage()));

                itemView.getContext().startActivity(intent);

                //finish the previous activity
                ((Activity)itemView.getContext()).finish();
            }
        });
    }

    public RviewHolder(LayoutInflater inflater, final ArrayList<Tv> al_tvs_recommended, ViewGroup parent) {

        super(inflater.inflate(R.layout.recommended_single_card, parent, false));

        media_poster = itemView.findViewById(R.id.media_poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(itemView.getContext(), SingleMovieActivity.class);

                // String movieId = String.valueOf(al_movies.get(getAdapterPosition()).getId());
                //intent.putExtra("movieId", movieId);
                intent.putExtra("movieORtvORrecommended", "recommended_tv");
                //intent.putExtra("sectionPosition", sectionPosition);
                //intent.putExtra("al_tv_recommended", al_tvs_recommended);
                intent.putExtra("tvId", String.valueOf(al_tvs_recommended.get(getAdapterPosition()).getId()));
                intent.putExtra("tvPosterUrl", al_tvs_recommended.get(getAdapterPosition()).getPosterPath());
                intent.putExtra("tvOverview", al_tvs_recommended.get(getAdapterPosition()).getOverview());
                intent.putExtra("tvRating", String.valueOf(al_tvs_recommended.get(getAdapterPosition()).getVoteAverage()));

                itemView.getContext().startActivity(intent);

                //finish the previous activity
                ((Activity)itemView.getContext()).finish();
            }
        });
    }
}