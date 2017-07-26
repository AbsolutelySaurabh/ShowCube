package com.example.absolutelysaurabh.showtime.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolutelysaurabh.showtime.Fragment.AboutUsFragment;
import com.example.absolutelysaurabh.showtime.Fragment.FavouritesFragment;
import com.example.absolutelysaurabh.showtime.Fragment.HomeFragment;
import com.example.absolutelysaurabh.showtime.Fragment.PopularMoviesFragment;
import com.example.absolutelysaurabh.showtime.Fragment.SettingsFragment;
import com.example.absolutelysaurabh.showtime.Fragment.TopRatedMovies;
import com.example.absolutelysaurabh.showtime.Fragment.UpcomingMoviesFragment;
import com.example.absolutelysaurabh.showtime.R;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
       UpcomingMoviesFragment.OnFragmentInteractionListener, TopRatedMovies.OnFragmentInteractionListener
        , FavouritesFragment.OnFragmentInteractionListener,PopularMoviesFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, AboutUsFragment.OnFragmentInteractionListener{

    public static String TAG = "Home";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_FAVOURITES = "favourites";
    private static final String TAG_POPULAR = "popular";
    private static final String TAG_TOP_RATED = "toprated";
    private static final String TAG_UPCOMING = "upcoming";
    private static final String TAG_ABOUT_US = "about_us";
    private static final String TAG_SETTINGS = "settings";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles = {"Now Playing","Popular Movies" ,"Top Rated","Upcoming Movies","Favourite Movies","About Us", "Settings"};

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }
     /*  * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {

//        txtName.setText("Showtimw");
//        txtWebsite.setText("www.androidhive.info");
        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {

            drawer.closeDrawers();
            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                // update the main content by replacing fragments
                for(int i=0;i<getFragmentManager().getBackStackEntryCount();i++){
                    //delete the fragments in the backstack
                    getSupportFragmentManager().popBackStack();
                }
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        //Closing drawer on item click
        drawer.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                // HomeFragment displays the "Now Playing" movies.
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // popular movies fragment
                PopularMoviesFragment popularMoviesFragment = new PopularMoviesFragment();
                return popularMoviesFragment;
            case 2:
                // Top Rated movies fragment
                TopRatedMovies topRatedMovies = new TopRatedMovies();
                return topRatedMovies;
            case 3:
                // Upcoming movies fragment
                UpcomingMoviesFragment moviesFragment = new UpcomingMoviesFragment();
                return moviesFragment;
            case 4:
                FavouritesFragment favouritesFragment = new FavouritesFragment();
                return favouritesFragment;
            case 5:
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                return aboutUsFragment;
            case 6:
                SettingsFragment settingsFragment  = new SettingsFragment();
                return settingsFragment;
                //by default it's HomeFragment
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        Log.e("navItemIndex: ", String.valueOf(navItemIndex));
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_popular_movies:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_POPULAR;
                        break;
                    case R.id.nav_top_rated:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TOP_RATED;
                        break;
                    case R.id.nav_upcoming:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_UPCOMING;
                        break;
                    case R.id.nav_favourites:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_FAVOURITES;
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ABOUT_US;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
