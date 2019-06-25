package com.bahricorp.bahrimedia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahricorp.bahrimedia.Activity.ChatActivity;
import com.bahricorp.bahrimedia.Activity.UsersActivity;
import com.bahricorp.bahrimedia.models.UserModel;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> // RecyclerView.ViewHolder
{
    public List<UserModel> mUserModels;
    public LayoutInflater mInflater;
    public Context mContext;

    View mView;
    public static String imgUrl;

    // data is passed into the constructor
    public UserAdapter(Context mContext, List<UserModel> mUserModels) // View itemView
    {
        this.mContext = mContext;
        this.mUserModels = mUserModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final UserModel user = mUserModels.get(position);
        holder.email.setText(user.getEmail());

        /*
        if(user.getImageURL().equals("default"))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        }
        */

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userEmail", user.getEmail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mUserModels.size();
    }

    public void setDetails(Context ctx, String email)
    {
        TextView textView = mView.findViewById(R.id.useremail);
        textView.setText(email);

        /*
        ImageView imageView = mView.findViewById(R.id.profile_image);
        Picasso.get().load(imageUrl).into(imageView);

        imgUrl = imageUrl;
        */
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView email;

        public TextView id;
        public ImageView profile_image;

        ViewHolder(View itemView)
        {
            super(itemView);

            email = itemView.findViewById(R.id.useremail);
            profile_image = itemView.findViewById(R.id.profile_image);
        }

        public void setEmailText(String emailText)
        {
            email = itemView.findViewById(R.id.useremail);
            email.setText(emailText);
        }
    }
}