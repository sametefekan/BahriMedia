package com.bahricorp.stumarkt.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.stumarkt.R;
import com.bahricorp.stumarkt.ViewPagerAdapter;
import com.bahricorp.stumarkt.models.BlogPost;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailActivity extends AppCompatActivity
{
    public TextView textView, nameTextView, emailTextView, descTextView, priceTextView;

    public ImageView imageView;
    public CircleImageView profileImage;

    public ViewPager viewPager;
    public Button chatButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    BlogPost model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // Action bar
        ActionBar actionBar = getSupportActionBar();

        // Action bar title
        actionBar.setTitle("Post");

        // set back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Text Views find
        textView = findViewById(R.id.blog_title);
        descTextView = findViewById(R.id.blog_desc);
        nameTextView = findViewById(R.id.user_name);
        emailTextView = findViewById(R.id.user_email);
        priceTextView = findViewById(R.id.blog_price);
        chatButton = findViewById(R.id.button_chat);

        // profileImage = findViewById(R.id.profile_image);
        profileImage = findViewById(R.id.blog_user_image);

        // image view // save
        // imageView = findViewById(R.id.blog_image);

        viewPager = findViewById(R.id.imageSlider);

        // get data from fragment
        String mTitle = getIntent().getStringExtra("title");
        String mDesc = getIntent().getStringExtra("desc");

        String mName = getIntent().getStringExtra("name");
        String mEmail = getIntent().getStringExtra("email");
        final String uid = getIntent().getStringExtra("userId");

        // price string from intent
        String mPrice = getIntent().getStringExtra("price");

        // image addresses
        String mImg = getIntent().getStringExtra("image");
        String mImg2 = getIntent().getStringExtra("image2");
        String mImg3 = getIntent().getStringExtra("image3");

        // for user image
        String imageURL = getIntent().getStringExtra("imageURL");

        String[] strings = {mImg, mImg2, mImg3};

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, strings);
        viewPager.setAdapter(viewPagerAdapter);

        // get image
        /*
        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bmp);
        */

        textView.setText(mTitle);
        descTextView.setText(mDesc);
        nameTextView.setText(mName);
        emailTextView.setText(mEmail);
        priceTextView.setText(mPrice);

        // save
        // Picasso.get().load(mImg).into(imageView);

        // for user profile image
        Picasso.get().load(imageURL).into(profileImage);

        if(imageURL == null)
        {
            profileImage.setImageResource(R.drawable.ic_person_black_24dp);
        }

        chatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PostDetailActivity.this, ChatActivity.class);

                intent.putExtra("userId", uid);
                intent.putExtra("userName", nameTextView.getText().toString());
                intent.putExtra("userEmail", emailTextView.getText().toString());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
