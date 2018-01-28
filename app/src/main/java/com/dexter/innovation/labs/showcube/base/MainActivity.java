package com.dexter.innovation.labs.showcube.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dexter.innovation.labs.showcube.R;
import com.dexter.innovation.labs.showcube.activity.activity.login.LoginActivity;
import com.dexter.innovation.labs.showcube.fragments.MovieFragment;
import com.dexter.innovation.labs.showcube.fragments.NearbyFragment;
import com.dexter.innovation.labs.showcube.fragments.PersonalFragment;
import com.dexter.innovation.labs.showcube.fragments.TvFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MovieFragment.OnFragmentInteractionListener,
        TvFragment.OnFragmentInteractionListener,
        NearbyFragment.OnFragmentInteractionListener ,
        PersonalFragment.OnFragmentInteractionListener {

    protected Toolbar actionBar;
    protected DrawerLayout drawerLayout;
    public static String TAG = "Movie";
    BottomBar bottomBar;
    public static final String ANONYMOUS = "anonymous";

    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;
    public static String currentLocation;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    View navHeader;
    NavigationView nav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        nav = (NavigationView) findViewById(R.id.navigation);
        if (nav != null) {
            LinearLayout mParent = (LinearLayout) nav.getHeaderView(0);

            if (mParent != null) {
                // Set your values to the image and text view by declaring and setting as you need to here.

                SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                String photoUrl = prefs.getString("photo_url", null);
                String user_name = prefs.getString("name", "User");
                String user_email = prefs.getString("email", "showcube03@gmail.com");

                if (photoUrl != null) {
                    Log.e("Photo Url: ", photoUrl);

                    TextView userName = mParent.findViewById(R.id.user_name);
                    userName.setText(user_name);

                    TextView text_view_user_email = mParent.findViewById(R.id.header_user_email);
                    text_view_user_email.setText(user_email);

                    ImageView user_imageView = mParent.findViewById(R.id.avatar);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_user_24dp);
                    requestOptions.error(R.drawable.ic_user_24dp);

                    Glide.with(this).load(photoUrl)
                            .apply(requestOptions).thumbnail(0.5f).into(user_imageView);

                }
            }
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
        }


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        bottomBar = (BottomBar) findViewById(R.id.bottom_navigation);
        setUi();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_drawer_info){

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.legal_info_dialog);
            dialog.show();
            Button close_button = (Button) dialog.findViewById(R.id.info_close);
            close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
        }else
            if(id == R.id.nav_drawer_share){

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey,\n Look at the showcube app on google playstore.");
                startActivity(Intent.createChooser(intent, "Share with"));

            }else
                if(id==R.id.nav_drawer_logout){

                    mFirebaseAuth.signOut();
                    //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    mUsername = ANONYMOUS;
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
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

    private void setActionBar() {

        actionBar = (Toolbar) findViewById(R.id.actionBar);
        actionBar.setTitle(R.string.movies);
        setSupportActionBar(actionBar);
    }

    private void setDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, actionBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setBottomNavigation() {

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.movies:
                        actionBar.setTitle(R.string.movies);
                        setFragmentMovies();

                        break;
                    case R.id.tv_shows:
                        actionBar.setTitle(R.string.tv_shows);
                        setFragmentTv();

                        break;
//                    case R.id.personal:
//                        actionBar.setTitle(R.string.personal);
//                        setPersonalFragment();
//                        break;

                    case R.id.tab_nearby:
                        actionBar.setTitle(R.string.nearby);
                        setNearbyFragment();
                        break;
                    default:

                        actionBar.setTitle(R.string.movies);
                        setFragmentMovies();
                }
            }
        });


    }

    public void setPersonalFragment() {

        TAG = "Personal";
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
        Fragment fragment = new PersonalFragment();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void setNearbyFragment() {

        TAG = "Nearby";
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
        Fragment fragment = new NearbyFragment();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void setFragmentMovies() {

        TAG = "Movie";
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
        Fragment fragment = new MovieFragment();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void setFragmentTv() {

        TAG = "Tv";
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
        Fragment fragment = new TvFragment();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, TAG);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void setUi() {

        setActionBar();
        setDrawer();
        setBottomNavigation();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

