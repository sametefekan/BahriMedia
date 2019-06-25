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

// before
// public class SimpleRVAdapter extends RecyclerView.ViewHolder
public class SimpleRVAdapter extends RecyclerView.ViewHolder
{
    public ArrayList<BlogPost> mExampleList; //List<BlogPost>
    public Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    View mView;

    public static String imgUrl;
    public static String dscText;

    private  SimpleRVAdapter.ClickListener mListener;

    public SimpleRVAdapter(View itemView) // View itemView
    {
        super(itemView);
        mView = itemView;
        // mExampleList = exampleList;

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onItemClick(v, getAdapterPosition()); //View view, int position
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

    /*
    @Override // new
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override // new
    public void onBindViewHolder(SimpleViewHolder holder, int position)
    {
        BlogPost currentItem = mExampleList.get(position);
        String imageUrl = currentItem.getImage();
    }

    @Override // new
    public int getItemCount()
    {
        // size or lenght
        return mExampleList.size();
    }
    */

    public interface ClickListener
    {
        void onItemClick(View view, int position); //View view, int position
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(SimpleRVAdapter.ClickListener clickListener)
    {
        mListener = clickListener;
    }

    public void setDetails(Context ctx, String blogTitle, String nameText, String emailText, String imageUrl, String descText)
    {
        TextView textView = mView.findViewById(R.id.blog_title);
        textView.setText(blogTitle);

        TextView nameTextView = mView.findViewById(R.id.user_name);
        nameTextView.setText(nameText);

        TextView emailTextView = mView.findViewById(R.id.user_email);
        emailTextView.setText(emailText);

        ImageView imageView = mView.findViewById(R.id.blog_image);
        Picasso.get().load(imageUrl).into(imageView);

        dscText = descText;
        imgUrl = imageUrl;
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