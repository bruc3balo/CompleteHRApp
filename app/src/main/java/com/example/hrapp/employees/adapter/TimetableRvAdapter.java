package com.example.hrapp.employees.adapter;

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

import static com.example.hrapp.login.LoginActivity.truncate;

public class TimetableRvAdapter extends RecyclerView.Adapter<TimetableRvAdapter.ViewHolder> {

    private final LinkedList<Models.TimeTableModel> list;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public TimetableRvAdapter(Context context, LinkedList<Models.TimeTableModel> list) {
        this.list = list;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayText.setText(getDay(list.get(position).getActivityDate()));
        System.out.println(getMonth(list.get(position).getActivityDate()) + "month");
        holder.monthText.setText(getMonthName(Integer.parseInt(getMonth(list.get(position).getActivityDate()))));
        holder.yearText.setText(getYear(list.get(position).getActivityDate()));

        holder.titleActivity.setText(list.get(position).getActivityTitle());
        holder.postedAtTimeline.setText(list.get(position).getCreatedAt());
        holder.activityTimeline.setText(list.get(position).getActivityDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView postedAtTimeline, activityTimeline, titleActivity, yearText, monthText, dayText;

        ViewHolder(View itemView) {
            super(itemView);
            postedAtTimeline = itemView.findViewById(R.id.postedAtTimeline);
            activityTimeline = itemView.findViewById(R.id.activityTimeline);
            titleActivity = itemView.findViewById(R.id.titleActivity);
            yearText = itemView.findViewById(R.id.yearText);
            monthText = itemView.findViewById(R.id.monthText);
            dayText = itemView.findViewById(R.id.dayText);

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

    public static String getDay(String date) {
        System.out.println("day is "+truncate(date,2));
        return truncate(date,2);
    }

    public static String getMonth (String date) {
        String newDate = date.substring(3,5);
        System.out.println("newDate month "+newDate);
        if (newDate.contains("/")) {
            newDate = newDate.replace("/","");
        }
        System.out.println("newDate month "+newDate);
        return newDate;
    }

    public static String getMonthName(int month) {
        String monthName = "";

        switch (month) {

            case 1:
                monthName = "Jan";
                break;

            case 2:
                monthName = "Feb";

                break;

            case 3:
                monthName = "Mar";

                break;

            case 4:
                monthName = "Apr";

                break;

            case 5:
                monthName = "May";


                break;

            case 6:
                monthName = "Jun";

                break;

            case 7:
                monthName = "Jul";

                break;

            case 8:
                monthName = "Aug";

                break;

            case 9:
                monthName = "Sept";

                break;

            case 10:
                monthName = "Oct";

                break;

            case 11:
                monthName = "Nov";

                break;

            case 12:
                monthName = "Dec";

                break;

            default:
                break;
        }

        return monthName;
    }

    public static String getYear (String date) {
        return date.substring(5);
    }
}