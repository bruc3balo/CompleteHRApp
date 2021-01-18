package com.example.hrapp.admin.updates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static com.example.hrapp.login.LoginActivity.truncate;
import static com.example.hrapp.models.Models.Updates.UPDATE_DB;
import static com.example.hrapp.utils.IdGenerator.getUpdateId;

public class NewUpdate extends AppCompatActivity {

    private final Models.Updates updateModel = new Models.Updates();
    public static String time = truncate(Calendar.getInstance().getTime().toString(), 16);
    private Button postUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_update);

        Toolbar newUpdateTb = findViewById(R.id.newUpdateTb);
        setSupportActionBar(newUpdateTb);
        newUpdateTb.setNavigationOnClickListener(v -> finish());

        EditText title = findViewById(R.id.titleField);
        EditText content = findViewById(R.id.contentField);
        postUpdateButton = findViewById(R.id.postUpdateButton);
        postUpdateButton.setOnClickListener(v -> {
            if (validateForm(title, content)) {
                posUpdate();
            } else {
                Toast.makeText(NewUpdate.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(EditText title, EditText content) {
        boolean valid = false;
        if (title.getText().toString().isEmpty()) {
            title.setError("Title required");
            title.requestFocus();
        } else if (content.getText().toString().isEmpty()) {
            content.setError("Content required");
            content.requestFocus();
        } else {
            updateModel.setUpdateTitle(title.getText().toString());
            updateModel.setUpdateContent(content.getText().toString());
            updateModel.setUpdateId(getUpdateId(title.getText().toString()));
            updateModel.setUpdatePostedAt(time);
            valid = true;
        }
        return valid;
    }

    private void posUpdate() {
        postUpdateButton.setEnabled(false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UPDATE_DB).document(updateModel.getUpdateId()).set(updateModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(NewUpdate.this, "Update posted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(NewUpdate.this, "Failed to post update", Toast.LENGTH_SHORT).show();
                postUpdateButton.setEnabled(true);
            }
        });
    }
}