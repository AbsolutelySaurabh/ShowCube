package com.example.absolutelysaurabh.popularmovies_bottombar.adapter.tv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.activity.more.singleMovie.SingleMovieActivity;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.movie.viewHolder.SectionItemRowHolder;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.tv.viewHolder.Tv_SingleItemRowHolder;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Tv;

import java.util.ArrayList;

public class Tv_SectionListDataAdapter extends RecyclerView.Adapter<Tv_SingleItemRowHolder> {

    private ArrayList<Tv> itemsList;
    private Context mContext;
    public int sectionPosition;

    public Tv_SectionListDataAdapter(Context context, ArrayList<Tv> itemsList, int sectionPosition) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.sectionPosition = sectionPosition;
    }

    @Override
    public Tv_SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int position) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
//        Tv_SingleItemRowHolder mh = new Tv_SingleItemRowHolder(v, position);
//        return mh;
        return new Tv_SingleItemRowHolder(LayoutInflater.from(parent.getContext()), parent, position, itemsList, sectionPosition);
    }

    @Override
    public void onBindViewHolder(Tv_SingleItemRowHolder holder, int position) {

        Tv singleItem = itemsList.get(position);

        String posterBaseUrl = "http://image.tmdb.org/t/p/w185/"+singleItem.getPosterPath();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.superman);
        requestOptions.error(R.drawable.superman);

        Glide.with(mContext).load(posterBaseUrl)
                .apply(requestOptions).thumbnail(0.5f).into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

}