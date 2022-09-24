package com.project.aquascaper.ui.notifications;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationViewModel extends ViewModel {
    /// KELAS VIEW MODEL BERFUNGSI UNTUK MENGAMBIL DATA DARI FIRESTORE KEMUDIAN MENERUSKANNYA KEPADA ACTIVITY YANG DI TUJU
    /// CONTOH KELAS NOTIFICATION VIEW MODEL MENGAMBIL DATA DARI COLLECTION "notification", KEMUDIAN SETELAH DI AMBIL, DATA DIMASUKKAN KEDALAM MODEL, SETELAH ITU DITERUSKAN KEPADA FRAGMENT NOTIFICATION, SEHINGGA FRAGMENT DAPAT MENAMPILKAN DATA NOTIFICATION

    private final MutableLiveData<ArrayList<NotificationModel>> listNotification = new MutableLiveData<>();
    final ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<>();

    private static final String TAG = NotificationViewModel.class.getSimpleName();

    public void setListNotification() {
        notificationModelArrayList.clear();

        try {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("notification");


            /// mendapatkan list notifikasi dari firebase
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        try {
                            if (ds.child("hour").getValue() != null && ds.child("notification").getValue() != null) {
                                String hour = "" + ds.child("hour").getValue();
                                String notification = "" + ds.child("notification").getValue();

                                NotificationModel model = new NotificationModel();
                                model.setNotification(notification);
                                model.setHour(hour);
                                notificationModelArrayList.add(model);
                            }
                        } catch (Exception e) {
                            Log.e("MESSAGE ERROR", e.getMessage());
                        }

                    }
                    listNotification.postValue(notificationModelArrayList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    public LiveData<ArrayList<NotificationModel>> getNotification() {
        return listNotification;
    }

}
