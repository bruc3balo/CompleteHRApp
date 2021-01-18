package com.example.hrapp.admin.userManagement;

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

import static com.example.hrapp.models.Models.Users.CREATED_AT;
import static com.example.hrapp.models.Models.Users.DOB;
import static com.example.hrapp.models.Models.Users.EMAIL_ADDRESS;
import static com.example.hrapp.models.Models.Users.EMPLOYEE_DB;
import static com.example.hrapp.models.Models.Users.FIRST_NAME;
import static com.example.hrapp.models.Models.Users.KRA_PIN;
import static com.example.hrapp.models.Models.Users.LAST_NAME;
import static com.example.hrapp.models.Models.Users.LEAVE_DATES_AVAILABLE;
import static com.example.hrapp.models.Models.Users.NATIONAL_ID;
import static com.example.hrapp.models.Models.Users.NHIF;
import static com.example.hrapp.models.Models.Users.NSSF;
import static com.example.hrapp.models.Models.Users.PERFORMANCE_RATING;
import static com.example.hrapp.models.Models.Users.PHONE_NO;
import static com.example.hrapp.models.Models.Users.POSITION;
import static com.example.hrapp.models.Models.Users.ROLE;
import static com.example.hrapp.models.Models.Users.SECRET_KEY;
import static com.example.hrapp.models.Models.Users.UID;
import static com.example.hrapp.models.Models.Users.WAGE;

public class UsersViewModel extends ViewModel {

    String fail = "Fail to get data";

    public UsersViewModel() {
    }


    private MutableLiveData<Models.Users> getUsers() {
        MutableLiveData<Models.Users> employeeMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(EMPLOYEE_DB).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot qs = task.getResult();

                for (int i = 0; i <= qs.size() - 1; i++) {
                    List<DocumentSnapshot> ds = qs.getDocuments();
                    Models.Users employee = new Models.Users();

                    String uid = "", first_name = "", last_name = "", date_of_birth = "", phone_number = "", email_address = "", wage = "", leave_dates_available = "", performance_ratings = "", nationalId = "", kraPin = "", nssf = "", nhif = "", createdAt = "", role = "";

                    try {
                        uid = ds.get(i).get(UID).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        uid = fail;
                    }

                    try {
                        first_name = ds.get(i).get(FIRST_NAME).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        first_name = fail;
                    }

                    try {
                        last_name = ds.get(i).get(LAST_NAME).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        last_name = fail;
                    }


                    try {
                        date_of_birth = ds.get(i).get(DOB).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        date_of_birth = fail;
                    }

                    try {
                        phone_number = ds.get(i).get(PHONE_NO).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        phone_number = fail;
                    }

                    try {
                        email_address = ds.get(i).get(EMAIL_ADDRESS).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        email_address = fail;
                    }

                    try {
                        wage = ds.get(i).get(WAGE).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        wage = fail;
                    }

                    try {
                        leave_dates_available = ds.get(i).get(LEAVE_DATES_AVAILABLE).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        leave_dates_available = fail;
                    }

                    try {
                        performance_ratings = ds.get(i).get(PERFORMANCE_RATING).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        performance_ratings = fail;
                    }

                    try {
                        nationalId = ds.get(i).get(NATIONAL_ID).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        nationalId = fail;
                    }

                    try {
                        kraPin = ds.get(i).get(KRA_PIN).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        kraPin = fail;
                    }

                    try {
                        nhif = ds.get(i).get(NHIF).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        nhif = fail;
                    }

                    try {
                        nssf = ds.get(i).get(NSSF).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        nssf = fail;
                    }

                    try {
                        createdAt = ds.get(i).get(CREATED_AT).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        createdAt = fail;
                    }

                    try {
                        role = ds.get(i).get(ROLE).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        role = fail;
                    }


                    employee.setPhone_number(phone_number);
                    employee.setEmail_address(email_address);
                    employee.setCreatedAt(createdAt);

                    employee.setDate_of_birth(date_of_birth);
                    employee.setFirst_name(first_name);
                    employee.setLast_name(last_name);

                    employee.setKraPin(kraPin);
                    employee.setLeave_dates_available(leave_dates_available);
                    employee.setNhif(nhif);

                    employee.setNssf(nssf);
                    employee.setNationalId(nationalId);
                    employee.setRole(role);

                    employee.setWage(wage);
                    employee.setUid(uid);
                    employee.setPerformance_ratings(performance_ratings);

                    employeeMutableLiveData.setValue(employee);

                }

            } else {
                Models.Users employee = new Models.Users();
                employee.setPhone_number(fail);
                employee.setEmail_address(fail);
                employee.setCreatedAt(fail);

                employee.setDate_of_birth(fail);
                employee.setFirst_name(fail);
                employee.setLast_name(fail);

                employee.setKraPin(fail);
                employee.setLeave_dates_available(fail);
                employee.setNhif(fail);

                employee.setNssf(fail);
                employee.setNationalId(fail);
                employee.setRole(fail);

                employee.setWage(fail);
                employee.setUid(fail);
                employee.setPerformance_ratings(fail);
                employeeMutableLiveData.setValue(employee);
            }
        });
        return employeeMutableLiveData;
    }

    private LiveData<Models.Users> getAUsersData(String uid) {
        MutableLiveData<Models.Users> employeeLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(EMPLOYEE_DB).document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DocumentSnapshot ds = task.getResult();
                Models.Users employee = new Models.Users();

                String uid1 = "", position = "", secret_key = "", first_name = "", last_name = "", date_of_birth = "", phone_number = "", email_address = "", wage = "", leave_dates_available = "", performance_ratings = "", nationalId = "", kraPin = "", nssf = "", nhif = "", createdAt = "", role = "";

                try {
                    position = ds.get(POSITION).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    position = fail;
                }

                try {
                    secret_key = ds.get(SECRET_KEY).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    secret_key = fail;
                }


                try {
                    uid1 = ds.get(UID).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    uid1 = fail;
                }

                try {
                    first_name = ds.get(FIRST_NAME).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    first_name = fail;
                }

                try {
                    last_name = ds.get(LAST_NAME).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    last_name = fail;
                }


                try {
                    date_of_birth = ds.get(DOB).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    date_of_birth = fail;
                }

                try {
                    phone_number = ds.get(PHONE_NO).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    phone_number = fail;
                }

                try {
                    email_address = ds.get(EMAIL_ADDRESS).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    email_address = fail;
                }

                try {
                    wage = ds.get(WAGE).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    wage = fail;
                }

                try {
                    leave_dates_available = ds.get(LEAVE_DATES_AVAILABLE).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    leave_dates_available = fail;
                }

                try {
                    performance_ratings = ds.get(PERFORMANCE_RATING).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    performance_ratings = fail;
                }

                try {
                    nationalId = ds.get(NATIONAL_ID).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    nationalId = fail;
                }

                try {
                    kraPin = ds.get(KRA_PIN).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    kraPin = fail;
                }

                try {
                    nhif = ds.get(NHIF).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    nhif = fail;
                }

                try {
                    nssf = ds.get(NSSF).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    nssf = fail;
                }

                try {
                    createdAt = ds.get(CREATED_AT).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    createdAt = fail;
                }

                try {
                    role = ds.get(ROLE).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    role = fail;
                }


                employee.setPhone_number(phone_number);
                employee.setEmail_address(email_address);
                employee.setCreatedAt(createdAt);

                employee.setDate_of_birth(date_of_birth);
                employee.setFirst_name(first_name);
                employee.setLast_name(last_name);

                employee.setKraPin(kraPin);
                employee.setLeave_dates_available(leave_dates_available);
                employee.setNhif(nhif);

                employee.setNssf(nssf);
                employee.setNationalId(nationalId);
                employee.setRole(role);

                employee.setWage(wage);
                employee.setUid(uid1);
                employee.setPerformance_ratings(performance_ratings);

                employee.setPosition(position);
                employee.setSecret_key(secret_key);

                employeeLiveData.setValue(employee);


            } else {
                Models.Users employee = new Models.Users();
                employee.setPhone_number(fail);
                employee.setEmail_address(fail);
                employee.setCreatedAt(fail);

                employee.setDate_of_birth(fail);
                employee.setFirst_name(fail);
                employee.setLast_name(fail);

                employee.setKraPin(fail);
                employee.setLeave_dates_available(fail);
                employee.setNhif(fail);

                employee.setNssf(fail);
                employee.setNationalId(fail);
                employee.setRole(fail);

                employee.setWage(fail);
                employee.setUid(fail);
                employee.setPerformance_ratings(fail);

                employee.setPosition(fail);
                employee.setSecret_key(fail);

                employeeLiveData.setValue(employee);
            }
        });
        return employeeLiveData;
    }

    public LiveData<Models.Users> getUsersList() {
        return getUsers();
    }

    public LiveData<Models.Users> getUsersData(String uid) {
        return getAUsersData(uid);
    }


}
