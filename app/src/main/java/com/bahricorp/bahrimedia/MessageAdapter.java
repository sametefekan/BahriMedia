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
import com.bahricorp.bahrimedia.models.ChatModel;
import com.bahricorp.bahrimedia.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    View mView;
    public LayoutInflater mInflater;
    public List<ChatModel> mChatModel;
    public Context mContext;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public static String imgUrl;

    FirebaseUser firebaseUser;


    // data is passed into the constructor
    public MessageAdapter(Context mContext, List<ChatModel> mChatModel) // View itemView
    {
        this.mContext = mContext;
        this.mChatModel = mChatModel;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position)
    {
        ChatModel chat = mChatModel.get(position);
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount()
    {
        return mChatModel.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;

        ViewHolder(View itemView)
        {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mChatModel.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}