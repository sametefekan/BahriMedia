package com.bahricorp.stumarkt.fragments;

// import android.app.Fragment;

import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.stumarkt.R;
import com.bahricorp.stumarkt.Activity.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    private Bundle savedInstanceState;

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button menuButton;
    private ImageView profilePhoto;

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) view.findViewById(R.id.textViewEmail);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout01);
        menuButton = (Button) view.findViewById(R.id.buttonHome);
        profilePhoto = (ImageView) view.findViewById(R.id.profilePhoto);

        assert user != null;

        textViewUserEmail.setText("Hey  " + user.getEmail());

        profilePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Sign Out
                firebaseAuth.signOut();

                // Start Activity
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
