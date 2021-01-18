package com.example.hrapp.admin.applications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.AppAvailabilitiesAdminRvAdapter;
import com.example.hrapp.employees.adapter.AppAvailabilitiesRvAdapter;
import com.example.hrapp.models.Models;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.LinkedList;

public class AdminApplications extends AppCompatActivity {

    private AppAvailabilitiesAdminRvAdapter appAvailabilitiesRvAdapter;
    private final LinkedList<Models.Applications> applicationList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_applications);

        Toolbar adminApplicationsTb = findViewById(R.id.adminApplicationsTb);
        setSupportActionBar(adminApplicationsTb);
        adminApplicationsTb.setNavigationOnClickListener(v -> finish());

        RecyclerView appRv = findViewById(R.id.appRv);
        appRv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        appAvailabilitiesRvAdapter = new AppAvailabilitiesAdminRvAdapter(this,applicationList);
        appRv.setAdapter(appAvailabilitiesRvAdapter);

        FloatingActionButton addApplicationButton = findViewById(R.id.addApplicationButton);
        addApplicationButton.setOnClickListener(v -> startActivity(new Intent(AdminApplications.this, NewApplicationPosition.class)));

        populateApplications();
    }

    private void populateApplications() {
        ApplicationViewModel applicationViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        applicationViewModel.applicationsLiveData().observe(this, new Observer<Models.Applications>() {
            @Override
            public void onChanged(Models.Applications applications) {
                applicationList.addAll(Collections.singleton(applications));
                appAvailabilitiesRvAdapter.notifyDataSetChanged();
            }
        });
    }
}