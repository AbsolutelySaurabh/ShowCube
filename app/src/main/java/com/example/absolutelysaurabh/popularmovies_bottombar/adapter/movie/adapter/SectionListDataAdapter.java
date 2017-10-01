package com.example.absolutelysaurabh.popularmovies_bottombar.adapter.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.movie.viewHolder.SectionItemRowHolder;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Movie;

import java.util.ArrayList;

import static android.support.constraint.R.id.parent;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionItemRowHolder> {

    private ArrayList<Movie> itemsList;
    private Context mContext;
    public int sectionPosition;

    public SectionListDataAdapter(Context context, ArrayList<Movie> itemsList, int sectionPosition) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.sectionPosition = sectionPosition;
    }

    @Override
    public SectionItemRowHolder onCreateViewHolder(ViewGroup parent, int position) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
//        SectionItemRowHolder mh = new SectionItemRowHolder(v, position, itemsList, sectionPosition);
//        return mh;

        return new SectionItemRowHolder(LayoutInflater.from(parent.getContext()), parent, position, itemsList, sectionPosition);

    }

    @Override
    public void onBindViewHolder(SectionItemRowHolder holder, int position) {

        Movie singleItem = itemsList.get(position);

        String posterBaseUrl = "http://image.tmdb.org/t/p/w185/"+singleItem.getPosterPath();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.fightclub);
        requestOptions.error(R.drawable.spectre);

        Glide.with(mContext).load(posterBaseUrl)
                .apply(requestOptions).thumbnail(0.5f).into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
}