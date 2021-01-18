package com.example.hrapp.employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.admin.AdminPannel;
import com.example.hrapp.employees.adapter.EmployeePageGrid;
import com.example.hrapp.employees.advance.EmployeeAdvances;
import com.example.hrapp.employees.appAvailabilities.ApplicationAvailabilities;
import com.example.hrapp.employees.leave.LeaveApplication;
import com.example.hrapp.employees.profile.EmployeeProfile;
import com.example.hrapp.employees.timetable.EmployeeTimetable;
import com.example.hrapp.employees.updates.UpdatesActivity;
import com.example.hrapp.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        mainToolbar.setSubtitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        GridView employeeGrid = findViewById(R.id.employeeGrid);
        EmployeePageGrid employeePageGrid = new EmployeePageGrid();
        employeeGrid.setAdapter(employeePageGrid);
        employeeGrid.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            default:
                break;
            case 0:
                startActivity(new Intent(MainActivity.this, EmployeeProfile.class));
                break;

            case 1:
                startActivity(new Intent(MainActivity.this, EmployeeAdvances.class));
                break;

            case 2:
                startActivity(new Intent(MainActivity.this, ApplicationAvailabilities.class));
                break;

            case 3:
                startActivity(new Intent(MainActivity.this, LeaveApplication.class));
                break;

            case 4:
                startActivity(new Intent(MainActivity.this, EmployeeTimetable.class));
                break;

            case 5:
                startActivity(new Intent(MainActivity.this, UpdatesActivity.class));
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.employee_signout) {
            FirebaseAuth.getInstance().signOut();
            updateUi(FirebaseAuth.getInstance().getCurrentUser());
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUi(FirebaseUser currentUser) {
        if (currentUser != null) {
            System.out.println("Signed in");
            Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Signed out");
            Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}