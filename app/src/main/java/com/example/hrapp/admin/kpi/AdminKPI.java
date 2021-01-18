package com.example.hrapp.admin.kpi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.KPIRvAdapter;
import com.example.hrapp.admin.adapter.UsersRvAdapter;
import com.example.hrapp.admin.userManagement.NewUser;
import com.example.hrapp.admin.userManagement.ShowUserInfo;
import com.example.hrapp.admin.userManagement.UserManagement;
import com.example.hrapp.admin.userManagement.UsersViewModel;
import com.example.hrapp.models.Models;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

import static com.example.hrapp.admin.userManagement.NewUser.NEW_USER;
import static com.example.hrapp.models.Models.Users.EMPLOYEE_DB;
import static com.example.hrapp.models.Models.Users.PERFORMANCE_RATING;
import static com.example.hrapp.models.Models.Users.UID;

public class AdminKPI extends AppCompatActivity {

    private final LinkedList<Models.Users> userList = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_k_p_i);

        Toolbar kpiTb = findViewById(R.id.kpiTb);
        setSupportActionBar(kpiTb);
        kpiTb.setNavigationOnClickListener(v -> finish());

        RecyclerView usersRv = findViewById(R.id.usersRv);
        usersRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        KPIRvAdapter usersRvAdapter = new KPIRvAdapter(this, userList);
        usersRv.setAdapter(usersRvAdapter);

        usersRvAdapter.setClickListener((view, position) ->changeValuesDialog(userList.get(position).getPerformance_ratings(),userList.get(position).getUid()) );


        populateList(usersRvAdapter);
    }

    private void populateList(KPIRvAdapter adapter) {
        UsersViewModel usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getUsersList().observe(this, users -> {
            userList.addAll(Collections.singleton(users));
            adapter.notifyDataSetChanged();
        });
    }



    private void changeValuesDialog(String val, String key) {
        Dialog d = new Dialog(AdminKPI.this);
        d.setContentView(R.layout.change_value);
        EditText valueF = d.findViewById(R.id.valueLayout);
        Button save = d.findViewById(R.id.changeButton), cancel = d.findViewById(R.id.cancel_dialog_button);

        d.show();

        valueF.setText(val);
        cancel.setOnClickListener(v -> d.dismiss());
        save.setOnClickListener(v -> {
            Toast.makeText(AdminKPI.this, "Saving", Toast.LENGTH_SHORT).show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(EMPLOYEE_DB).document(key).update(PERFORMANCE_RATING, valueF.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AdminKPI.this, "Kpi's Updated", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                } else {
                    Toast.makeText(AdminKPI.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}