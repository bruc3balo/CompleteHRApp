package com.example.hrapp.admin.userManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.admin.AdminPannel;
import com.example.hrapp.admin.adapter.UsersRvAdapter;
import com.example.hrapp.login.LoginActivity;
import com.example.hrapp.models.Models;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.Objects;

import static com.example.hrapp.admin.userManagement.NewUser.NEW_USER;
import static com.example.hrapp.models.Models.Users.ADMIN;
import static com.example.hrapp.models.Models.Users.DOB;
import static com.example.hrapp.models.Models.Users.EMPLOYEE;
import static com.example.hrapp.models.Models.Users.EMPLOYEE_DB;
import static com.example.hrapp.models.Models.Users.KRA_PIN;
import static com.example.hrapp.models.Models.Users.NATIONAL_ID;
import static com.example.hrapp.models.Models.Users.NHIF;
import static com.example.hrapp.models.Models.Users.NSSF;
import static com.example.hrapp.models.Models.Users.PERFORMANCE_RATING;
import static com.example.hrapp.models.Models.Users.PHONE_NO;
import static com.example.hrapp.models.Models.Users.POSITION;
import static com.example.hrapp.models.Models.Users.ROLE;
import static com.example.hrapp.models.Models.Users.UID;
import static com.example.hrapp.models.Models.Users.WAGE;

public class ShowUserInfo extends AppCompatActivity {
    //todo edit data
    Models.Users oldModel = new Models.Users();

    private boolean newUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);

        newUser = Boolean.parseBoolean(getIntent().getExtras().get(NEW_USER).toString());

        Toolbar userDataTb = findViewById(R.id.userDataTb);
        setSupportActionBar(userDataTb);
        userDataTb.setNavigationOnClickListener(v -> signOut());

        if (!getIntent().getExtras().get(UID).toString().isEmpty()) {
            populateList(getIntent().getExtras().get(UID).toString());
        } else {
            Toast.makeText(this, "No UID", Toast.LENGTH_SHORT).show();
        }

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(v -> signOut());

        setEditButtons(doneButton);

    }

    private void setEditButtons(Button done) {
        ImageButton changeIdAdmin = findViewById(R.id.changeIdAdmin), changeKraAdmin = findViewById(R.id.changeKraAdmin), changeNssfAdmin = findViewById(R.id.changeNssfAdmin), changeNhifAdmin = findViewById(R.id.changeNhifAdmin), changePositionAdmin = findViewById(R.id.changePositionAdmin), changePhoneAdmin = findViewById(R.id.changePhoneAdmin), changeDobAdmin = findViewById(R.id.changeDobAdmin), changeRoleAdmin = findViewById(R.id.changeRoleAdmin), changePerformanceAdmin = findViewById(R.id.changePerformanceAdmin), changeWageAdmin = findViewById(R.id.changeWageAdmin);
        int vis;
        if (newUser) {
            vis = View.GONE;
            Toast.makeText(this, "Gone", Toast.LENGTH_SHORT).show();

        } else {
            vis = View.VISIBLE;
            Toast.makeText(this, "Visible", Toast.LENGTH_SHORT).show();

            done.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            done.setText("Delete");
            done.setOnClickListener(v -> {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(EMPLOYEE_DB).document(oldModel.getUid()).delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ShowUserInfo.this, "User deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ShowUserInfo.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                    }
                });
            });

        }
        changeIdAdmin.setVisibility(vis);
        changeKraAdmin.setVisibility(vis);
        changeNssfAdmin.setVisibility(vis);
        changeNhifAdmin.setVisibility(vis);
        changePositionAdmin.setVisibility(vis);
        changePhoneAdmin.setVisibility(vis);
        changeDobAdmin.setVisibility(vis);
        changeRoleAdmin.setVisibility(vis);
        changePerformanceAdmin.setVisibility(View.GONE);
        changeWageAdmin.setVisibility(vis);

        changeIdAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getNationalId(), NATIONAL_ID));
        changeKraAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getKraPin(), KRA_PIN));

        changeNssfAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getNssf(), NSSF));
        changeNhifAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getNhif(), NHIF));
        changePositionAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getPosition(), POSITION));

        changePhoneAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getPhone_number(), PHONE_NO));
        changeDobAdmin.setOnClickListener(v -> showDatePicker());
        changeRoleAdmin.setOnClickListener(v -> changeRole());

        //changePerformanceAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getPerformance_ratings(), PERFORMANCE_RATING));
        changeWageAdmin.setOnClickListener(v -> changeValuesDialog(oldModel.getWage(), WAGE));

    }

    private void changeValuesDialog(String val, String key) {
        Dialog d = new Dialog(ShowUserInfo.this);
        d.setContentView(R.layout.change_value);
        EditText valueF = d.findViewById(R.id.valueLayout);
        Button save = d.findViewById(R.id.changeButton), cancel = d.findViewById(R.id.cancel_dialog_button);

        d.show();

        valueF.setText(val);
        cancel.setOnClickListener(v -> d.dismiss());
        save.setOnClickListener(v -> {
            Toast.makeText(ShowUserInfo.this, "Saving", Toast.LENGTH_SHORT).show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(EMPLOYEE_DB).document(oldModel.getUid()).update(key, valueF.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ShowUserInfo.this, "updated", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                } else {
                    Toast.makeText(ShowUserInfo.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void showDatePicker() {
        Toast.makeText(this, "Pick your date of birth", Toast.LENGTH_SHORT).show();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ShowUserInfo.this, (view, year, month, dayOfMonth) -> {
            String datePicked = dayOfMonth + "/" + ++month + "/" + year;
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(EMPLOYEE_DB).document(oldModel.getUid()).update(DOB, datePicked).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ShowUserInfo.this, "Date Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowUserInfo.this, "Failed to change date", Toast.LENGTH_SHORT).show();
                }
            });
            Snackbar.make(findViewById(android.R.id.content), datePicked, Snackbar.LENGTH_LONG).show();
        }, 2008, 1, 1);
        datePickerDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    private void changeRole() {
        Dialog d = new Dialog(ShowUserInfo.this);
        d.setContentView(R.layout.change_role);
        d.show();
        final String[] newROle = {""};
        RadioGroup roleGroup = d.findViewById(R.id.roleGroup);
        RadioButton adminRadio = d.findViewById(R.id.adminRadio);
        RadioButton employeeRadio = d.findViewById(R.id.employeeRadio);
        if (oldModel.getRole().equals(ADMIN)) {
            adminRadio.setChecked(true);
            newROle[0] = ADMIN;
        } else {
            employeeRadio.setChecked(true);
            newROle[0] = EMPLOYEE;
        }
        roleGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                default:
                    break;

                case R.id.adminRadio:
                    newROle[0] = ADMIN;
                    Toast.makeText(this, newROle[0], Toast.LENGTH_SHORT).show();
                    break;

                case R.id.employeeRadio:
                    newROle[0] = EMPLOYEE;
                    Toast.makeText(this, newROle[0], Toast.LENGTH_SHORT).show();

                    break;
            }
        });
        Button cancel = d.findViewById(R.id.cancel_role_button);
        cancel.setOnClickListener(v -> d.dismiss());
        Button changeRoleButton = d.findViewById(R.id.changeRoleButton);
        changeRoleButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(EMPLOYEE_DB).document(oldModel.getUid()).update(ROLE, newROle[0]).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ShowUserInfo.this, "role updated", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                } else {
                    Toast.makeText(ShowUserInfo.this, "Failed to update role", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void signOut() {
        if (newUser) {
            Toast.makeText(this, "You will be signed out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            updateUi(FirebaseAuth.getInstance().getCurrentUser());
        } else {
            finish();
        }
    }

    private void updateUi(FirebaseUser currentUser) {
        if (currentUser != null) {
            System.out.println("Signed in");
            Toast.makeText(ShowUserInfo.this, "Signed in", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Signed out");
            Toast.makeText(ShowUserInfo.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ShowUserInfo.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void updateUser() {
        Toast.makeText(this, "Do later", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void populateList(String uid) {
        UsersViewModel usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getUsersData(uid).observe(this, this::setUserData);
    }

    private void setUserData(Models.Users users) {

        oldModel = users;


        TextView nameField = findViewById(R.id.dialogNameTv);
        nameField.setText(users.getFirst_name().concat(" ").concat(users.getLast_name()));
        TextView emailField = findViewById(R.id.dialogEmailTv);
        emailField.setText(users.getEmail_address());

        TextView phone_number_field = findViewById(R.id.dialogNumberTv);
        phone_number_field.setText(users.getPhone_number());
        TextView password_field = findViewById(R.id.dialog_secret_key);
        password_field.setText(users.getSecret_key());
        TextView dialogRoleTv = findViewById(R.id.dialogRoleTv);
        dialogRoleTv.setText(users.getRole());

        TextView position_field = findViewById(R.id.dialogPositionTv);
        position_field.setText(users.getPosition());
        TextView kra_number_field = findViewById(R.id.dialogKraTv);
        kra_number_field.setText(users.getKraPin());
        TextView nssf_number_field = findViewById(R.id.dialogNssfTv);
        nssf_number_field.setText(users.getNssf());

        TextView nationalId_number_field = findViewById(R.id.dialogIdTv);
        nationalId_number_field.setText(users.getNationalId());
        TextView nhfi_number_field = findViewById(R.id.dialogNhifTv);
        nhfi_number_field.setText(users.getNhif());
        TextView dialogDateJoinedTv = findViewById(R.id.dialogDateJoinedTv);
        dialogDateJoinedTv.setText(users.getCreatedAt());

        TextView dialogDobTv = findViewById(R.id.dialogDobTv);
        dialogDobTv.setText(users.getDate_of_birth());
        TextView performance = findViewById(R.id.userPerformanceTv);
        performance.setText(users.getPerformance_ratings());
        TextView wage = findViewById(R.id.wageTv);
        wage.setText(users.getWage());
    }
}