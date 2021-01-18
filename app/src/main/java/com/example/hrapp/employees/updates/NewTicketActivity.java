package com.example.hrapp.employees.updates;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hrapp.R;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.hrapp.models.Models.Tickets.HIGH_S;
import static com.example.hrapp.models.Models.Tickets.LOW_S;
import static com.example.hrapp.models.Models.Tickets.MID_S;
import static com.example.hrapp.models.Models.Tickets.TICKET_DB;
import static com.example.hrapp.models.Models.Users.EMPLOYEE_DB;
import static com.example.hrapp.models.Models.Users.FIRST_NAME;
import static com.example.hrapp.models.Models.Users.LAST_NAME;

public class NewTicketActivity extends AppCompatActivity {

    private String ticketSeverity = LOW_S;
    private final Models.Tickets tickets = new Models.Tickets();
    private String userName = "";
    private Button postTicketButton;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        getUserName();

        Toolbar newTicketTb = findViewById(R.id.newTicketTb);
        setSupportActionBar(newTicketTb);
        newTicketTb.setNavigationOnClickListener(v -> finish());

        RadioGroup ticketSeverityGroup = findViewById(R.id.ticketSeverityGroup);
        ticketSeverityGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                default:
                    break;

                case R.id.lowRadio:
                    ticketSeverity = LOW_S;
                    break;

                case R.id.midRadio:
                    ticketSeverity = MID_S;
                    break;

                case R.id.highRadio:
                    ticketSeverity = HIGH_S;
                    break;
            }
        });

        EditText contentField = findViewById(R.id.contentField);

        postTicketButton = findViewById(R.id.postTicketButton);
        postTicketButton.setOnClickListener(v -> makeTicket(contentField));
    }

    private void makeTicket(EditText content) {
        if (content.getText().toString().isEmpty()) {
            content.setError("Cannot be empty");
            content.requestFocus();
        } else {
            tickets.setTicketContent(content.getText().toString());
            tickets.setTicketSeverity(ticketSeverity);
            tickets.setTicketId(IdGenerator.getTicketId(FirebaseAuth.getInstance().getCurrentUser().getUid()));

            tickets.setSolved(false);
            tickets.setTicketComments(null);
            tickets.setDateSolvedAt(null);

            tickets.setDateCreatedAt(IdGenerator.time);
            tickets.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
            tickets.setUserName(userName);

            postTicket(tickets);
        }
    }

    private void getUserName() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(EMPLOYEE_DB).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String fName = task.getResult().get(FIRST_NAME).toString(), sName = task.getResult().get(LAST_NAME).toString();
                userName = fName.concat(" ").concat(sName);
            } else {
                userName = "Failed to get name";
            }
            Toast.makeText(NewTicketActivity.this, userName, Toast.LENGTH_SHORT).show();
        });
    }

    private void postTicket(Models.Tickets tickets1) {
        postTicketButton.setEnabled(false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TICKET_DB).document(tickets.getTicketId()).set(tickets1).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Ticket posted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to post ticket", Toast.LENGTH_SHORT).show();
            }
        });
    }
}