package com.example.hrapp.employees.appAvailabilities;

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
import com.example.hrapp.admin.applications.ApplicationViewModel;
import com.example.hrapp.employees.adapter.AppAvailabilitiesRvAdapter;
import com.example.hrapp.models.Models;

import java.util.Collections;
import java.util.LinkedList;

public class ApplicationAvailabilities extends AppCompatActivity {

    private final LinkedList<Models.Applications> appAvailabilitiesList = new LinkedList<>();
    private AppAvailabilitiesRvAdapter availabilitiesRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_availabilities);

        Toolbar availabilityTb = findViewById(R.id.availabilityTb);
        setSupportActionBar(availabilityTb);

        availabilityTb.setNavigationOnClickListener(v -> finish());



        RecyclerView applicationsRv = findViewById(R.id.applicationsRv);
        applicationsRv.setLayoutManager(new LinearLayoutManager(ApplicationAvailabilities.this, RecyclerView.VERTICAL, false));
         availabilitiesRvAdapter = new AppAvailabilitiesRvAdapter(ApplicationAvailabilities.this, appAvailabilitiesList);
        applicationsRv.setAdapter(availabilitiesRvAdapter);

        populateApplications();
    }

    private void populateApplications() {
        ApplicationViewModel applicationViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        applicationViewModel.applicationsLiveData().observe(this, applications -> {
            appAvailabilitiesList.addAll(Collections.singleton(applications));
            availabilitiesRvAdapter.notifyDataSetChanged();
        });
    }
}