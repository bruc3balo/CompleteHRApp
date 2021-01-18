package com.example.hrapp.employees.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hrapp.R;
import com.example.hrapp.admin.userManagement.UsersViewModel;
import com.example.hrapp.models.Models;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        Toolbar profileTb = findViewById(R.id.profileTb);
        setSupportActionBar(profileTb);
        profileTb.setNavigationOnClickListener(v -> finish());

        getMyData();
    }

    private void getMyData() {
        UsersViewModel usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getUsersData(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(this, new Observer<Models.Users>() {
            @Override
            public void onChanged(Models.Users users) {
                setUserData(users);
            }
        });
    }

    private void setUserData(Models.Users users) {


        TextView nameField = findViewById(R.id.profileNameTv);
        nameField.setText(users.getFirst_name().concat(" ").concat(users.getLast_name()));
        TextView emailField = findViewById(R.id.profileEmailTv);
        emailField.setText(users.getEmail_address());

        TextView phone_number_field = findViewById(R.id.phoneNumberTv);
        phone_number_field.setText(users.getPhone_number());


        TextView position_field = findViewById(R.id.profilePositionTv);
        position_field.setText(users.getPosition());
        TextView kra_number_field = findViewById(R.id.profileKraTv);
        kra_number_field.setText(users.getKraPin());
        TextView nssf_number_field = findViewById(R.id.profileNssfTv);
        nssf_number_field.setText(users.getNssf());

        TextView nationalId_number_field = findViewById(R.id.profileIdTv);
        nationalId_number_field.setText(users.getNationalId());
        TextView nhfi_number_field = findViewById(R.id.profileNhifTv);
        nhfi_number_field.setText(users.getNhif());
        TextView dialogDateJoinedTv = findViewById(R.id.dateJoinedTv);
        dialogDateJoinedTv.setText(users.getCreatedAt());

        TextView dialogDobTv = findViewById(R.id.dobTv);
        dialogDobTv.setText(users.getDate_of_birth());

    }
}