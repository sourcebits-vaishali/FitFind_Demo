package com.sourcebits.fitfind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.sourcebits.fitfind.com.sourcebits.fitfind.preference.UIConstants;
import com.sourcebits.fitfind.com.sourcebits.fitfind.preference.UIPreference;
import com.sourcebits.fitfind.fragments.FitnessClassFragment;
import com.sourcebits.fitfind.fragments.GoalsFragment;
import com.sourcebits.fitfind.fragments.HelpFragment;
import com.sourcebits.fitfind.fragments.HistoryFragment;
import com.sourcebits.fitfind.fragments.MessageFragment;
import com.sourcebits.fitfind.fragments.PaymentFragment;
import com.sourcebits.fitfind.fragments.PersonalTrainerFragment;
import com.sourcebits.fitfind.fragments.ProfileFragment;
import com.sourcebits.fitfind.fragments.SettingsFragment;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class HomeAcivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    private Toolbar mToolbar;
    NavigationView mNavigationView;

    private static final int REQUEST_CODE_FILTER = 101;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(mNavigationView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);

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
            menu.add(0, R.id.filter_trainer_action, 0,R.string.filter_trainer);
            return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }  else if (item.getItemId() == R.id.filter_trainer_action){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FILTER && resultCode == RESULT_OK){
/*
            ArrayList<TrainerDetails> filelist =  (ArrayList<TrainerDetails>)getIntent().getSerializableExtra("mylist");
            Toast.makeText(HomeAcivity.this, "Size == "+filelist.size(), Toast.LENGTH_SHORT).show();
            PersonalTrainerFragment.updateList(filelist);*/
        }
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
