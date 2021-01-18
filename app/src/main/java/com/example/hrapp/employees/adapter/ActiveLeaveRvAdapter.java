package com.example.hrapp.employees.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import static com.example.hrapp.employees.adapter.TimetableRvAdapter.getDay;
import static com.example.hrapp.employees.adapter.TimetableRvAdapter.getMonth;
import static com.example.hrapp.employees.adapter.TimetableRvAdapter.getMonthName;
import static com.example.hrapp.employees.adapter.TimetableRvAdapter.getYear;

public class ActiveLeaveRvAdapter extends RecyclerView.Adapter<ActiveLeaveRvAdapter.ViewHolder> {

    private LinkedList<String> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    public ActiveLeaveRvAdapter(Context context, LinkedList<String> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.active_dates_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayText.setText(getDay(list.get(position)));
        System.out.println(getMonth(list.get(position)) + "month");
        holder.monthText.setText(getMonthName(Integer.parseInt(getMonth(list.get(position)))));
        holder.yearText.setText(getYear(list.get(position)));
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView yearText, monthText, dayText;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            yearText = itemView.findViewById(R.id.yearText);
            monthText = itemView.findViewById(R.id.monthText);
            dayText = itemView.findViewById(R.id.dayText);
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