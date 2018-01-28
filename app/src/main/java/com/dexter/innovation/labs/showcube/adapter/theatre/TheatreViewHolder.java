package com.dexter.innovation.labs.showcube.adapter.theatre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexter.innovation.labs.showcube.R;
import com.dexter.innovation.labs.showcube.model.nearby.PlaceApi;
import com.dexter.innovation.labs.showcube.model.nearby.Theatre;

import java.util.ArrayList;

public class TheatreViewHolder extends RecyclerView.ViewHolder {

    public ImageView theatre_image;
    public TextView theatre_name;
    public TextView theatre_address;
    public ImageButton theatre_website;
    public ImageButton theatre_phone_number;
    public ImageButton theatre_map_url;
    public TextView theatre_rating;

    private int theatre_position;

    public ArrayList<PlaceApi> al_theatres;
    public ArrayList<Theatre> al_single_theatre_data;

    private Theatre single_theatre;
    private Context mContext;

    public TheatreViewHolder(LayoutInflater inflater, ViewGroup parent, ArrayList<PlaceApi> al_theatres, Context context, final int position) {

        super(inflater.inflate(R.layout.item_theatre, parent, false));
        this.al_theatres = al_theatres;
        this.mContext = context;
        this.theatre_position = position;
        al_single_theatre_data = new ArrayList<>();

        single_theatre = new Theatre();

        initialize();
    }


    public void initialize(){

        theatre_image = (ImageView) itemView.findViewById(R.id.theatre_image);
        theatre_name = (TextView) itemView.findViewById(R.id.theatre_name);
        theatre_address = (TextView) itemView.findViewById(R.id.theatre_address);
    }
}