package com.appsomniac.showbox.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsomniac.showbox.R;
import com.appsomniac.showbox.base.MainActivity;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearbyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NearbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearbyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View nearbyFragment;

    Button btnProceed;
    TextView tvAddress;
    TextView tvEmpty;
    RelativeLayout rlPick;
    ProgressBar locationProgress;

    public static String currentLocation="";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;

    public NearbyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearbyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearbyFragment newInstance(String param1, String param2) {
        NearbyFragment fragment = new NearbyFragment();
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
        nearbyFragment = inflater.inflate(R.layout.fragment_nearby, container, false);
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        btnProceed = nearbyFragment.findViewById(R.id.btnLocation);
        tvAddress = nearbyFragment.findViewById(R.id.tvAddress);
        tvEmpty = nearbyFragment.findViewById(R.id.tvEmpty);
        rlPick = nearbyFragment.findViewById(R.id.rlPickLocation);
        locationProgress = nearbyFragment.findViewById(R.id.locationProgress);

        rlPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkLocation()) {

                    tvEmpty.setVisibility(View.VISIBLE);
                    return;
                }

                if(currentLocation.length()==0) {
                    tvEmpty.setVisibility(View.GONE);
                    locationProgress.setVisibility(View.VISIBLE);
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
                }else{
                    tvEmpty.setVisibility(View.GONE);
                    tvAddress.setText(currentLocation);
                    tvAddress.setVisibility(View.VISIBLE);
                }

            }
        });


        return nearbyFragment;
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();

            tvEmpty.setVisibility(View.GONE);

            getAddress();
            if(!btnProceed.isEnabled())
                btnProceed.setEnabled(true);

        }


        public void getAddress(){

            Address locationAddress;
            locationAddress=getAddressExact(latitudeGPS, longitudeGPS);

            if(locationAddress!=null)
            {

                String address = locationAddress.getAddressLine(0);
                String address1 = locationAddress.getAddressLine(1);
                String city = locationAddress.getLocality();
                String state = locationAddress.getAdminArea();
                String country = locationAddress.getCountryName();
                String postalCode = locationAddress.getPostalCode();

                if(!TextUtils.isEmpty(address))
                {
                    currentLocation=address;

                    if (!TextUtils.isEmpty(address1))
                        currentLocation+="\n"+address1;

                    if (!TextUtils.isEmpty(city))
                    {
                        currentLocation+="\n"+city;

                        if (!TextUtils.isEmpty(postalCode))
                            currentLocation+=" - "+postalCode;
                    }
                    else
                    {
                        if (!TextUtils.isEmpty(postalCode))
                            currentLocation+="\n"+postalCode;
                    }

                    if (!TextUtils.isEmpty(state))
                        currentLocation+="\n"+state;

                    if (!TextUtils.isEmpty(country))
                        currentLocation+="\n"+country;

                    locationProgress.setVisibility(View.GONE);
                    tvAddress.setText(currentLocation);
                    tvAddress.setVisibility(View.VISIBLE);

                    if(!btnProceed.isEnabled())
                        btnProceed.setEnabled(true);

                }

            }
            else
                showToast("Something went wrong");
        }


        public Address getAddressExact(double latitude, double longitude)
        {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                return addresses.get(0);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    public void showToast(String message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
