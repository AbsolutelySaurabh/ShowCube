package com.example.absolutelysaurabh.popularmovies_bottombar.adapter.recommended.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolutelysaurabh.popularmovies_bottombar.R;

public class RviewHolder extends RecyclerView.ViewHolder {

    public ImageView media_poster;
    public TextView description;

    public RviewHolder(LayoutInflater inflater, ViewGroup parent) {

        super(inflater.inflate(R.layout.recommended_single_card, parent, false));

        media_poster = itemView.findViewById(R.id.media_poster);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, DetailsActivity.class);
//
//                Bundle bund = new Bundle();
//                bund.putInt("tab",1);
//                bund.putInt(DetailsActivity.EXTRA_POSITION,getAdapterPosition());
//
//                intent.putExtra("bundle", bund);
//                context.startActivity(intent);
//            }
//        });
    }
}