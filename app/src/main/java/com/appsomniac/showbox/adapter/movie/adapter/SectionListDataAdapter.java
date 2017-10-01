package com.appsomniac.showbox.adapter.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.appsomniac.showbox.R;
import com.appsomniac.showbox.adapter.movie.viewHolder.SectionItemRowHolder;
import com.appsomniac.showbox.model.Movie;

import java.util.ArrayList;

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