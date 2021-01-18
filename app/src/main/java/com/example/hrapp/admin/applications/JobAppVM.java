package com.example.hrapp.admin.applications;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hrapp.models.Models;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

import static com.example.hrapp.models.Models.Applications.APP_ID;
import static com.example.hrapp.models.Models.EmployeeApplication.APP_STATUS;
import static com.example.hrapp.models.Models.EmployeeApplication.COVER_LETTER;
import static com.example.hrapp.models.Models.EmployeeApplication.JOB_APPLICATION_DB;
import static com.example.hrapp.models.Models.EmployeeApplication.WHY_TEXT;
import static com.example.hrapp.models.Models.Users.CREATED_AT;
import static com.example.hrapp.models.Models.Users.EMAIL_ADDRESS;
import static com.example.hrapp.models.Models.Users.UID;

public class JobAppVM extends ViewModel {


    private MutableLiveData<Models.EmployeeApplication> getFromDb(String appId) {
        MutableLiveData<Models.EmployeeApplication> employmentList = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JOB_APPLICATION_DB).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Models.EmployeeApplication employeeApplication;
                    String fail = "Failed to get data";
                    List<DocumentSnapshot> ds = Objects.requireNonNull(task.getResult()).getDocuments();
                    for (int i = 0; i <= ds.size() - 1; i++) {
                        employeeApplication = new Models.EmployeeApplication();

                        String key = Objects.requireNonNull(ds.get(i).get(APP_ID)).toString();
                        if (key.equals(appId)) {
                            employeeApplication.setEmail_address(Objects.requireNonNull(ds.get(i).get(EMAIL_ADDRESS)).toString());
                            employeeApplication.setUid(Objects.requireNonNull(ds.get(i).get(UID)).toString());
                            employeeApplication.setStatus(Objects.requireNonNull(ds.get(i).get(APP_STATUS)).toString());

                            employeeApplication.setWhyText(Objects.requireNonNull(ds.get(i).get(WHY_TEXT)).toString());
                            employeeApplication.setCoverLetter(Objects.requireNonNull(ds.get(i).get(COVER_LETTER)).toString());
                            employeeApplication.setCreatedAt(Objects.requireNonNull(ds.get(i).get(CREATED_AT)).toString());
                            employeeApplication.setAppId(appId);
                            employmentList.setValue(employeeApplication);
                        }

                    }
                } else {
                    System.out.println(Objects.requireNonNull(task.getException()).toString());
                }
            }
        });

        return employmentList;
    }

    public LiveData<Models.EmployeeApplication> getEmploymentApplications(String appId) {
        return getFromDb(appId);
    }
}
