package com.sourcebits.fitfind;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sourcebits.fitfind.com.sourcebits.fitfind.preference.UIConstants;
import com.sourcebits.fitfind.com.sourcebits.fitfind.preference.UIPreference;
import com.sourcebits.fitfind.custom.CircularImageClass;
import com.sourcebits.fitfind.fragments.FitnessClassFragment;
import com.sourcebits.fitfind.fragments.GoalsFragment;
import com.sourcebits.fitfind.fragments.HelpFragment;
import com.sourcebits.fitfind.fragments.HistoryFragment;
import com.sourcebits.fitfind.fragments.MessageFragment;
import com.sourcebits.fitfind.fragments.PaymentFragment;
import com.sourcebits.fitfind.fragments.PersonalTrainerFragment;
import com.sourcebits.fitfind.fragments.ProfileFragment;
import com.sourcebits.fitfind.fragments.SettingsFragment;
import com.sourcebits.fitfind.model.DummyData;
import com.sourcebits.fitfind.model.TrainerDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class HomeAcivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    private Toolbar mToolbar;
    NavigationView mNavigationView;

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

    //TODO- Change the highlighted color of navigational menu item
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(mNavigationView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = setupDrawerToggle();

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavigationView.getMenu().getItem(0).setChecked(true);
        //TODO - View is not visisble in profile.
        mPref = UIPreference.getSharedPref(this);

        String name = mPref.getString(UIConstants.USER_NAME, "");

        View headerLayout = mNavigationView.getHeaderView(0);
        TextView user_name = (TextView)headerLayout.findViewById(R.id.user_name);
        user_name.setText(name);

        TextView logout = (TextView)headerLayout.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        ImageView userImg = (ImageView)headerLayout.findViewById(R.id.user_img);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.download);
        Bitmap setImg = CircularImageClass.getRoundedShape(bmp, 100 , 100);
        if(setImg != null ) {
            userImg.setImageBitmap(setImg);
        }


    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    Fragment fragment = null;
    public void selectDrawerItem(MenuItem menuItem) {
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_profile_picture:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_personal_trainer:
                fragmentClass = PersonalTrainerFragment.class;
                break;
            case R.id.nav_fitness_class:
                fragmentClass = FitnessClassFragment.class;
                break;

            case R.id.nav_goals:
                fragmentClass = GoalsFragment.class;
                break;

            case R.id.nav_messages:
                fragmentClass = MessageFragment.class;
                break;

            case R.id.nav_history:
                fragmentClass = HistoryFragment.class;
                break;
            case R.id.nav_payments:
                fragmentClass = PaymentFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_help:
                fragmentClass = HelpFragment.class;
                break;

            default:
                fragmentClass = ProfileFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        menuItem.setChecked(true);
        invalidateOptionsMenu();
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(fragment instanceof PersonalTrainerFragment) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.personal_trainer_menu, menu);
            MenuItem searchItem = menu.findItem(R.id.menu_search);

            if (searchItem != null) {
                searchAction(searchItem);
            } return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void searchAction(MenuItem searchItem){
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //some operation
            }
        });
        EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchPlate.setHint("Search");
        View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                class searchResult implements Predicate<TrainerDetails> {

                    @Override
                    public boolean apply(TrainerDetails products) {
                        return (products.getName().contains(query)) ;
                    }
                }
                List<TrainerDetails> list = DummyData.getData();
                List<TrainerDetails> results = new ArrayList<>();

                Collection<TrainerDetails> searchRes = Collections2.filter(list, new searchResult());

                if(searchRes != null){
                    results.addAll(searchRes);
                    ((PersonalTrainerFragment) fragment).updateList(results);
                } else {
                    Toast.makeText(HomeAcivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }  else if (id == R.id.filter_trainer_action){
            Intent intent = new Intent(this,FilterActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.logout:
                LoginManager.getInstance().logOut();
                mPref.edit().putString(UIConstants.USER_NAME, "").putString(UIConstants.FB_ACCESS_TOKEN, "").commit();
                startActivity(new Intent(HomeAcivity.this, MainActivity.class));
                this.finish();
                break;
        }
    }
}
