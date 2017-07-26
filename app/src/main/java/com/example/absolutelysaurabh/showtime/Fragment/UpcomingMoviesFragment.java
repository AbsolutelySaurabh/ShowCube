package com.example.absolutelysaurabh.showtime.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.absolutelysaurabh.showtime.Activity.SingleMovieActivity;
import com.example.absolutelysaurabh.showtime.Fragment.Movies.Movie;
import com.example.absolutelysaurabh.showtime.Fragment.Movies.MovieAdapter;
import com.example.absolutelysaurabh.showtime.Other.Config;
import com.example.absolutelysaurabh.showtime.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingMoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingMoviesFragment extends Fragment {

    public static String UPCOMING_MOVIES_URL_IN = "https://api.themoviedb.org/3/movie/upcoming?" +
            "api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1&region=IN";

    public static String UPCOMING_MOVIES_URL_US = "https://api.themoviedb.org/3/movie/upcoming?" +
            "api_key=5b4b359c1e593af429152b47752d4247&language=en-US&page=1&region=US";

    private MovieAdapter movieAdapter;
    View view;
    GridView gridView;
    View loadingIndicator;
    public static ArrayList<String> al_video_urls;
    List<Movie> al_movies;
    public static final String API_KEY = (new Config()).DEVELOPER_KEY;
    public static  String VIDEO_ID = "" ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingMoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingMoviesFragment newInstance(String param1, String param2) {
        UpcomingMoviesFragment fragment = new UpcomingMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        al_movies = new ArrayList<Movie>();

        loadingIndicator = view.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);

        getUpcoming();
        return view;
    }


    public void getUpcoming(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getActivity(), UPCOMING_MOVIES_URL_IN , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseObject) {

                try{
                    JSONArray movieArray = responseObject.getJSONArray("results");
                    for(int i=0;i<movieArray.length();i++){

                        JSONObject currentMovie = movieArray.getJSONObject(i);
                        String title = currentMovie.getString("original_title");
                        String overview = currentMovie.getString("overview");
                        String poster_url = currentMovie.getString("poster_path");
                        String release_date = currentMovie.getString("release_date");
                        String movie_id = String.valueOf(currentMovie.getInt("id"));
                        double rating = currentMovie.getInt("vote_average");

                        if(!poster_url.equals("null")) {
                            Movie movie = new Movie(poster_url, title, overview, release_date, rating, movie_id);
                            al_movies.add(movie);
                        }
                    }

                    getUsUpcomingMovies();

                } catch (JSONException e) {
                    Log.e("MovieQuery", " Problem parsing the movie JSON results ",e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error Loading Movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUsUpcomingMovies(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getActivity(), UPCOMING_MOVIES_URL_US , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseObject) {
                try{
                    JSONArray movieArray = responseObject.getJSONArray("results");

                    for(int i=0;i<movieArray.length();i++){

                        JSONObject currentMovie = movieArray.getJSONObject(i);
                        String title = currentMovie.getString("original_title");
                        String overview = currentMovie.getString("overview");
                        String poster_url = currentMovie.getString("poster_path");
                        String release_date = currentMovie.getString("release_date");
                        String movie_id = String.valueOf(currentMovie.getInt("id"));
                        double rating = currentMovie.getInt("vote_average");

                        Movie movie = new Movie(poster_url, title, overview, release_date, rating, movie_id);
                        //al_movies.add(movie);
                        int flag=1;
                        for(int j=0;j<al_movies.size();j++){

                            if(al_movies.get(j).getMovieId().equals(movie_id)){

                                flag=0;
                            }
                        }
                        if(flag==1){
                            al_movies.add(movie);
                        }
                    }
                    if(al_movies.size()%2!=0){
                        al_movies.remove(al_movies.size()-1);
                    }
                    loadingIndicator.setVisibility(View.GONE);
                    try {
                        //I need to send the arrayList of "movie" in the adapter;
                        movieAdapter = new MovieAdapter(getContext(), al_movies);

                    }catch(NullPointerException e){
                        Log.d("NullPointerException: ", "");
                    }
                    gridView.setAdapter(movieAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Movie currentMovie = movieAdapter.getItem(position);

                            Intent i = new Intent(getContext(),SingleMovieActivity.class);
                            //Pass the image index
                            i.putExtra("movies",currentMovie);
                            startActivity(i);
                        }
                    });

                } catch (JSONException e) {
                    Log.e("MovieQuery", " Problem parsing the movie JSON results ",e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error Loading Movies", Toast.LENGTH_SHORT).show();
            }
        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
