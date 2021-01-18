package com.example.hrapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.AdminPageGrid;
import com.example.hrapp.admin.advances.AdvancesActivity;
import com.example.hrapp.admin.applications.AdminApplications;
import com.example.hrapp.admin.kpi.AdminKPI;
import com.example.hrapp.admin.tickets.AdminTickets;
import com.example.hrapp.admin.timetable.TimetableActivity;
import com.example.hrapp.admin.updates.AdminUpdates;
import com.example.hrapp.admin.userManagement.UserManagement;
import com.example.hrapp.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AdminPannel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);

        Toolbar adminToolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(adminToolbar);
        adminToolbar.setSubtitle(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        GridView adminGrid = findViewById(R.id.adminGrid);
        AdminPageGrid adminPageGrid = new AdminPageGrid();
        adminGrid.setAdapter(adminPageGrid);
        adminGrid.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            default:
                break;
            case 0:
                startActivity(new Intent(AdminPannel.this, UserManagement.class));
                break;
            case 1:
                startActivity(new Intent(AdminPannel.this, AdminUpdates.class));
                break;

            case 2:
                startActivity(new Intent(AdminPannel.this, AdminTickets.class));
                break;

            case 3:
                startActivity(new Intent(AdminPannel.this, AdminKPI.class));
                break;

            case 4:
                startActivity(new Intent(AdminPannel.this, AdminApplications.class));
                break;

            case 5:
                startActivity(new Intent(AdminPannel.this, TimetableActivity.class));
                break;

            case 6:
                startActivity(new Intent(AdminPannel.this, AdvancesActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.admin_signout) {
            FirebaseAuth.getInstance().signOut();
            updateUi(FirebaseAuth.getInstance().getCurrentUser());
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUi(FirebaseUser currentUser) {
        if (currentUser != null) {
            System.out.println("Signed in");
            Toast.makeText(AdminPannel.this, "Signed in", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Signed out");
            Toast.makeText(AdminPannel.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminPannel.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}