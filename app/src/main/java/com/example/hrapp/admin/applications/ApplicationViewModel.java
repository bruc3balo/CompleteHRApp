package com.example.hrapp.admin.applications;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hrapp.models.Models;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.hrapp.models.Models.Applications.APPLICATION_DB;
import static com.example.hrapp.models.Models.Applications.APP_DESCRIPTION;
import static com.example.hrapp.models.Models.Applications.APP_EMPLOYMENT_TYPE;
import static com.example.hrapp.models.Models.Applications.APP_ID;
import static com.example.hrapp.models.Models.Applications.APP_JOB_FUNCTION;
import static com.example.hrapp.models.Models.Applications.APP_POSITION_FIELD;
import static com.example.hrapp.models.Models.Applications.APP_QUALIFICATIONS;
import static com.example.hrapp.models.Models.Applications.APP_RESPONSIBILITIES;
import static com.example.hrapp.models.Models.Applications.APP_TITLE;
import static com.example.hrapp.models.Models.Applications.USERS_APPLIED;
import static com.example.hrapp.models.Models.Users.CREATED_AT;

public class ApplicationViewModel extends ViewModel {

    private MutableLiveData<Models.Applications> getApplications() {
        MutableLiveData<Models.Applications> applicationML = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(APPLICATION_DB).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String fail = "Failed to get data";
                if (task.isSuccessful()) {
                    QuerySnapshot qs = task.getResult();
                    for (int i = 0; i <= qs.size() - 1; i++) {

                        Models.Applications applications = new Models.Applications();
                        try {
                            applications.setAppId(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_ID)).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppId(fail);
                        }

                        try {
                            applications.setCreatedAt(Objects.requireNonNull(qs.getDocuments().get(i).get(CREATED_AT)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setCreatedAt(fail);
                        }

                        try {
                            applications.setAppPositionField(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_POSITION_FIELD)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppPositionField(fail);
                        }

                        try {
                            applications.setAppJobFunction(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_JOB_FUNCTION)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppJobFunction(fail);
                        }

                        try {
                            applications.setAppQualifications(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_QUALIFICATIONS)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppQualifications(fail);
                        }

                        try {
                            applications.setAppResponsibilities(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_RESPONSIBILITIES)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppResponsibilities(fail);
                        }

                        try {
                            applications.setAppDescription(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_DESCRIPTION)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppDescription(fail);
                        }

                        try {
                            applications.setAppTitle(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_TITLE)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppTitle(fail);

                        }

                        try {
                            applications.setAppEmploymentType(Objects.requireNonNull(qs.getDocuments().get(i).get(APP_EMPLOYMENT_TYPE)).toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setAppEmploymentType(fail);

                        }

                    /*    try {
                            ArrayList<Models.EmployeeApplication> employeeApplications = (ArrayList<Models.EmployeeApplication>) Objects.requireNonNull(qs.getDocuments().get(i).get(USERS_APPLIED));
                            applications.setUsersApplied(employeeApplications);

                            for (int b = 0 ;b <= employeeApplications.size() - 1 ; b++) {
                               // applications.setUsersApplied(employeeApplications.get(b));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            applications.setUsersApplied(null);

                        }*/

                        applicationML.setValue(applications);
                    }
                } else {
                    applicationML.setValue(new Models.Applications(fail));
                    System.out.println(Objects.requireNonNull(task.getException()).toString());
                }
            }
        });
        return applicationML;
    }


    public LiveData<Models.Applications> applicationsLiveData() {
        return getApplications();
    }
}
