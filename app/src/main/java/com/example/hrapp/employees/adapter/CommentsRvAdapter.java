package com.example.hrapp.employees.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class CommentsRvAdapter extends RecyclerView.Adapter<CommentsRvAdapter.ViewHolder> {

    public static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;
    private ItemClickListener mClickListener;
    private final Context mContext;
    private final LinkedList<Models.Comments> chatList;
    private final String myUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private boolean fullSize;


    public CommentsRvAdapter(Context context, LinkedList<Models.Comments> chatList) {
        this.chatList = chatList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == TYPE_LEFT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right_item, parent, false);
        }
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        fullSize = false;
        holder.message.setText(chatList.get(position).getCommentContent());
        holder.timeStamp.setText(chatList.get(position).getCommentedAt());

        holder.message.setOnClickListener(v -> {
            fullSize = !fullSize;

            if (fullSize) {
                holder.timeStamp.setVisibility(View.GONE);
            } else {
                holder.timeStamp.setVisibility(View.VISIBLE);
            }
        });
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {


        if (chatList.get(position).getUid().equals(myUserId)) {
            return TYPE_RIGHT;
        } else {
            return TYPE_LEFT;
        }

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView message;
        TextView timeStamp;

        ViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageContent);
            timeStamp = itemView.findViewById(R.id.timeStamp);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}