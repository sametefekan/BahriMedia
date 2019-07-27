package com.bahricorp.stumarkt.Activity;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

// import android.app.Fragment;
// import android.app.FragmentTransaction;
// import android.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bahricorp.stumarkt.MyListener;
import com.bahricorp.stumarkt.R;
import com.bahricorp.stumarkt.fragments.CategoryFragment;
import com.bahricorp.stumarkt.fragments.HomeFragmentMain;
import com.bahricorp.stumarkt.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements MyListener
{
    private FrameLayout mMainFrame;
    private HomeFragmentMain homeFragmentMain;
    private CategoryFragment categoryFragment;
    private ProfileFragment profileFragment;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action bar title
        ActionBar actionBar = getSupportActionBar();
        // Action bar title
        actionBar.setTitle("Home");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMainFrame = (FrameLayout) findViewById(R.id.home);
        postButton = (Button) findViewById(R.id.buttonPost);

        homeFragmentMain = new HomeFragmentMain();
        categoryFragment = new CategoryFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragmentMain, "home_fragment");
    }

    @Override
    public void getData(String data)
    {
        // FragmentManager manager = getSupportFragmentManager();
        // FragmentManager manager = getFragmentManager();
        // .findFragmentByTag("home_fragment")

        //HomeFragmentMain fragmentB = (HomeFragmentMain) getFragmentManager().findFragmentByTag("home_fragment");
        HomeFragmentMain fragmentB = (HomeFragmentMain) getSupportFragmentManager().findFragmentById(R.id.main_frame);
        //.findFragmentById(R.id.main_frame)

        fragmentB.getData(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.chat_button)
        {
            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            // new 14.06.19
            // Action bar
            ActionBar actionBar = getSupportActionBar();

            switch(item.getItemId())
            {
                case R.id.navigation_home:
                    setFragment(homeFragmentMain, "home_fragment");

                    // post button on
                    postButton.setVisibility(View.VISIBLE);

                    // Action bar title
                    actionBar.setTitle("Home");
                    return true;

                case R.id.navigation_category:
                    setFragment(categoryFragment, "category_fragment");

                    // post button off
                    postButton.setVisibility(View.INVISIBLE);

                    // Action bar title
                    actionBar.setTitle("Kategorien");
                    return true;

                case R.id.navigation_profile:
                    setFragment(profileFragment, "profile_fragment");

                    // post button off
                    postButton.setVisibility(View.INVISIBLE);

                    // Action bar title
                    actionBar.setTitle("Profil");
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment, String tag)
    {
        // old
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // new
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment, tag);
        fragmentTransaction.commit();

        // new
        /*
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
        */

        // get current Fragment
        // HomeFragmentMain myFragment = (HomeFragmentMain) getFragmentManager().findFragmentByTag("MY_FRAGMENT");
    }
}