package com.example.hrapp.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrapp.R;
import com.example.hrapp.admin.AdminPannel;
import com.example.hrapp.employees.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.hrapp.models.Models.Users.ADMIN;
import static com.example.hrapp.models.Models.Users.EMPLOYEE;
import static com.example.hrapp.models.Models.Users.EMPLOYEE_DB;
import static com.example.hrapp.models.Models.Users.ROLE;

public class LoginActivity extends AppCompatActivity {

    private boolean backPressed;
    private final String role_fail = "Failed to get role";
    private String role = "";
    private Button signInB;
    private ProgressBar loginPagePb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(android.R.color.darker_gray));
        }

        EditText email_field = findViewById(R.id.email_field);
        EditText pass_field = findViewById(R.id.pass_field);
        signInB = findViewById(R.id.signInB);
        loginPagePb = findViewById(R.id.loginPagePb);

        signInB.setOnClickListener(v1 -> {
            if (validateForm(email_field, pass_field)) {
                signInUser(email_field.getText().toString(), pass_field.getText().toString(), signInB);
            } else {
                Toast.makeText(LoginActivity.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.teal_200));
        }

        backPressed = false;
    }

    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> updateUi(firebaseAuth.getCurrentUser());

    private boolean validateForm(EditText email, EditText pass) {
        boolean valid = false;

        if (!email.getText().toString().contains("@")) {
            email.setError("Wrong email format");
            email.requestFocus();
        } else if (pass.getText().toString().length() < 6) {
            pass.setError("Password is short");
            pass.requestFocus();
        } else {
            valid = true;
        }

        return valid;
    }

    private void signInUser(String email, String pass, Button signInB) {
        signInB.setEnabled(false);
        loginPagePb.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                updateUi(user);
                Toast.makeText(LoginActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                signInB.setEnabled(true);
            }
        });
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {
            signInB.setEnabled(false);
            loginPagePb.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
            getRole(user.getUid());
        } else {
            signInB.setEnabled(true);
            loginPagePb.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Sign in to continue", Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseRole() {
        switch (role) {
            case role_fail:
                signInB.setEnabled(true);
                loginPagePb.setVisibility(View.GONE);
                Toast.makeText(this, "Failed to get role. Seek help", Toast.LENGTH_SHORT).show();
                break;
            case ADMIN:
                startActivity(new Intent(LoginActivity.this, AdminPannel.class));
                finish();
                break;
            case EMPLOYEE:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = false;
    }

    @Override
    public void onBackPressed() {
        if (!backPressed) {
            backPressed = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        super.onDestroy();
    }

    private void getRole(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(EMPLOYEE_DB).document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                role = task.getResult().get(ROLE).toString();
                chooseRole();
            } else {
                role = role_fail;
                chooseRole();
                Toast.makeText(LoginActivity.this, "Failed to get role", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

}