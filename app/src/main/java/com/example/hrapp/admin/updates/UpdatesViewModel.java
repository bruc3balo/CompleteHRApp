package com.example.hrapp.admin.updates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hrapp.models.Models;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.hrapp.models.Models.Updates.UPDATE_CONTENT;
import static com.example.hrapp.models.Models.Updates.UPDATE_DB;
import static com.example.hrapp.models.Models.Updates.UPDATE_ID;
import static com.example.hrapp.models.Models.Updates.UPDATE_POSTED_AT;
import static com.example.hrapp.models.Models.Updates.UPDATE_TITLE;

public class UpdatesViewModel extends ViewModel {

    public UpdatesViewModel() {
    }

    private MutableLiveData<Models.Updates> getUpdatesList() {
        MutableLiveData<Models.Updates> updatesMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fail = "Failed to get data";
        db.collection(UPDATE_DB).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                QuerySnapshot qs = task.getResult();

                for (int i = 0; i <= qs.size() - 1; i++) {
                    String updateId = "", postedAt = "", content = "", title = "";
                    Models.Updates updates = new Models.Updates();
                    List<DocumentSnapshot> ds = qs.getDocuments();


                    try {
                        updateId = ds.get(i).get(UPDATE_ID).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        updateId = fail;
                    }

                    try {
                        postedAt = ds.get(i).get(UPDATE_POSTED_AT).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        postedAt = fail;
                    }

                    try {
                        content = ds.get(i).get(UPDATE_CONTENT).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        content = fail;
                    }

                    try {
                        title = ds.get(i).get(UPDATE_TITLE).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        title = fail;
                    }

                    updates.setUpdateId(updateId);
                    updates.setUpdatePostedAt(postedAt);
                    updates.setUpdateContent(content);
                    updates.setUpdateTitle(title);

                    System.out.println("update "+title);

                    updatesMutableLiveData.setValue(updates);

                }

            } else {
                Models.Updates updates = new Models.Updates();

                updates.setUpdateId(fail);
                updates.setUpdatePostedAt(fail);
                updates.setUpdateContent(fail);
                updates.setUpdateTitle(fail);

                updatesMutableLiveData.setValue(updates);
            }
        });
        return updatesMutableLiveData;
    }

    public LiveData<Models.Updates> getUpdates() {
        return getUpdatesList();
    }


}
