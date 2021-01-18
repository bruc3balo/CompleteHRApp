package com.example.hrapp.admin.advances;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hrapp.models.Models;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.hrapp.models.Models.Advances.ADVANCE_DB;
import static com.example.hrapp.models.Models.Advances.MAX;
import static com.example.hrapp.models.Models.Advances.MIN;
import static com.example.hrapp.models.Models.Advances.REASON;
import static com.example.hrapp.models.Models.Users.UID;

public class AdvancesViewModel extends ViewModel {

    private MutableLiveData<Models.Advances> getAllAdvances() {
        MutableLiveData<Models.Advances> advancesMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ADVANCE_DB).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (int i = 0; i <= task.getResult().size() - 1; i++) {
                    Models.Advances advances = new Models.Advances();
                    try {
                        advances.setUid(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(UID)).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        advances.setUid("");
                    }

                    try {
                        advances.setReason(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(REASON)).toString());
                    } catch (Exception e ){
                        e.printStackTrace();
                        advances.setReason("");
                    }


                    try {
                        advances.setMin(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(MIN)).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        advances.setMin("");
                    }

                    try {
                        advances.setMax(Objects.requireNonNull(task.getResult().getDocuments().get(i).get(MAX)).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        advances.setMax("");
                    }

                    advancesMutableLiveData.setValue(advances);
                }
            } else {
                System.out.println(Objects.requireNonNull(task.getException()).toString());
            }
        });
        return advancesMutableLiveData;
    }

    public LiveData<Models.Advances> getAdvances() {
        return getAllAdvances();
    }
}
