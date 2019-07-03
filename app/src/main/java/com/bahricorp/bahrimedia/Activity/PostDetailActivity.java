package com.bahricorp.bahrimedia.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.ViewPagerAdapter;
import com.bahricorp.bahrimedia.models.BlogPost;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity
{
    public TextView textView, nameTextView, emailTextView, descTextView, priceTextView;

    public ImageView imageView;
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
        nameTextView = findViewById(R.id.user_name);
        emailTextView = findViewById(R.id.user_email);
        descTextView = findViewById(R.id.blog_desc);
        chatButton = findViewById(R.id.button_chat);
        priceTextView = findViewById(R.id.blog_price);

        // image view // save
        // imageView = findViewById(R.id.blog_image);

        viewPager = findViewById(R.id.imageSlider);

        // get data from fragment
        String mTitle = getIntent().getStringExtra("title");
        String mDesc = getIntent().getStringExtra("desc");
        String mPrice = getIntent().getStringExtra("price");

        String mName = getIntent().getStringExtra("name");
        String mEmail = getIntent().getStringExtra("email");
        final String uid = getIntent().getStringExtra("userId");

        String mImg = getIntent().getStringExtra("image");

        // new
        String mImg2 = getIntent().getStringExtra("image2");
        String mImg3 = getIntent().getStringExtra("image3");


        String[] strings = {mImg, mImg2, mImg3};

        // Image Slider View
        /*
        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this, strings);
        sliderView.setSliderAdapter(adapter);
        */

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, strings);
        viewPager.setAdapter(viewPagerAdapter);

        // get image
        /*
        byte[] bytes = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bmp);
        */

        textView.setText(mTitle);
        nameTextView.setText(mName);
        emailTextView.setText(mEmail);
        descTextView.setText(mDesc);
        priceTextView.setText(mPrice);

        // save
        // Picasso.get().load(mImg).into(imageView);

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
