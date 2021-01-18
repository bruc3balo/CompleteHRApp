package com.example.hrapp.admin.applications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.AppRv;
import com.example.hrapp.models.Models;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

import static com.example.hrapp.models.Models.Applications.APPLICATION_DB;
import static com.example.hrapp.models.Models.Applications.APP_ID;
import static com.example.hrapp.models.Models.EmployeeApplication.JOB_APPLICATION_DB;

public class ViewJobApplications extends AppCompatActivity {

    private AppRv appRv;
    private String appId = "";
    private final LinkedList<Models.EmployeeApplication> applicationLinkedList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_applications);

        if (getIntent().getExtras() != null) {
            appId = getIntent().getExtras().get(APP_ID).toString();
        }

        Toolbar jobApplicationsTb = findViewById(R.id.jobApplicationsTb);
        setSupportActionBar(jobApplicationsTb);
        jobApplicationsTb.setNavigationOnClickListener(v -> finish());

        RecyclerView jobApplicationsRv = findViewById(R.id.jobApplicationsRv);
        jobApplicationsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        appRv = new AppRv(this, applicationLinkedList, appId);
        jobApplicationsRv.setAdapter(appRv);

        populateList();
    }

    private void populateList() {
        JobAppVM jobAppVM = new ViewModelProvider(this).get(JobAppVM.class);
        jobAppVM.getEmploymentApplications(appId).observe(this, employeeApplication -> {
            applicationLinkedList.addAll(Collections.singleton(employeeApplication));
            appRv.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_application) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(APPLICATION_DB).document(appId).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ViewJobApplications.this, "Delete application", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ViewJobApplications.this, "Failed to delete application", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}