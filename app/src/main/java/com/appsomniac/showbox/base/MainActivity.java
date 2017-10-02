package com.appsomniac.showbox.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsomniac.showbox.R;
import com.appsomniac.showbox.activity.more.LoginActivity;
import com.appsomniac.showbox.fragments.MovieFragment;
import com.appsomniac.showbox.fragments.NearbyFragment;
import com.appsomniac.showbox.fragments.PersonalFragment;
import com.appsomniac.showbox.fragments.TvFragment;
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

//        if(photoUrl!=null && user_name!=null) {
//            nav = (NavigationView) findViewById(R.id.navigation);
//            navHeader = nav.inflateHeaderView(R.layout.header);
//        }

        nav = ( NavigationView ) findViewById( R.id.navigation );

        if( nav != null ){
            LinearLayout mParent = ( LinearLayout ) nav.getHeaderView( 0 );

            if( mParent != null ){
                // Set your values to the image and text view by declaring and setting as you need to here.

                SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                String photoUrl = prefs.getString("photo_url", null);
                String user_name = prefs.getString("name", "User");

                if(photoUrl!=null) {
                    Log.e("Photo Url: ", photoUrl);

                    TextView userName = mParent.findViewById(R.id.user_name);
                    userName.setText(user_name);

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


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        bottomBar = (BottomBar) findViewById(R.id.bottom_navigation);

        setUi();

    }

    private void setActionBar(){

        actionBar = (Toolbar) findViewById(R.id.actionBar);
        actionBar.setTitle(R.string.movies);
        setSupportActionBar(actionBar);
    }

    private void setDrawer(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, actionBar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setBottomNavigation(){

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId){
                    case R.id.movies:
                        actionBar.setTitle(R.string.movies);
                        setFragmentMovies();

                        break;
                    case R.id.tv_shows:
                        actionBar.setTitle(R.string.tv_shows);
                        setFragmentTv();

                        break;
                    case R.id.personal:
                        actionBar.setTitle(R.string.personal);
                        setPersonalFragment();
                        break;

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

    public void setPersonalFragment(){

        TAG = "Personal";
        for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
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

    public void setNearbyFragment(){

        TAG = "Nearby";
        for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
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

    public void setFragmentMovies(){

        TAG = "Movie";
        for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
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

    public void setFragmentTv(){

        TAG = "Tv";
        for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
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

    public void setUi(){

        setActionBar();
        setDrawer();
        setBottomNavigation();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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