package com.example.hrapp.admin.adapter;

import android.annotation.SuppressLint;
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

public class AdvancesRvAdapter extends RecyclerView.Adapter<AdvancesRvAdapter.ViewHolder> {

    private LinkedList<Models.Advances> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    public AdvancesRvAdapter(Context context, LinkedList<Models.Advances> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.advances_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.maxAdv.setText("Max pay : "+list.get(position).getMax());
        holder.minAdv.setText("Min pay : "+ list.get(position).getMin());
        holder.reasonAdv.setText("Reasons : "+list.get(position).getReason());
       holder.userIdAdv.setText("User Id : "+list.get(position).getUid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView maxAdv, minAdv, reasonAdv,userIdAdv;

        ViewHolder(View itemView) {
            super(itemView);
            maxAdv = itemView.findViewById(R.id.maxAdv);
            minAdv = itemView.findViewById(R.id.minAdv);
            reasonAdv = itemView.findViewById(R.id.reasonAdv);
            userIdAdv = itemView.findViewById(R.id.userIdAdv);

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