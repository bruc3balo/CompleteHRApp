package com.example.hrapp.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;


public class UpdatesRvAdapter extends RecyclerView.Adapter<UpdatesRvAdapter.ViewHolder> {

    private final LinkedList<Models.Updates> list;
    private ItemClickListener mClickListener;
    private Context mContext;

    public UpdatesRvAdapter(Context context, LinkedList<Models.Updates> list) {
        this.list = list;
        this.mContext = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updatePostedAt.setText(list.get(position).getUpdatePostedAt());
        holder.updateContent.setText(list.get(position).getUpdateContent());
        holder.updateTitle.setText(list.get(position).getUpdateTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView updateTitle, updateContent, updatePostedAt;

        ViewHolder(View itemView) {
            super(itemView);
            updateTitle = itemView.findViewById(R.id.updateTitle);
            updateContent = itemView.findViewById(R.id.updateContent);
            updatePostedAt = itemView.findViewById(R.id.updatePostedAt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

}