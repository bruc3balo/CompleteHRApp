package com.example.hrapp.employees.appAvailabilities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.hrapp.models.Models.Applications.APPLICATION_DB;
import static com.example.hrapp.models.Models.Applications.APP_ID;
import static com.example.hrapp.models.Models.Applications.USERS_APPLIED;
import static com.example.hrapp.models.Models.EmployeeApplication.JOB_APPLICATION_DB;
import static com.example.hrapp.models.Models.EmployeeApplication.PENDING;

public class JobApplicationActivity extends AppCompatActivity {

    private Button applyJobButton;
    private ProgressBar jobAppPb;
    private String appId = "";
    private final Models.EmployeeApplication employeeApplication = new Models.EmployeeApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_application);

        if (getIntent().getExtras() != null) {
            appId = getIntent().getExtras().get(APP_ID).toString();
            Toast.makeText(this, appId, Toast.LENGTH_SHORT).show();
        }

        Toolbar newJobApplicationTb = findViewById(R.id.newJobApplicationTb);
        setSupportActionBar(newJobApplicationTb);
        newJobApplicationTb.setNavigationOnClickListener(v -> finish());

        EditText coverLetterField = findViewById(R.id.coverLetterField), why_get_field = findViewById(R.id.why_get_field);
        applyJobButton = findViewById(R.id.applyJobButton);
        jobAppPb = findViewById(R.id.jobAppPb);
        applyJobButton.setOnClickListener(v -> {
            if (validateForm(coverLetterField,why_get_field)) {
                inProgress();
                sendApplication();
            } else {
                Toast.makeText(JobApplicationActivity.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendApplication () {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JOB_APPLICATION_DB).document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).set(employeeApplication).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(JobApplicationActivity.this, "Application posted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                outProgress();
                Toast.makeText(JobApplicationActivity.this, "Failed to post application", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(EditText cv, EditText why) {
        boolean valid = false;
        if (cv.getText().toString().isEmpty()) {
            cv.setError("Cover letter needed");
            cv.requestFocus();
        } else if (why.getText().toString().isEmpty()) {
            why.setError("Why do you want this job");
            why.requestFocus();
        } else {
            employeeApplication.setCoverLetter(cv.getText().toString());
            employeeApplication.setWhyText(why.getText().toString());
            employeeApplication.setCreatedAt(IdGenerator.time);
            employeeApplication.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
            employeeApplication.setStatus(PENDING);
            employeeApplication.setAppId(appId);
            employeeApplication.setEmail_address(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            valid = true;
        }
        return valid;
    }

    private void inProgress() {
        jobAppPb.setVisibility(View.VISIBLE);
        applyJobButton.setEnabled(false);
    }

    private void outProgress() {
        jobAppPb.setVisibility(View.GONE);
        applyJobButton.setEnabled(true);
    }
}