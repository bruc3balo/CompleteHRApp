package com.example.hrapp.admin.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.hrapp.models.Models.TimeTableModel.TIMELINE_DB;

public class NewTimelineActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    private final Models.TimeTableModel timeTableModel = new Models.TimeTableModel();
    private Button saveTimelineButton;
    private ProgressBar newTimelinePB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_timeline);
        DatePicker newTimelineDatepicker = findViewById(R.id.newTimelineDatepicker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newTimelineDatepicker.setOnDateChangedListener(this);
        }
        EditText activityTitleField = findViewById(R.id.activityTitleField), activityTitleDescription = findViewById(R.id.activityTitleDescription);
        saveTimelineButton = findViewById(R.id.saveTimelineButton);
        newTimelinePB = findViewById(R.id.newTimelinePB);

        saveTimelineButton.setOnClickListener(v -> {
            if (validateForm(activityTitleField, activityTitleDescription)) {
                inProgress();
                saveToTimeline(timeTableModel);
            } else {
                Toast.makeText(NewTimelineActivity.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validateForm(EditText activityTitle, EditText activityDesc) {
        boolean valid = false;
        if (activityTitle.getText().toString().isEmpty()) {
            activityTitle.setError("Title needed");
            activityTitle.requestFocus();
        } else if (activityDesc.getText().toString().isEmpty()) {
            activityDesc.requestFocus();
            activityDesc.setError("Activity needed");
        } else if (timeTableModel.getActivityDate() == null) {
            Toast.makeText(this, "Pick a date", Toast.LENGTH_SHORT).show();
        } else {
            timeTableModel.setActivityTitle(activityTitle.getText().toString());
            timeTableModel.setActivityDescription(activityDesc.getText().toString());
            timeTableModel.setCreatedAt(IdGenerator.time);
            timeTableModel.setActivityId(IdGenerator.getTimelineId(activityTitle.getText().toString()));
            valid = true;
        }
        return valid;
    }

    private void saveToTimeline(Models.TimeTableModel timeTableModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TIMELINE_DB).document(timeTableModel.getActivityId()).set(timeTableModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(NewTimelineActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                ouProgress();
                Toast.makeText(NewTimelineActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String datePicked = dayOfMonth + "/" + ++monthOfYear + "/" + year;
        timeTableModel.setActivityDate(datePicked);
    }

    private void inProgress() {
        saveTimelineButton.setEnabled(false);
        newTimelinePB.setVisibility(View.VISIBLE);
    }

    private void ouProgress() {
        saveTimelineButton.setEnabled(true);
        newTimelinePB.setVisibility(View.GONE);
    }
}