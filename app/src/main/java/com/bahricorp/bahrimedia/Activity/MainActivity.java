package com.bahricorp.bahrimedia.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.fragments.CategoryFragment;
import com.bahricorp.bahrimedia.fragments.HomeFragmentMain;
import com.bahricorp.bahrimedia.fragments.ProfileFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
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

        postButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent postIntent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivity(postIntent);
            }
        });

        // new 12.06.19
        setFragment(homeFragmentMain);
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
                    setFragment(homeFragmentMain);

                    // post button on
                    postButton.setVisibility(View.VISIBLE);

                    // Action bar title
                    actionBar.setTitle("Home");
                    return true;

                case R.id.navigation_category:
                    setFragment(categoryFragment);

                    // post button off
                    postButton.setVisibility(View.INVISIBLE);

                    // Action bar title
                    actionBar.setTitle("Category");
                    return true;

                case R.id.navigation_profile:
                    setFragment(profileFragment);

                    // post button off
                    postButton.setVisibility(View.INVISIBLE);

                    // Action bar title
                    actionBar.setTitle("Profile");
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment, "MY_FRAGMENT");
        fragmentTransaction.commit();

        // get current Fragment
        // HomeFragmentMain myFragment = (HomeFragmentMain) getFragmentManager().findFragmentByTag("MY_FRAGMENT");
    }
}