package com.example.absolutelysaurabh.popularmovies_bottombar.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absolutelysaurabh.popularmovies_bottombar.R;
import com.example.absolutelysaurabh.popularmovies_bottombar.adapter.other.RecyclerViewDataAdapter;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.ApiClient;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.ApiInterface;
import com.example.absolutelysaurabh.popularmovies_bottombar.other.Config;
import com.example.absolutelysaurabh.popularmovies_bottombar.config.MovieResponse;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.Movie;
import com.example.absolutelysaurabh.popularmovies_bottombar.model.SectionDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View movieFragment;
    private TextView mTextMessage;
    List<Movie> movies;
    ArrayList<SectionDataModel> allSampleData;
    ArrayList<Movie> al_movie;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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

        movieFragment = inflater.inflate(R.layout.fragment_movie, container, false);

        allSampleData = new ArrayList<SectionDataModel>();
        al_movie = new ArrayList<>();

        getMovies();
        createDummyData();
       // Log.e("SECOND SUCCESS: ", "Number of movies received: " + String.valueOf(movies.size()));


        return movieFragment;
    }

    public void getMovies(){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", Config.API_KEY);
        params.put("region", "IN");

        Call<MovieResponse> call = apiService.getNowPlayingMoviesSimpleQuery(Config.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {

                movies = response.body().getResults();

                RecyclerView my_recycler_view = movieFragment.findViewById(R.id.my_recycler_view);
                my_recycler_view.setHasFixedSize(true);
                my_recycler_view.setNestedScrollingEnabled(true);

                RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);
                my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                my_recycler_view.setAdapter(adapter);
                Log.e("SUCCESS: ", "Number of movies received: " + String.valueOf(movies.size()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("FAILURE: ", t.toString());
            }
        });

    }

    public void createDummyData() {
        for (int i = 0; i < 4; i++) {

            SectionDataModel dm = new SectionDataModel();


            if(i==0){

                dm.setHeaderTitle("Now Playing");

               // setNowPlayingData();


            }else
                if(i==1){

                    dm.setHeaderTitle("Popular");
                }else
                    if(i==2){
                        dm.setHeaderTitle("Top Rated");

                    }else
                        if(i==3){

                            dm.setHeaderTitle("Upcoming");
                        }


            ArrayList<Movie> singleItem = new ArrayList<Movie>();
            for (int j = 0; j <= 7; j++) {

                singleItem.add(new Movie(" "));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
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
