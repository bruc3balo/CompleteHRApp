package com.example.hrapp.admin.applications;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.hrapp.models.Models.Applications.APPLICATION_DB;
import static com.example.hrapp.utils.IdGenerator.getAppId;
import static com.example.hrapp.utils.IdGenerator.time;

public class NewApplicationPosition extends AppCompatActivity {

    private final Models.Applications applications = new Models.Applications();
    private Button applicationButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_application_position);

        EditText titleField = findViewById(R.id.titleField), descriptionField = findViewById(R.id.descriptionField), responsibilitiesField = findViewById(R.id.responsibilitiesField), qualificationsField = findViewById(R.id.qualificationsField), jobFunctionField = findViewById(R.id.jobFunctionField), employmentType = findViewById(R.id.employmentType), professionField = findViewById(R.id.professionField);
        progressBar = findViewById(R.id.appPb);
        progressBar.setVisibility(View.GONE);
        applicationButton = findViewById(R.id.applicationButton);
        applicationButton.setOnClickListener(v -> {
            if (validateForm(titleField, descriptionField, responsibilitiesField, qualificationsField, jobFunctionField, employmentType, professionField)) {
                saveApp(applications);
            } else {
                Toast.makeText(NewApplicationPosition.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(EditText title, EditText des, EditText res, EditText qual, EditText job, EditText emp, EditText prof) {
        boolean valid = false;

        if (title.getText().toString().isEmpty()) {
            title.setError("Required");
            title.requestFocus();
        } else if (des.getText().toString().isEmpty()) {
            des.setError("Required");
            des.requestFocus();
        } else if (res.getText().toString().isEmpty()) {
            res.setError("Required");
            res.requestFocus();
        } else if (qual.getText().toString().isEmpty()) {
            qual.setError("Required");
            qual.requestFocus();
        } else if (job.getText().toString().isEmpty()) {
            job.setError("Required");
            job.requestFocus();
        } else if (emp.getText().toString().isEmpty()) {
            emp.setError("Required");
            emp.requestFocus();
        } else if (prof.getText().toString().isEmpty()) {
            prof.setError("Required");
            prof.requestFocus();
        } else {
            applications.setAppEmploymentType(emp.getText().toString());
            applications.setAppTitle(title.getText().toString());
            applications.setAppDescription(des.getText().toString());

            applications.setAppResponsibilities(res.getText().toString());
            applications.setAppQualifications(qual.getText().toString());
            applications.setAppJobFunction(job.getText().toString());

            applications.setAppPositionField(prof.getText().toString());
            applications.setCreatedAt(time);
            applications.setAppId(getAppId(title.getText().toString()));

            valid = true;
        }

        return valid;
    }

    private void saveApp(Models.Applications applications) {
        inProgress();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(APPLICATION_DB).document(applications.getAppId()).set(applications).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(NewApplicationPosition.this, "Application Posted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(NewApplicationPosition.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                outProgress();
            }
        });
    }

    private void inProgress() {
        progressBar.setVisibility(View.VISIBLE);
        applicationButton.setEnabled(false);
    }

    private void outProgress() {
        progressBar.setVisibility(View.GONE);
        applicationButton.setEnabled(true);
    }
}