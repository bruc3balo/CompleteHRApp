package com.example.hrapp.employees.timetable;

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

import static com.example.hrapp.models.Models.TimeTableModel.ACTIVITY_DATE;
import static com.example.hrapp.models.Models.TimeTableModel.ACTIVITY_DESCRIPTION;
import static com.example.hrapp.models.Models.TimeTableModel.ACTIVITY_ID;
import static com.example.hrapp.models.Models.TimeTableModel.ACTIVITY_TITLE;
import static com.example.hrapp.models.Models.TimeTableModel.TIMELINE_DB;
import static com.example.hrapp.models.Models.Users.CREATED_AT;

public class TimetableViewModel extends ViewModel {

    private MutableLiveData<Models.TimeTableModel> getAllActivities() {
        MutableLiveData<Models.TimeTableModel> activityList = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(TIMELINE_DB).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                QuerySnapshot qs = task.getResult();

                for (int i = 0; i <= Objects.requireNonNull(qs).size() - 1; i++) {
                    Models.TimeTableModel timeTableModel = new Models.TimeTableModel();

                    String activityId = Objects.requireNonNull(qs.getDocuments().get(i).get(ACTIVITY_ID)).toString();
                    timeTableModel.setActivityId(activityId);
                    String activityTitle = Objects.requireNonNull(qs.getDocuments().get(i).get(ACTIVITY_TITLE)).toString();
                    timeTableModel.setActivityTitle(activityTitle);
                    String activityDesc = Objects.requireNonNull(qs.getDocuments().get(i).get(ACTIVITY_DESCRIPTION)).toString();
                    timeTableModel.setActivityDescription(activityDesc);
                    String createdAt = Objects.requireNonNull(qs.getDocuments().get(i).get(CREATED_AT)).toString();
                    timeTableModel.setCreatedAt(createdAt);
                    String activityDate = Objects.requireNonNull(qs.getDocuments().get(i).get(ACTIVITY_DATE)).toString();
                    timeTableModel.setActivityDate(activityDate);

                    activityList.setValue(timeTableModel);
                }

            } else {
                System.out.println(Objects.requireNonNull(task.getException()).toString());
            }
        });
        return activityList;
    }

    public LiveData<Models.TimeTableModel> getActivities() {
        return getAllActivities();
    }
}
