package com.appsomniac.movie.tv.showcube.adapter.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.appsomniac.movie.tv.showcube.R;
import com.appsomniac.movie.tv.showcube.adapter.movie.viewHolder.SectionItemRowHolder;
import com.appsomniac.movie.tv.showcube.model.Movie;

import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionItemRowHolder> {

    private ArrayList<Movie> itemsList;
    private Context mContext;
    private int sectionPosition;

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

        if(!posterBaseUrl.equals("null")) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.color.background_lightish);
            requestOptions.placeholder(R.drawable.wonderwoman);

            Glide.with(mContext).load(posterBaseUrl)
                    .apply(requestOptions).thumbnail(0.5f).into(holder.itemImage);
        }

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
}