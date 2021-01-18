package com.example.hrapp.employees.timetable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.employees.adapter.TimetableRvAdapter;
import com.example.hrapp.models.Models;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;


import org.joda.time.DateTime;

import java.util.Collections;
import java.util.LinkedList;

import static com.example.hrapp.login.LoginActivity.truncate;

public class EmployeeTimetable extends AppCompatActivity implements DatePickerListener {

    String date = "";
    String today = ""; //dd/mmm//yyyy

    private final LinkedList<Models.TimeTableModel> timetableList = new LinkedList<>();
    private final LinkedList<Models.TimeTableModel> timetableFullList = new LinkedList<>();
    private TimetableRvAdapter timetableRvAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_timetable);

        Toolbar timeTableTb = findViewById(R.id.timeTableTb);
        setSupportActionBar(timeTableTb);

        timeTableTb.setNavigationOnClickListener(v -> finish());

        HorizontalPicker picker = findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker.setListener(this)
                .setDays(20)
                .setOffset(10)
                .setDateSelectedColor(Color.RED)
                .setDateSelectedTextColor(Color.WHITE)
                .setUnselectedDayTextColor(Color.BLACK)
                .setMonthAndYearTextColor(getColor(R.color.teal_700))
                .setTodayButtonTextColor(Color.RED)
                .setTodayDateTextColor(getColor(R.color.teal_200))
                .setTodayDateBackgroundColor(getColor(R.color.black))
                .setDayOfWeekTextColor(Color.WHITE)
                .setUnselectedDayTextColor(getColor(R.color.semiGray))
                .showTodayButton(true)
                .init();

        // or on the View directly after init was completed
        picker.setBackgroundColor(Color.WHITE);
        picker.setDate(new DateTime().plusDays(4));
        //  truncate();


        RecyclerView datesTimeTable = findViewById(R.id.datesTimeTable);
        datesTimeTable.setLayoutManager(new LinearLayoutManager(EmployeeTimetable.this, RecyclerView.VERTICAL, false));
         timetableRvAdapter = new TimetableRvAdapter(EmployeeTimetable.this, timetableList);
        datesTimeTable.setAdapter(timetableRvAdapter);

        populateList();

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Toast.makeText(this, "Selected date is \"" + dateSelected.toString(), Toast.LENGTH_SHORT).show();
        selectDate(String.valueOf(dateSelected.getDayOfMonth()));
    }

    private void selectDate(String date) {
        System.out.println("date ");
        timetableList.clear();
        for (int i = 0; i<= timetableFullList.size() - 1 ; i ++) {
            if (getDay(timetableFullList.get(i).getActivityDate()).equals(date)) {
                timetableList.add(timetableFullList.get(i));
                Toast.makeText(this, "Selected : " +date, Toast.LENGTH_SHORT).show();
                timetableRvAdapter.notifyDataSetChanged();
            } else {
                if (timetableList.contains(timetableFullList.get(i))) {
                    timetableFullList.remove(timetableFullList.get(i));
                    Toast.makeText(this, "Removed : " +date, Toast.LENGTH_SHORT).show();
                    timetableRvAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private String getDay(String date) {
        System.out.println("day is "+truncate(date,2));
        return truncate(date,2);
    }

    private void populateList() {
        TimetableViewModel timetableViewModel = new ViewModelProvider(this).get(TimetableViewModel.class);
        timetableViewModel.getActivities().observe(this, timeTableModel -> {
            timetableFullList.addAll(Collections.singleton(timeTableModel));
            timetableList.addAll(Collections.singleton(timeTableModel));
            timetableRvAdapter.notifyDataSetChanged();
        });
    }
}