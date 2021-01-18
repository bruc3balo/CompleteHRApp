package com.example.hrapp.admin.userManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.employees.MainActivity;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.hrapp.models.Models.Users.EMPLOYEE;
import static com.example.hrapp.models.Models.Users.EMPLOYEE_DB;
import static com.example.hrapp.models.Models.Users.UID;


public class NewUser extends AppCompatActivity {

    private final Models.Users userModel = new Models.Users();
    private final String empty = "";
    private String dateOfBirth = "";
    public static final String NEW_USER = "New User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        EditText fNameField = findViewById(R.id.first_name_field);
        EditText sNameField = findViewById(R.id.second_name_field);
        EditText emailField = findViewById(R.id.email_address_field);

        EditText phone_number_field = findViewById(R.id.phone_number_field);
        EditText password_field = findViewById(R.id.password_field);
        EditText confirm_password_field = findViewById(R.id.confirm_password_field);

        EditText position_field = findViewById(R.id.position_field);
        EditText kra_number_field = findViewById(R.id.kra_number_field);
        EditText nssf_number_field = findViewById(R.id.nssf_number_field);

        EditText nationalId_number_field = findViewById(R.id.nationalId_number_field);
        EditText nhfi_number_field = findViewById(R.id.nhfi_number_field);
        DatePicker dobPicker = findViewById(R.id.dobPicker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dobPicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                dateOfBirth = dayOfMonth + "//" + monthOfYear + "//" + year;
                Toast.makeText(NewUser.this, dateOfBirth, Toast.LENGTH_SHORT).show();
                TextView dobField = findViewById(R.id.dobField);
                dobField.setText(dateOfBirth);
            });
        }

        Button createButton = findViewById(R.id.createAccountButton);
        createButton.setOnClickListener(v1 -> {
            if (validateForm(fNameField, sNameField, emailField, phone_number_field, nationalId_number_field, password_field, confirm_password_field)) {

                if (!position_field.getText().toString().isEmpty()) {
                    userModel.setPosition(position_field.getText().toString());
                } else {
                    userModel.setPosition(empty);
                }
                if (!kra_number_field.getText().toString().isEmpty()) {
                    userModel.setKraPin(kra_number_field.getText().toString());
                } else {
                    userModel.setKraPin(empty);
                }
                if (!nssf_number_field.getText().toString().isEmpty()) {
                    userModel.setNssf(nssf_number_field.getText().toString());
                } else {
                    userModel.setNssf(empty);
                }
                if (!nhfi_number_field.getText().toString().isEmpty()) {
                    userModel.setNhif(nhfi_number_field.getText().toString());
                } else {
                    userModel.setNhif(empty);
                }
                userModel.setRole(EMPLOYEE);

                createUserWithFireBase(emailField.getText().toString(), confirm_password_field.getText().toString(), createButton);

            } else {
                Toast.makeText(NewUser.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(EditText fName, EditText sName, EditText email, EditText phone, EditText nationalId_number_field, EditText password, EditText confirmPassword) {

        boolean valid = false;

        if (fName.getText().toString().isEmpty()) {
            fName.setError("Required");
            fName.requestFocus();
        } else if (sName.getText().toString().isEmpty()) {
            sName.setError("Required");
            sName.requestFocus();
        } else if (!email.getText().toString().contains("@")) {
            email.setError("Wrong format");
            email.requestFocus();
        } else if (phone.getText().toString().length() < 10) {
            phone.setError("Too short");
            phone.requestFocus();
        } else if (nationalId_number_field.getText().toString().isEmpty()) {
            nationalId_number_field.setError("Required");
            nationalId_number_field.requestFocus();
        } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
            confirmPassword.setError("Passwords don't match");
            confirmPassword.requestFocus();
        } else if (confirmPassword.getText().toString().length() < 6) {
            confirmPassword.setError("Password is less than 6");
            confirmPassword.requestFocus();
        } else {
            userModel.setEmail_address(email.getText().toString());
            userModel.setFirst_name(fName.getText().toString());
            userModel.setLast_name(sName.getText().toString());

            userModel.setCreatedAt(IdGenerator.time);
            userModel.setPhone_number(phone.getText().toString());
            userModel.setNationalId(nationalId_number_field.getText().toString());

            userModel.setSecret_key(confirmPassword.getText().toString());
            userModel.setDate_of_birth(dateOfBirth);
            valid = true;
        }
        return valid;
    }

    private void createUserWithFireBase(String email, String password, Button create) {
        create.setEnabled(false);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                userModel.setUid(user.getUid());
                new saveDetailsAsyncTask().execute(userModel);
                Toast.makeText(this, "Created account with " + user.getEmail(), Toast.LENGTH_SHORT).show();
                userCreated(userModel);
            } else {
                create.setEnabled(true);
                failedToCreateUser(task.getException().toString());
                Toast.makeText(this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userCreated(Models.Users users) {
        startActivity(new Intent(NewUser.this, ShowUserInfo.class).putExtra(UID, users.getUid()).putExtra(NEW_USER,true));
        finish();
    }

    private void failedToCreateUser(String s) {
        Snackbar.make(findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG).show();
    }

    public static class saveDetailsAsyncTask extends AsyncTask<Models.Users, Void, Void> {

        public saveDetailsAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            System.out.println("Saving info");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Models.Users... users) {
            saveUserDetails(users[0]);
            return null;
        }

        private void saveUserDetails(Models.Users employee) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(EMPLOYEE_DB).document(employee.getUid()).set(employee).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    System.out.println("Details saved");
                } else {
                    System.out.println("Details failed to save");
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println("Saved info");
            super.onPostExecute(aVoid);
        }
    }
}