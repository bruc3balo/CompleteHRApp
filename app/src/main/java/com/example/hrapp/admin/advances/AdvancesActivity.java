package com.example.hrapp.admin.advances;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.AdvancesRvAdapter;
import com.example.hrapp.models.Models;

import java.util.Collections;
import java.util.LinkedList;

public class AdvancesActivity extends AppCompatActivity {

    private AdvancesRvAdapter advancesRvAdapter;
    private final LinkedList<Models.Advances> userList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advances);

        RecyclerView admin_advances_rv = findViewById(R.id.admin_advances_rv);
        admin_advances_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        advancesRvAdapter = new AdvancesRvAdapter(this, userList);
        admin_advances_rv.setAdapter(advancesRvAdapter);
        //  advancesRvAdapter.setClickListener((view, position) -> startActivity(new Intent(AdvancesActivity.this, AdvanceViewActivity.class)));

        populateList();
    }

    private void populateList() {
        AdvancesViewModel advancesViewModel = new ViewModelProvider(this).get(AdvancesViewModel.class);
        advancesViewModel.getAdvances().observe(this, advances -> {
            userList.addAll(Collections.singleton(advances));
            advancesRvAdapter.notifyDataSetChanged();
        });
    }
}