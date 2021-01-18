package com.example.hrapp.admin.userManagement;

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
import com.example.hrapp.admin.adapter.UsersRvAdapter;
import com.example.hrapp.models.Models;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.LinkedList;

import static com.example.hrapp.admin.userManagement.NewUser.NEW_USER;
import static com.example.hrapp.models.Models.Users.UID;

public class UserManagement extends AppCompatActivity {

    private final LinkedList<Models.Users> userList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        Toolbar adminUserTb = findViewById(R.id.adminUserTb);
        setSupportActionBar(adminUserTb);
        adminUserTb.setNavigationOnClickListener(v -> finish());

        RecyclerView usersRv = findViewById(R.id.usersRv);
        usersRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        UsersRvAdapter usersRvAdapter = new UsersRvAdapter(this, userList);
        usersRv.setAdapter(usersRvAdapter);

        usersRvAdapter.setClickListener((view, position) -> startActivity(new Intent(UserManagement.this, ShowUserInfo.class).putExtra(UID, userList.get(position).getUid()).putExtra(NEW_USER, false)));

        FloatingActionButton addUserButton = findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(v -> startActivity(new Intent(UserManagement.this, NewUser.class)));

        populateList(usersRvAdapter);
    }

    private void populateList(UsersRvAdapter adapter) {
        UsersViewModel usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getUsersList().observe(this, users -> {
            userList.addAll(Collections.singleton(users));
            adapter.notifyDataSetChanged();
        });
    }

}