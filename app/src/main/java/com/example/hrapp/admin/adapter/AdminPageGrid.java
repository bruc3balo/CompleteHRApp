package com.example.hrapp.admin.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hrapp.R;

public class AdminPageGrid extends BaseAdapter {

    private final String[] mainPageGridTitle = new String[]{"Users", "Updates", "Tickets", "KPI's", "Applications", "Timetable","Advances"};
    private final int[] mainPageIcons = new int[]{R.drawable.ic_user_, R.drawable.ic_update, R.drawable.ic_tickets, R.drawable.ic_report_, R.drawable.ic_application, R.drawable.ic_schedule,R.drawable.ic_forward};

    public AdminPageGrid() {
    }

    @Override
    public int getCount() {
        return mainPageGridTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return mainPageGridTitle[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, null);
        }

        TextView title = convertView.findViewById(R.id.title_row);
        title.setText(mainPageGridTitle[position]);
        ImageView icon = convertView.findViewById(R.id.icon_row);
        Glide.with(parent.getContext()).load(mainPageIcons[position]).into(icon);

        return convertView;
    }
}
