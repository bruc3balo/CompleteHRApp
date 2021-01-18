package com.example.hrapp.admin.timetable;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.admin.updates.AdminUpdates;
import com.example.hrapp.employees.adapter.TimetableRvAdapter;
import com.example.hrapp.employees.timetable.TimetableViewModel;
import com.example.hrapp.models.Models;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.LinkedList;

import static com.example.hrapp.login.LoginActivity.truncate;
import static com.example.hrapp.models.Models.TimeTableModel.TIMELINE_DB;
import static com.example.hrapp.models.Models.Updates.UPDATE_DB;

public class TimetableActivity extends AppCompatActivity implements DatePickerListener {

    private final LinkedList<Models.TimeTableModel> timetableList = new LinkedList<>();
    private final LinkedList<Models.TimeTableModel> timetableFullList = new LinkedList<>();
    private TimetableRvAdapter timetableRvAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Toolbar timeTableTb = findViewById(R.id.adminTimeTableTb);
        setSupportActionBar(timeTableTb);

        timeTableTb.setNavigationOnClickListener(v -> finish());

        HorizontalPicker picker = findViewById(R.id.datePickerAdmin);

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
        picker.setDate(new DateTime());


        //  truncate();



        RecyclerView datesTimeTable = findViewById(R.id.datesTimeTableAdmin);
        datesTimeTable.setLayoutManager(new LinearLayoutManager(TimetableActivity.this, RecyclerView.VERTICAL, false));
         timetableRvAdapter = new TimetableRvAdapter(TimetableActivity.this, timetableList);
        datesTimeTable.setAdapter(timetableRvAdapter);
        timetableRvAdapter.setClickListener((view, position) -> showDelete(position));

        FloatingActionButton addDateButton = findViewById(R.id.addDateButton);
        addDateButton.setOnClickListener(v -> startActivity(new Intent(TimetableActivity.this, NewTimelineActivity.class)));

        populateList();
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Toast.makeText(this, "" + dateSelected.getDayOfMonth(), Toast.LENGTH_SHORT).show();
        selectDate(String.valueOf(dateSelected.getDayOfMonth()).trim());
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


    private void showDelete(int pos) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.update_delete);
        Button delete = d.findViewById(R.id.delete_update);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.show();
        delete.setOnClickListener(v -> {
            deleteUpdate(timetableFullList.get(pos).getActivityId());
            d.dismiss();
        });
    }

    private void deleteUpdate(String updateId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TIMELINE_DB).document(updateId).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(TimetableActivity.this, "Update deleted", Toast.LENGTH_SHORT).show();
                removeFromList(updateId);
            } else {
                Toast.makeText(TimetableActivity.this, "Failed to delete timeline", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromList(String updateId) {
        for (int i = 0; i <= timetableFullList.size() - 1; i++) {
            if (timetableFullList.get(i).getActivityId().equals(updateId)) {
                timetableFullList.remove(i);
                timetableRvAdapter.notifyDataSetChanged();
            }
        }
    }
}