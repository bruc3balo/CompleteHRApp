package com.example.hrapp.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hrapp.R;
import com.example.hrapp.models.Models;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class KPIRvAdapter extends RecyclerView.Adapter<KPIRvAdapter.ViewHolder> {

    private LinkedList<Models.Users> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    public KPIRvAdapter(Context context, LinkedList<Models.Users> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.users_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(list.get(position).getFirst_name() + " " + list.get(position).getLast_name());
        holder.userEmail.setText(list.get(position).getEmail_address());
        holder.userPosition.setText(list.get(position).getPerformance_ratings());
        Glide.with(mContext).load(R.drawable.ic_user_).into(holder.userDp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView userDp;
        TextView userName, userEmail, userPosition;

        ViewHolder(View itemView) {
            super(itemView);
            userDp = itemView.findViewById(R.id.user_dp);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userPosition = itemView.findViewById(R.id.user_position);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}