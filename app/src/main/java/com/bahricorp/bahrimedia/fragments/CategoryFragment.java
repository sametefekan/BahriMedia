package com.bahricorp.bahrimedia.fragments;

// import android.app.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bahricorp.bahrimedia.Activity.MainActivity;
import com.bahricorp.bahrimedia.MyListener;
import com.bahricorp.bahrimedia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment
{
    private Bundle savedInstanceState;
    public String categoryText;

    String[] strings = {
                    "Cars",
                    "Real Estate",
                    "Electronic",
                    "House&Garden",
                    "Entertainment",
                    "Other Vehicles",
                    "Clothes&Accessories",
                    "Movie,Book&Music",
                    "Others"};

    private int[] imageArray = {
            R.drawable.ic_car,
            R.drawable.ic_house,
            R.drawable.ic_smartphone,
            R.drawable.ic_garden,
            R.drawable.ic_entertainment,
            R.drawable.ic_motorcycle,
            R.drawable.ic_clothes,
            R.drawable.ic_book,
            R.drawable.ic_others};

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
        public void onBindViewHolder(SimpleViewHolder holder, final int position)
        {
            holder.textView.setText(dataSource[position]);

            Drawable d = getResources().getDrawable(imageSource[position]);
            holder.image.setImageDrawable(d);

            // new for item click
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    HomeFragmentMain fragmentMain = new HomeFragmentMain();

                    /*
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    HomeFragmentMain secondFragment = new HomeFragmentMain();
                    secondFragment.setArguments(bundle);

                    fragmentTransaction.replace(R.id.main_frame, secondFragment);
                    fragmentTransaction.commit();
                    */

                    switch (position)
                    {
                        case 0:
                            categoryText = "cars";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            //sendData();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            //intent.putExtra(bundle);
                            // original
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 1:
                            categoryText = "real-estate";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 2:
                            categoryText = "electronic";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 3:
                            categoryText = "house&garden";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 4:
                            categoryText = "entertainment";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 5:
                            categoryText = "other vehicles";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 6:
                            categoryText = "clothes&accessories";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 7:
                            categoryText = "movie, book&music";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 8:
                            categoryText = "others";
                            Toast.makeText(getActivity().getApplicationContext(), categoryText, Toast.LENGTH_LONG).show();
                            bundle.putString("category", categoryText);
                            fragmentMain.setArguments(bundle);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                    }
                }
            });
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
        public String categoryText;

        public SimpleViewHolder(View itemView)
        {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.useremail);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
        }
    }

    public void sendData()
    {
        MyListener listener= (MyListener) getActivity();
        listener.getData(categoryText);
    }
}
