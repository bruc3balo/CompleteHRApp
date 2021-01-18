package com.example.hrapp.employees.updates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.UpdatesRvAdapter;
import com.example.hrapp.admin.tickets.TicketsViewModel;
import com.example.hrapp.admin.updates.UpdatesViewModel;
import com.example.hrapp.employees.adapter.TicketRvAdapter;
import com.example.hrapp.models.Models;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.LinkedList;

public class UpdatesActivity extends AppCompatActivity {

    private final LinkedList<Models.Updates> updatesLinkedList = new LinkedList<>();
    private final LinkedList<Models.Tickets> ticketsLinkedList = new LinkedList<>();

    private TextView ticketsTv, updatesTv;

    private UpdatesRvAdapter updatesRvAdapter;
    private TicketRvAdapter ticketRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        Toolbar employeeTicketsTb = findViewById(R.id.employeeTicketsTb);
        employeeTicketsTb.setNavigationOnClickListener(v -> finish());

        RecyclerView updatesRv = findViewById(R.id.employeeUpdatesRv);
        updatesRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        updatesRvAdapter = new UpdatesRvAdapter(this, updatesLinkedList);
        updatesRv.setAdapter(updatesRvAdapter);

        RecyclerView ticketRv = findViewById(R.id.employeeticketsRv);
        ticketRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ticketRvAdapter = new TicketRvAdapter(this, ticketsLinkedList,getSupportFragmentManager());
        ticketRv.setAdapter(ticketRvAdapter);

        ticketsTv = findViewById(R.id.ticketsTv);
        updatesTv = findViewById(R.id.updatesTv);

        FloatingActionButton addTicketButton = findViewById(R.id.addTicketButton);
        addTicketButton.setOnClickListener(v -> startActivity(new Intent(UpdatesActivity.this, NewTicketActivity.class)));

        populateUpdates();
        populateTickets();
        updateLists();
    }

    @SuppressLint("SetTextI18n")
    private void updateLists() {
        updatesRvAdapter.notifyDataSetChanged();
        ticketRvAdapter.notifyDataSetChanged();

        if (ticketsLinkedList.size() == 0) {
            ticketsTv.setText(getString(R.string.no_tickets));
        } else {
            ticketsTv.setText(getString(R.string.tickets_available) + " (" + ticketsLinkedList.size() + ")");
        }

        if (updatesLinkedList.size() == 0) {
            updatesTv.setText(getString(R.string.no_updates));
        } else {
            updatesTv.setText(getString(R.string.updates_available) + " (" + updatesLinkedList.size() + ")");
        }
    }

    private void populateUpdates() {
        UpdatesViewModel updatesViewModel = new ViewModelProvider(this).get(UpdatesViewModel.class);
        updatesViewModel.getUpdates().observe(this, updates -> {
            updatesLinkedList.addAll(Collections.singleton(updates));
            updateLists();
        });
    }

    private void populateTickets() {
        TicketsViewModel ticketsViewModel = new ViewModelProvider(this).get(TicketsViewModel.class);
        ticketsViewModel.getTicketsList().observe(this, tickets -> {
            ticketsLinkedList.addAll(Collections.singleton(tickets));
            updateLists();
        });
    }
}