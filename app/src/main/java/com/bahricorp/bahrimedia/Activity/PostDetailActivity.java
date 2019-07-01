package com.bahricorp.bahrimedia.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    public TextView priceTextView;
    public ImageView imageView;
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

        // image view
        imageView = findViewById(R.id.blog_image);

        // get data from fragment
        String mTitle = getIntent().getStringExtra("title");
        String mName = getIntent().getStringExtra("name");
        String mEmail = getIntent().getStringExtra("email");
        String mDesc = getIntent().getStringExtra("desc");
        final String uid = getIntent().getStringExtra("userId");

        String mImg = getIntent().getStringExtra("image");
        String mPrice = getIntent().getStringExtra("price");

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

        Picasso.get().load(mImg).into(imageView);

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
