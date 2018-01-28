package com.dexter.innovation.labs.showcube.adapter.tv.viewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.innovation.labs.showcube.R;
import com.dexter.innovation.labs.showcube.activity.activity.singleMovie.SingleMovieActivity;
import com.dexter.innovation.labs.showcube.model.Tv;

import java.util.ArrayList;

public class Tv_SingleItemRowHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public ImageView itemImage;
    public int single_position;

    public Tv_SingleItemRowHolder(LayoutInflater inflater, ViewGroup parent, final int single_position, final ArrayList<Tv> itemsList, final int sectionPosition) {

        super(inflater.inflate(R.layout.list_single_card, parent, false));

        //this.tvTitle = itemView.findViewById(R.id.media_title);
        this.itemImage = itemView.findViewById(R.id.media_poster);
        this.single_position = single_position;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(itemView.getContext(), SingleMovieActivity.class);
                String tvId = String.valueOf(itemsList.get(getAdapterPosition()).getId());
                intent.putExtra("tvId", tvId);
                intent.putExtra("movieORtvORrecommended", "tv");
                intent.putExtra("sectionPosition", sectionPosition);
                Log.e("POSITION:: ", String.valueOf(getAdapterPosition()));
                intent.putExtra("itemPosition", getAdapterPosition());
                itemView.getContext().startActivity(intent);

            }
        });
    }
}