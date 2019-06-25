package com.bahricorp.bahrimedia.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity
{
    CircleImageView profileImage;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Action bar title
        ActionBar actionBar = getSupportActionBar();

        // set back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        String userid = intent.getStringExtra("userEmail");

        // Action bar title
        actionBar.setTitle("" + userid);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        /*
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                //username.setText();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        */
    }
}
