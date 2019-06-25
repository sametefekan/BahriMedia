package com.bahricorp.bahrimedia.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.models.BlogPost;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity
{
    public TextView textView;
    public TextView nameTextView;
    public TextView emailTextView;
    public TextView descTextView;
    public ImageView imageView;

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

        // image view
        imageView = findViewById(R.id.blog_image);

        // get data from fragment
        String mTitle = getIntent().getStringExtra("title");
        String mName = getIntent().getStringExtra("name");
        String mEmail = getIntent().getStringExtra("email");
        String mDesc = getIntent().getStringExtra("desc");

        String mImg = getIntent().getStringExtra("image");

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

        Picasso.get().load(mImg).into(imageView);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
