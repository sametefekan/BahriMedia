package com.bahricorp.bahrimedia.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.Activity.UsersActivity;
import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.SimpleRVAdapter;
import com.bahricorp.bahrimedia.UserAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment
{
    private Bundle savedInstanceState;

    String[] strings = {
                    "Cars",
                    "Real Estate",
                    "Electronic",
                    "House&Garden",
                    "Entertainment",
                    "Other Vehicles",
                    "Clothes&Accessories",
                    "Movie, Book&Music",
                    "Others", ""};

    private int[] imageArray = {
            R.drawable.ic_directions_car_black_24dp,
            R.drawable.ic_home_purple_24dp,
            R.drawable.ic_phone_iphone_green_24dp,
            R.drawable.ic_directions_car_black_24dp,
            R.drawable.ic_home_purple_24dp,
            R.drawable.ic_phone_iphone_green_24dp,
            R.drawable.ic_directions_car_black_24dp,
            R.drawable.ic_home_purple_24dp,
            R.drawable.ic_phone_iphone_green_24dp,
            R.mipmap.ic_launcher};

    public RecyclerView recyclerView;

    public CategoryFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new categoryAdapter(strings, imageArray));

        /*
        Button button = (Button) view.findViewById(R.id.chat_btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), UsersActivity.class);
                startActivity(intent);

                // do something
            }
        });
        */

        return view;
    }

    public class categoryAdapter extends RecyclerView.Adapter<SimpleViewHolder>
    {
        private String[] dataSource;
        private int[] imageSource;

        public categoryAdapter(String[] dataArgs, int[] images)
        {
            dataSource = dataArgs;
            imageSource = images;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position)
        {
            holder.textView.setText(dataSource[position]);

            // old
            // holder.image.setImageResource(imageSource[position]);

            Drawable d = getResources().getDrawable(imageSource[position]);
            holder.image.setImageDrawable(d);
        }

        @Override
        public int getItemCount()
        {
            return dataSource.length;
        }
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public ImageView image;

        public SimpleViewHolder(View itemView)
        {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.useremail);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}
