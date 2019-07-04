package com.bahricorp.bahrimedia.Activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.SimpleRVAdapter;
import com.bahricorp.bahrimedia.UserAdapter;
import com.bahricorp.bahrimedia.models.ChatModel;
import com.bahricorp.bahrimedia.models.UserModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;

    private ArrayList<UserModel> mUsers;
    private List<String> usersList;

    // new
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    public String image;

    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // Action bar
        ActionBar actionBar = getSupportActionBar();
        // Action bar title
        actionBar.setTitle("Chat");

        // set back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mUsers = new ArrayList<>();
        usersList = new ArrayList<>();

        // Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this, mUsers);
        recyclerView.setAdapter(userAdapter);

        // old
        readUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("Users");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    // old
    private void readUser()
    {
        // old one
        // final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats"); // old one "Users"
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // old for users activity
                mUsers.clear();

                // new for chats activity
                usersList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    /* it's for users activity
                    UserModel user = snapshot.getValue(UserModel.class);

                    assert user != null;
                    assert firebaseUser != null;

                    if(!user.getEmail().equals(firebaseUser.getUid()))
                    {
                        mUsers.add(user);
                    }
                    */

                    // start
                    // it's for you have messages chat activity
                    ChatModel chat = snapshot.getValue(ChatModel.class);

                    if(chat.getSender().equals((fuser.getUid())))
                    {
                        usersList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(fuser.getUid()))
                    {
                        usersList.add(chat.getSender());
                    }
                }

                // new
                readChats();

                /* // it's for users activity
                userAdapter = new UserAdapter(getBaseContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    // it's new for chats activity
    private void readChats()
    {
        mUsers = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mUsers.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserModel user = snapshot.getValue(UserModel.class);

                    for(String id : usersList)
                    {
                        if(user.getId().equals(id))
                        {
                            if (mUsers.size() != 0)
                            {
                                for(UserModel user1 : mUsers)
                                {
                                    if(!user.getId().equals(user1.getId()))
                                    {
                                        mUsers.add(user);
                                    }
                                }
                            }
                            else
                            {
                                mUsers.add(user);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(getBaseContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
