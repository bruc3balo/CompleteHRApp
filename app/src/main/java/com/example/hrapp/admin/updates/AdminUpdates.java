package com.example.hrapp.admin.updates;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.UpdatesRvAdapter;
import com.example.hrapp.models.Models;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.LinkedList;

import static com.example.hrapp.models.Models.Updates.UPDATE_DB;

public class AdminUpdates extends AppCompatActivity {

    private final LinkedList<Models.Updates> updatesLinkedList = new LinkedList<>();
    private UpdatesRvAdapter updatesRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_updates);

        Toolbar adminUpdatesTb = findViewById(R.id.adminUpdatesTb);
        setSupportActionBar(adminUpdatesTb);
        adminUpdatesTb.setNavigationOnClickListener(v -> finish());

        RecyclerView adminUpdatesRv = findViewById(R.id.adminUpdatesRv);
        adminUpdatesRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        updatesRvAdapter = new UpdatesRvAdapter(this, updatesLinkedList);
        adminUpdatesRv.setAdapter(updatesRvAdapter);

        updatesRvAdapter.setClickListener((view, position) -> showDelete(position));

        populateList();

        FloatingActionButton addUpdateButton = findViewById(R.id.addUpdateButton);
        addUpdateButton.setOnClickListener(v -> startActivity(new Intent(AdminUpdates.this, NewUpdate.class)));
    }

    private void showDelete(int pos) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.update_delete);
        Button delete = d.findViewById(R.id.delete_update);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.show();
        delete.setOnClickListener(v -> {
            deleteUpdate(updatesLinkedList.get(pos).getUpdateId());
            d.dismiss();
        });
    }

    private void deleteUpdate(String updateId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UPDATE_DB).document(updateId).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdminUpdates.this, "Update deleted", Toast.LENGTH_SHORT).show();
                removeFromList(updateId);
            } else {
                Toast.makeText(AdminUpdates.this, "Failed to delete update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromList(String updateId) {
        for (int i = 0; i <= updatesLinkedList.size() - 1; i++) {
            if (updatesLinkedList.get(i).getUpdateId().equals(updateId)) {
                updatesLinkedList.remove(i);
                updatesRvAdapter.notifyDataSetChanged();
            }
        }
    }

    private void populateList() {
        UpdatesViewModel updatesViewModel = new ViewModelProvider(this).get(UpdatesViewModel.class);
        updatesViewModel.getUpdates().observe(this, updates -> {
            updatesLinkedList.addAll(Collections.singleton(updates));
            updatesRvAdapter.notifyDataSetChanged();
            Toast.makeText(this, updates.getUpdateTitle(), Toast.LENGTH_SHORT).show();
        });
    }
}