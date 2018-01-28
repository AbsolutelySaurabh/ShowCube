package com.dexter.innovation.labs.showcube.adapter.more.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.innovation.labs.showcube.R;

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
        //title =  itemView.findViewById(R.id.media_title);

    }
}