package com.bahricorp.bahrimedia.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.Activity.PostDetailActivity;
import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.SimpleRVAdapter;
import com.bahricorp.bahrimedia.models.BlogPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeFragmentMain extends Fragment
{
    private RecyclerView recyclerView;
    private ArrayList<BlogPost> mExampleList; // List<BlogPost>

    private Button button;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private SimpleRVAdapter simpleRVAdapter;

    public String image;
    public String desc;

    public HomeFragmentMain() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mExampleList = new ArrayList<>();

        // Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("Post");

        // before SimpleRVAdapter
        // after SimpleRVAdapter.SimpleViewHolder
        FirebaseRecyclerAdapter<BlogPost, SimpleRVAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogPost, SimpleRVAdapter>(BlogPost.class, R.layout.card, SimpleRVAdapter.class, mDatabase)
        {
            @Override
            protected void populateViewHolder(SimpleRVAdapter rvAdapter, final BlogPost model, int position)
            {
                rvAdapter.setDetails(container.getContext(), model.getTitle(), model.getName(), model.getEmail(), model.getImage(), model.getDesc());

                rvAdapter.setOnClickListener(new SimpleRVAdapter.ClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        image = model.getImage();
                        desc = model.getDesc();

                        // views find
                        TextView textView = (TextView) view.findViewById(R.id.blog_title);
                        TextView nameTextView = (TextView) view.findViewById(R.id.user_name);
                        TextView emailTextView = (TextView) view.findViewById(R.id.user_email);
                        ImageView imageView = (ImageView) view.findViewById(R.id.blog_image);
                        TextView descTextView = (TextView) view.findViewById(R.id.blog_desc);

                        //get data from views
                        String mTitle = textView.getText().toString();
                        String mName = nameTextView.getText().toString();
                        String mEmail = emailTextView.getText().toString();

                        // get image
                        /*
                        Drawable mDrawable = imageView.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
                        */

                        // pass this data to new activity
                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);

                        /*
                        // image
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // mBitmap.compress((Bitmap.CompressFormat.PNG), 100, stream);
                        byte[] bytes = stream.toByteArray();

                        // put bitmap image as array of bytes
                        intent.putExtra("image", bytes);
                        */

                        intent.putExtra("title", mTitle); // put Title
                        intent.putExtra("name", mName); // put name
                        intent.putExtra("email", mEmail); // put email

                        intent.putExtra("image", image); // put Title
                        intent.putExtra("desc", desc); // put Title

                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position)
                    {

                    }
                });
            }

            // before SimpleRVAdapter
            // after SimpleRVAdapter.SimpleViewHolder
            public SimpleRVAdapter onCreateViewHolder(ViewGroup parent, int viewType) // ArrayList<BlogPost> exampleList
            {
                // before SimpleRVAdapter
                // after SimpleRVAdapter.SimpleViewHolder
                SimpleRVAdapter viewHolder = super.onCreateViewHolder(parent, viewType);

                viewHolder.setOnClickListener(new SimpleRVAdapter.ClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        image = SimpleRVAdapter.imgUrl;
                        desc = SimpleRVAdapter.dscText;

                        // views find
                        TextView textView = (TextView) view.findViewById(R.id.blog_title);
                        TextView nameTextView = (TextView) view.findViewById(R.id.user_name);
                        TextView emailTextView = (TextView) view.findViewById(R.id.user_email);
                        ImageView imageView = (ImageView) view.findViewById(R.id.blog_image);
                        TextView descTextView = (TextView) view.findViewById(R.id.blog_desc);

                        //get data from views
                        String mTitle = textView.getText().toString();
                        String mName = nameTextView.getText().toString();
                        String mEmail = emailTextView.getText().toString();

                        // get image
                        /*
                        Drawable mDrawable = imageView.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
                        */

                        // pass this data to new activity
                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);

                        /*
                        // image
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // mBitmap.compress((Bitmap.CompressFormat.PNG), 100, stream);
                        byte[] bytes = stream.toByteArray();

                        // put bitmap image as array of bytes
                        intent.putExtra("image", bytes);
                        */

                        intent.putExtra("title", mTitle); // put Title
                        intent.putExtra("name", mName); // put name
                        intent.putExtra("email", mEmail); // put email

                        intent.putExtra("image", image); // put Title
                        intent.putExtra("desc", desc); // put Title

                        startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position)
                    {

                    }
                });

                return viewHolder;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        return recyclerView;

        // Google>Android Studio Simple Recycler View Fragment
        // PhotoBlogApp Repository (examples)
    }
}
