package com.appsomniac.showbox.adapter.theatre;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsomniac.showbox.R;

/**
 * Created by absolutelysaurabh on 6/10/17.
 */



public class TheatreViewHolder extends RecyclerView.ViewHolder {

    public ImageView theatre_image;
    public TextView theatre_name;
    public TextView theatre_address;
    public TextView theatre_website;
    public TextView theatre_phone_number;
    public TextView theatre_map_url;
    public TextView theatre_rating;

    public TheatreViewHolder(LayoutInflater inflater, ViewGroup parent) {

        super(inflater.inflate(R.layout.item_theatre, parent, false));

        theatre_image = (ImageView) itemView.findViewById(R.id.theatre_image);
        theatre_name = (TextView) itemView.findViewById(R.id.theatre_name);
        theatre_address = (TextView) itemView.findViewById(R.id.theatre_address);

//        theatre_website = (ImageView) itemView.findViewById(R.id.theatre_image);
//        theatre_phone_number = (TextView) itemView.findViewById(R.id.theatre_name);
//        theatre_map_url = (TextView) itemView.findViewById(R.id.theatre_,);
//        theatre_rating = (TextView) itemView.findViewById(R.id.theatre_rating);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailsActivity.class);
//
//                    Bundle bund = new Bundle();
//                    bund.putInt("tab",4);
//                    bund.putInt(DetailsActivity.EXTRA_POSITION,getAdapterPosition());
//
//                    intent.putExtra("bundle", bund);
//                    context.startActivity(intent);
            }
        });


        ImageButton deleteImageButton = (ImageButton) itemView.findViewById(R.id.theatre_address_button);

        ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.theatre_call);
        //          shareImageButton.setOnClickListener(new View.OnClickListener(){
        //    @Override
        //  public void onClick(View v) {
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, al_news.get(getAdapterPosition()).getUrl());
//                    sendIntent.setType("text/plain");
//                    startActivity(Intent.createChooser(sendIntent,("Share news via:")));
//                    Snackbar.make(v, "Sharing....", Snackbar.LENGTH_SHORT).show();
        //  }
        // });
    }
}