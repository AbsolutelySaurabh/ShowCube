package com.example.absolutelysaurabh.popularmovies_bottombar.adapter.tv;

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
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Movie;

import java.util.ArrayList;

public class Tv_SectionListDataAdapter extends RecyclerView.Adapter<Tv_SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<Movie> itemsList;
    private Context mContext;
    public int sectionPosition;

    public Tv_SectionListDataAdapter(Context context, ArrayList<Movie> itemsList, int sectionPosition) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.sectionPosition = sectionPosition;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v, position);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {

        Movie singleItem = itemsList.get(position);

        String posterBaseUrl = "http://image.tmdb.org/t/p/w185/"+singleItem.getPosterPath();

//        Glide.with(mContext)
//                .load(posterBaseUrl)
//                .error(R.drawable.fightclub)
//                .thumbnail(0.5f)
//                .into(holder.itemImage);

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

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public ImageView itemImage;
        public int single_position;

        public SingleItemRowHolder(View view, final int single_position) {
            super(view);

            this.tvTitle = view.findViewById(R.id.media_title);
            this.itemImage = view.findViewById(R.id.media_poster);
            this.single_position = single_position;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, SingleMovieActivity.class);
                    String movieId = String.valueOf(itemsList.get(single_position).getId());
                    intent.putExtra("movieId", movieId);
                    intent.putExtra("sectionPosition", sectionPosition);
                    Log.e("POSITION:: ", String.valueOf(single_position));
                    intent.putExtra("itemPosition", single_position);
                    mContext.startActivity(intent);

                }
            });
        }
    }
}