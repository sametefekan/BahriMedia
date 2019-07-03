package com.bahricorp.bahrimedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.models.BlogPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// old
// public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.SimpleViewHolder>
public class SimpleRVAdapter extends RecyclerView.ViewHolder
{
    public ArrayList<BlogPost> mExampleList;
    public Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    View mView;

    public static String imgUrl;
    public static String dscText;
    public static String uidText;
    public static String nameTxt;
    public static String emailTxt;
    public static String categoryTxt;

    private  SimpleRVAdapter.ClickListener mListener;

    public SimpleRVAdapter(View itemView)
    {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onItemClick(v, getAdapterPosition());
            }
        });

        /*
        itemView.setOnClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
        */
    }

    public interface ClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(SimpleRVAdapter.ClickListener clickListener)
    {
        mListener = clickListener;
    }

    public void setDetails(Context ctx, String blogTitle, String nameText, String emailText, String imageUrl, String descText, String userId, String price)
    {
        TextView textView = mView.findViewById(R.id.blog_title);
        textView.setText(blogTitle);

        TextView nameTextView = mView.findViewById(R.id.user_name);
        nameTextView.setText(nameText);

        TextView emailTextView = mView.findViewById(R.id.user_email);
        emailTextView.setText(emailText);

        TextView priceTextView = mView.findViewById(R.id.blog_price);
        priceTextView.setText(price);

        ImageView imageView = mView.findViewById(R.id.blog_image);
        Picasso.get().load(imageUrl).into(imageView);

        dscText = descText;
        imgUrl = imageUrl;
        uidText = userId;
    }

    public void setProduct(Context ctx, String blogTitle, String nameText, String emailText, String imageUrl, String descText, String userId, String price, String category)
    {
        TextView textView = mView.findViewById(R.id.blog_title);
        textView.setText(blogTitle);

        TextView priceTextView = mView.findViewById(R.id.blog_price);
        priceTextView.setText(price);

        ImageView imageView = mView.findViewById(R.id.blog_image);
        Picasso.get().load(imageUrl).into(imageView);

        dscText = descText;
        imgUrl = imageUrl;
        uidText = userId;

        nameTxt = nameText;
        emailTxt = emailText;
        categoryTxt = category;
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public TextView nameTextView;
        public TextView emailTextView;
        public ImageView imageView;

        public SimpleViewHolder(View itemView)
        {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.blog_title);
            nameTextView = (TextView) itemView.findViewById(R.id.user_name);
            emailTextView = (TextView) itemView.findViewById(R.id.user_email);
            imageView = (ImageView) itemView.findViewById(R.id.blog_image);
        }

        public void setTitleText(String blogTitle)
        {
            textView = itemView.findViewById(R.id.blog_title);
            textView.setText(blogTitle);
        }

        public void setNameText(String nameText)
        {
            nameTextView = itemView.findViewById(R.id.user_name);
            nameTextView.setText(nameText);
        }

        public void setEmailText(String emailText)
        {
            emailTextView = itemView.findViewById(R.id.user_email);
            emailTextView.setText(emailText);
        }

        public void setImage(String imageUrl)
        {
            imageView = itemView.findViewById(R.id.blog_image);
            Picasso.get().load(imageUrl).into(imageView);
        }
    }
}