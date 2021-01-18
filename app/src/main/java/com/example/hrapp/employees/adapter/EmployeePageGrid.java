package com.example.hrapp.employees.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hrapp.R;

public class EmployeePageGrid extends BaseAdapter {

    private final String[] mainPageGridTitle = new String[]{"Profile", "Advances", "Applications", "Leave", "Timetable", "Updates"};
    private final int[] mainPageIcons = new int[]{R.drawable.ic_user_, R.drawable.ic_forward, R.drawable.ic_application,R.drawable.ic_relax, R.drawable.ic_schedule, R.drawable.ic_update};

    public EmployeePageGrid() {
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
