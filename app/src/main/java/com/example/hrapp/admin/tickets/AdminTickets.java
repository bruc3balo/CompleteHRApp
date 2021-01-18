package com.example.hrapp.admin.tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrapp.R;
import com.example.hrapp.admin.adapter.AdminTicketRvAdapter;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static com.example.hrapp.models.Models.Tickets.DATE_SOLVED;
import static com.example.hrapp.models.Models.Tickets.HIGH_S;
import static com.example.hrapp.models.Models.Tickets.LOW_S;
import static com.example.hrapp.models.Models.Tickets.MID_S;
import static com.example.hrapp.models.Models.Tickets.SOLVED;
import static com.example.hrapp.models.Models.Tickets.TICKET_DB;

public class AdminTickets extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private LinkedList<Models.Tickets> ticketList = new LinkedList<>();
    private final LinkedList<Models.Tickets> fullTicketList = new LinkedList<>();
    private AdminTicketRvAdapter adminTicketRvAdapter;
    private final ArrayList<String> severityList = new ArrayList<>();
    private RecyclerView adminTicketsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tickets);

        Toolbar adminTicketsTb = findViewById(R.id.adminTicketsTb);
        setSupportActionBar(adminTicketsTb);
        adminTicketsTb.setNavigationOnClickListener(v -> finish());

        severityList.add(LOW_S);
        severityList.add(MID_S);
        severityList.add(HIGH_S);

        CheckBox low = findViewById(R.id.lowBox);
        low.setOnCheckedChangeListener(this);
        low.setVisibility(View.GONE);
        CheckBox mid = findViewById(R.id.midBox);
        mid.setOnCheckedChangeListener(this);
        mid.setVisibility(View.GONE);
        CheckBox high = findViewById(R.id.highBox);
        high.setOnCheckedChangeListener(this);
        high.setVisibility(View.GONE);

        adminTicketsRv = findViewById(R.id.adminTicketsRv);
        adminTicketsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adminTicketRvAdapter = new AdminTicketRvAdapter(this, fullTicketList, getSupportFragmentManager());
        adminTicketsRv.setAdapter(adminTicketRvAdapter);

        adminTicketRvAdapter.setClickListener((view, position) -> handleTicket(position));

        populateTickets();

    }


    private void handleTicket(int pos) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.handle_ticket_layout);
        TextView inf0Dialog = d.findViewById(R.id.inf0Dialog);
        Button yes = d.findViewById(R.id.yesButton), no = d.findViewById(R.id.noButton), cancel = d.findViewById(R.id.cancel_button);
        d.show();

        inf0Dialog.setText(ticketList.get(pos).getTicketContent());
        yes.setText("Solved");
        no.setText("Delete");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        no.setOnClickListener(v -> db.collection(TICKET_DB).document(ticketList.get(pos).getTicketId()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ticketList.remove(ticketList.get(pos));
                Toast.makeText(AdminTickets.this, "Ticked Deleted", Toast.LENGTH_SHORT).show();
                d.dismiss();
            } else {
                Toast.makeText(AdminTickets.this, "Ticket failed : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        }));

        yes.setOnClickListener(v -> db.collection(TICKET_DB).document(ticketList.get(pos).getTicketId()).update(DATE_SOLVED, IdGenerator.time, SOLVED, true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdminTickets.this, "Solved", Toast.LENGTH_SHORT).show();
                d.dismiss();
            } else {
                Toast.makeText(AdminTickets.this, "Failed to solve : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        }));

        cancel.setOnClickListener(v -> d.dismiss());
    }

    private void chooseCategory() {
        for (int i = 0; i <= fullTicketList.size() - 1; i++) {
            System.out.println("ticket size " + fullTicketList.size());
            for (int b = 0; b <= severityList.size() - 1; b++) {
                if (fullTicketList.get(i).getTicketSeverity().equals(severityList.get(b))) {
                    ticketList.add(fullTicketList.get(i));
                    System.out.println("ticket added " + fullTicketList.get(i).getTicketComments());
                } else {
                    if (ticketList.contains(fullTicketList.get(i))) {
                        ticketList.remove(fullTicketList.get(i));
                    }
                    System.out.println("ticket not added " + fullTicketList.get(i).getTicketComments());
                }
                updateList();
            }
        }
        System.out.println("Comments " + ticketList);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            default:
                break;

            case R.id.lowBox:
                if (isChecked) {
                    if (!severityList.contains(LOW_S)) {
                        severityList.add(LOW_S);
                        //chooseCategory();
                    }

                } else {
                    if (severityList.contains(LOW_S)) {
                        severityList.remove(LOW_S);
                       // chooseCategory();
                    }
                }
                Toast.makeText(this, severityList.toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.midBox:
                if (isChecked) {
                    if (!severityList.contains(MID_S)) {
                        severityList.add(MID_S);
                       // chooseCategory();
                    }
                } else {
                    if (severityList.contains(MID_S)) {
                        severityList.remove(MID_S);
                       // chooseCategory();
                    }
                }
                Toast.makeText(this, severityList.toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.highBox:
                if (isChecked) {
                    if (!severityList.contains(HIGH_S)) {
                        severityList.add(HIGH_S);
                      //  chooseCategory();
                    }
                } else {
                    if (severityList.contains(HIGH_S)) {
                        severityList.remove(HIGH_S);
                     //   chooseCategory();
                    }
                }
                Toast.makeText(this, severityList.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void populateTickets() {
        TicketsViewModel ticketsViewModel = new ViewModelProvider(this).get(TicketsViewModel.class);
        ticketsViewModel.getTicketsList().observe(this, tickets -> {
            fullTicketList.addAll(Collections.singleton(tickets));
            ticketList = fullTicketList;
            System.out.println("Comments " + ticketList.size());
            adminTicketRvAdapter.notifyDataSetChanged();
            //chooseCategory();
            updateList();
        });
    }

    private void updateList() {
        adminTicketsRv.setAdapter(adminTicketRvAdapter);
        adminTicketRvAdapter.notifyDataSetChanged();
    }
}