package com.example.hrapp.employees.advance;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.hrapp.models.Models.Advances.ADVANCE_DB;

public class EmployeeAdvances extends AppCompatActivity {

    private int min, max;
    private final Models.Advances advances = new Models.Advances();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_advances);

        Toolbar advancesTb = findViewById(R.id.advancesTb);
        setSupportActionBar(advancesTb);

        advancesTb.setNavigationOnClickListener(v -> finish());

        EditText advanceReasonsField = findViewById(R.id.advanceReasonsField), minField = findViewById(R.id.minField), maxField = findViewById(R.id.maxField);

        RadioGroup categoryGroup = findViewById(R.id.categoryGroup);
        categoryGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                default:
                    break;
                case R.id.k5:
                    min = 0;
                    max = 5000;
                    break;
                case R.id.k15:
                    min = 5000;
                    max = 15000;
                    break;
                case R.id.k25:
                    min = 15000;
                    max = 25000;
                    break;
            }
        });

        Button applyButtonAdvances = findViewById(R.id.applyButtonAdvances);
        applyButtonAdvances.setOnClickListener(v -> {
            if (validateForm(advanceReasonsField,minField,maxField)) {
                postAdvance();
            } else {
                Toast.makeText(EmployeeAdvances.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateForm(EditText adv, EditText minField, EditText maxField) {
        boolean valid = false;
        if (Integer.parseInt(minField.getText().toString()) < min) {
            minField.setError("Too low");
            minField.requestFocus();
        } else if (Integer.parseInt(maxField.getText().toString()) > max) {
            maxField.setError("Too high");
            maxField.requestFocus();
        } else if (adv.getText().toString().isEmpty()) {
            adv.setError("Required");
            adv.requestFocus();
        } else {

            advances.setMax(maxField.getText().toString());
            advances.setMin(minField.getText().toString());
            advances.setReason(adv.getText().toString());
            advances.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

            valid = true;
        }
        return valid;
    }

    private void postAdvance () {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ADVANCE_DB).document(advances.getUid()).set(advances).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EmployeeAdvances.this, "Posted advance", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EmployeeAdvances.this, "Failed to post advance", Toast.LENGTH_SHORT).show();
            }
        });
    }
}