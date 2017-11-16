package com.appsomniac.movie.tv.stream.showcube.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsomniac.movie.tv.stream.showcube.R;
import com.appsomniac.movie.tv.stream.showcube.adapter.tv.adapter.Tv_RecyclerViewDataAdapter;
import com.appsomniac.movie.tv.stream.showcube.base.SplashActivity;
import com.appsomniac.movie.tv.stream.showcube.model.Tv;
import com.appsomniac.movie.tv.stream.showcube.model.section.Tv_SectionDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TvFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View tvFragment;
    private TextView mTextMessage;
    ArrayList<Tv> tvs;
    ArrayList<Tv_SectionDataModel> allMovieSampleData;
    ArrayList<Tv> al_tv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TvFragment newInstance(String param1, String param2) {
        TvFragment fragment = new TvFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tvFragment = inflater.inflate(R.layout.fragment_tv, container, false);

        allMovieSampleData = new ArrayList<Tv_SectionDataModel>();
        al_tv = new ArrayList<>();

        setTvFragment();

        return tvFragment;

    }

    public void setTvFragment(){

//        final Handler handler = new Handler();
//        new Thread(new Runnable(){
//            @Override
//            public void run(){
//                //long running code
//                //this is running on a background thread
//                        handler.post(new Runnable(){
//                            @Override
//                            public void run(){
//
//                                RecyclerView my_recycler_view = tvFragment.findViewById(R.id.my_recycler_view);
//                                my_recycler_view.setHasFixedSize(true);
//                                my_recycler_view.setNestedScrollingEnabled(true);
//
//                                Tv_RecyclerViewDataAdapter adapter = new Tv_RecyclerViewDataAdapter(getContext(), SplashActivity.allTvSampleData);
//                                my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                                my_recycler_view.setAdapter(adapter);
//
//                            }
//                        });
//            }
//        }).start();


        RecyclerView my_recycler_view = tvFragment.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setNestedScrollingEnabled(true);

        Tv_RecyclerViewDataAdapter adapter = new Tv_RecyclerViewDataAdapter(getContext(), SplashActivity.allTvSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);

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
