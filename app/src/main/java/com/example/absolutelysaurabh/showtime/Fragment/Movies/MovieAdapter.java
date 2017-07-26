package com.example.absolutelysaurabh.showtime.Fragment.Movies;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.absolutelysaurabh.showtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Created by absolutelysaurabh on 28/5/17.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context context;
    public MovieAdapter(Context c){

        super(c,0);
        context = c;
    }
    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView=  convertView;
        if(listItemView==null){
            listItemView =  LayoutInflater.from(getContext()).inflate(R.layout.grid_item,parent,false);
        }
        Movie currentMovie = getItem(position);

        String url = currentMovie.getPoster_path_url();
        String newUrl = "http://image.tmdb.org/t/p/w185/"+url;
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        Picasso.with(context).load(newUrl).resize(355,530).centerCrop().error(R.drawable.life).into(imageView);

//        RatingBar ratingBar = (RatingBar)listItemView.findViewById(R.id.ratingBar);
//        ratingBar.setRating((float) 2);

        return listItemView;
    }
}