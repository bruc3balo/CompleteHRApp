package com.example.hrapp.employees.leave;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hrapp.models.Models;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import static com.example.hrapp.models.Models.EmployeeApplication.APP_STATUS;
import static com.example.hrapp.models.Models.Leave.DATE_1;
import static com.example.hrapp.models.Models.Leave.DATE_2;
import static com.example.hrapp.models.Models.Leave.DATE_3;
import static com.example.hrapp.models.Models.Leave.LEAVE_DB;
import static com.example.hrapp.models.Models.Users.CREATED_AT;
import static com.example.hrapp.models.Models.Users.UID;

public class LeaveViewModel extends ViewModel {


    private MutableLiveData<Models.Leave> getAllLeaveDates() {
        MutableLiveData<Models.Leave> leaveMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(LEAVE_DB).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (int i = 0; i <= task.getResult().size() - 1; i++) {

                        Models.Leave leave = new Models.Leave();

                        try {
                            leave.setUid(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(UID)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setUid("");
                        }

                        try {
                            leave.setCreatedAt(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(CREATED_AT)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            leave.setStatus(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(APP_STATUS)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setStatus("");
                        }
                        try {
                            leave.setDate1(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(DATE_1)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setDate1("");
                        }

                        try {
                            leave.setDate2(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(DATE_2)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setDate2("");
                        }

                        try {
                            leave.setDate3(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(DATE_3)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setDate3("");
                        }


                        leaveMutableLiveData.setValue(leave);
                    }
                } else {
                    System.out.println("Failed to get leaves");
                }
            }
        });
        return leaveMutableLiveData;
    }

    public LiveData<Models.Leave> getLeaves() {
        return getAllLeaveDates();
    }


    public LiveData<Models.Leave> getMyLeave(String uid) {
        return getMyLeaveDate(uid);
    }

    private MutableLiveData<Models.Leave> getMyLeaveDate(String uid) {
        MutableLiveData<Models.Leave> myLeaveMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(LEAVE_DB).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                for (int i = 0; i <= task.getResult().size() - 1; i++) {

                    Models.Leave leave = new Models.Leave();

                    if (Objects.requireNonNull(task.getResult().getDocuments().get(i).get(UID)).toString().equals(uid)) {

                        try {
                            leave.setUid(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(UID)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setUid("");
                        }

                        try {
                            leave.setCreatedAt(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(CREATED_AT)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            leave.setStatus(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(APP_STATUS)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setStatus("");
                        }
                        try {
                            leave.setDate1(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(DATE_1)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setDate1("");
                        }

                        try {
                            leave.setDate2(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(DATE_2)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setDate2("");
                        }

                        try {
                            leave.setDate3(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(DATE_3)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            leave.setDate3("");
                        }

                        System.out.println("dates are 1: "+leave.getDate1() +"2"+ leave.getDate2() + "3"+leave.getDate3());

                        myLeaveMutableLiveData.setValue(leave);
                    }
                }
            } else {
                System.out.println("Failed to get leaves");
            }
        });

        return myLeaveMutableLiveData;
    }
}
