package com.example.hrapp.employees.leave;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrapp.R;
import com.example.hrapp.employees.adapter.ActiveLeaveRvAdapter;
import com.example.hrapp.employees.adapter.ListRvAdapter;
import com.example.hrapp.models.Models;
import com.example.hrapp.utils.IdGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.Objects;

import static com.example.hrapp.models.Models.EmployeeApplication.PENDING;
import static com.example.hrapp.models.Models.Leave.LEAVE_DB;

public class LeaveApplication extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    private final LinkedList<String> leaveDates = new LinkedList<>();
    private final LinkedList<String> acceptedLeaveDates = new LinkedList<>();

    private RecyclerView leaveDaysPicked;
    private ListRvAdapter listRvAdapter;
    private ActiveLeaveRvAdapter activeLeaveRvAdapter;

    private Button applyButton;
    private int availableLeaveDates = 3;

    private final Models.Leave leave = new Models.Leave();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

        Toolbar leaveTb = findViewById(R.id.leaveTb);
        setSupportActionBar(leaveTb);
        leaveTb.setNavigationOnClickListener(v -> finish());


        leaveDaysPicked = findViewById(R.id.leaveDaysPicked);
        leaveDaysPicked.setLayoutManager(new LinearLayoutManager(LeaveApplication.this, RecyclerView.VERTICAL, false));
        listRvAdapter = new ListRvAdapter(LeaveApplication.this, leaveDates);
        leaveDaysPicked.setAdapter(listRvAdapter);

        RecyclerView activeLeaveRv = findViewById(R.id.activeLeaveDaysRv);
        activeLeaveRv.setLayoutManager(new LinearLayoutManager(LeaveApplication.this, RecyclerView.HORIZONTAL, false));
        activeLeaveRvAdapter = new ActiveLeaveRvAdapter(LeaveApplication.this, acceptedLeaveDates);
        activeLeaveRv.setAdapter(activeLeaveRvAdapter);
        activeLeaveRvAdapter.notifyDataSetChanged();

        DatePicker leaveDatePicker = findViewById(R.id.leaveDatePicker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            leaveDatePicker.setOnDateChangedListener(this);
        }

        applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(v -> assembleObj());

        emptyObj();

        updateList();

        getLeaveDates();

    }

    private void emptyObj() {
        leave.setDate3("");
        leave.setDate2("");
        leave.setDate1("");
        leave.setCreatedAt(IdGenerator.time);
        leave.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        leave.setStatus(PENDING);
    }

    private void getLeaveDates() {
        LeaveViewModel leaveViewModel = new ViewModelProvider(this).get(LeaveViewModel.class);
        leaveViewModel.getMyLeave(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).observe(this, leave -> {
            if (!leave.getDate1().equals("")) {
               // acceptedLeaveDates.add(leave.getDate1());
                availableLeaveDates = 1;
                activeLeaveRvAdapter.notifyDataSetChanged();
            } else if (!leave.getDate2().equals("")) {
                availableLeaveDates = 2;
             //   acceptedLeaveDates.add(leave.getDate2());
                activeLeaveRvAdapter.notifyDataSetChanged();
            } else if (!leave.getDate3().equals("")) {
                availableLeaveDates = availableLeaveDates + 1;
           //     acceptedLeaveDates.add(leave.getDate3());
                activeLeaveRvAdapter.notifyDataSetChanged();
            } else {
                availableLeaveDates = 3;
                acceptedLeaveDates.clear();
            }

            updateList();
        });
    }

    private void assembleObj() {
        for (int i = 0; i <= leaveDates.size() - 1; i++) {
            if (i == 0) {
                leave.setDate1(leaveDates.get(i));
            } else if (i == 1) {
                leave.setDate2(leaveDates.get(i));
            } else if (i == 2) {
                leave.setDate3(leaveDates.get(i));
            }
        }

        applyLeave();
    }

    private void applyLeave() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(LEAVE_DB).document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).set(leave).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LeaveApplication.this, "Dates applied", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(LeaveApplication.this, "Failed to apply leave", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateList() {
        TextView leaveDatesTv = findViewById(R.id.leaveDatesTv);
        LinearLayout pickLeaveDatesLayout = findViewById(R.id.pickLeaveDatesLayout);
        TextView leaveDaysRemainingTv = findViewById(R.id.leaveDaysRemainingTv);
        LinearLayout activeLeaveDaysLayout;
        activeLeaveDaysLayout = findViewById(R.id.activeLeaveDaysLayout);

        if (leaveDates.size() == 0) {
            leaveDaysPicked.setVisibility(View.GONE);
            listRvAdapter.notifyDataSetChanged();
            leaveDatesTv.setText("No leave dates picked");
        } else {
            leaveDatesTv.setText("Leave dates picked (" + leaveDates.size() + ")");
            leaveDaysPicked.setVisibility(View.VISIBLE);
            listRvAdapter.notifyDataSetChanged();
        }

        if (acceptedLeaveDates.size() == 0) {
            activeLeaveDaysLayout.setVisibility(View.GONE);
        } else {
            activeLeaveDaysLayout.setVisibility(View.VISIBLE);

        }

        if (availableLeaveDates == 0) {
            pickLeaveDatesLayout.setVisibility(View.GONE);
            leaveDaysRemainingTv.setText("You have " + availableLeaveDates + " days remaining");
        } else {
            leaveDaysRemainingTv.setText("You have " + availableLeaveDates + " days remaining");
            pickLeaveDatesLayout.setVisibility(View.VISIBLE);
        }

        applyButton.setEnabled(leaveDates.size() != 0);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Dialog d = new Dialog(LeaveApplication.this);
        d.setContentView(R.layout.confirm_leave_date_dialog);
        TextView dialog_confirm_date_tv = d.findViewById(R.id.dialog_confirm_date_tv);
        ImageButton cancel_dialog = d.findViewById(R.id.cancel_dialog), accept_dialog = d.findViewById(R.id.accept_dialog);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.show();

        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
        dialog_confirm_date_tv.setText("Confirm application for date " + date);
        accept_dialog.setOnClickListener(v -> {
            if (leaveDates.contains(date)) {
                Toast.makeText(LeaveApplication.this, "Date already added", Toast.LENGTH_SHORT).show();
            } else {
                if (leaveDates.size() <= availableLeaveDates) {
                    leaveDates.add(date);
                    updateList();
                } else {
                    Toast.makeText(this, "Maximum leave dates", Toast.LENGTH_SHORT).show();
                }
            }
            d.dismiss();
        });
        cancel_dialog.setOnClickListener(v -> d.dismiss());

        d.setOnDismissListener(dialog -> updateList());
    }
}