package com.project.aquascaper.ui.graph;

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

public class GraphViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<GraphModel>> listData = new MutableLiveData<>();
    final ArrayList<GraphModel> graphicSellModelArrayList = new ArrayList<>();
    private static final String TAG = GraphViewModel.class.getSimpleName();

    public void setGraph() {
        graphicSellModelArrayList.clear();

        try {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("graph");


            /// mendapatkan list notifikasi dari firebase
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        try {
                            if (ds.child("value").getValue() != null) {
                                String dayOfWeek = "" + ds.child("dayOfWeek").getValue();
                                /// week sekarang dimulai dari week 44, untuk inisiasi week1, 2, dst maka harus dikurangi 43
                                int week = Integer.parseInt("" + ds.child("week").getValue()) - 43;
                                String parameter = "" + ds.child("parameter").getValue();
                                double value = Double.parseDouble("" + ds.child("value").getValue());

                                GraphModel model = new GraphModel();
                                model.setDayOfWeek(dayOfWeek);
                                model.setWeek(week);
                                model.setParameter(parameter);
                                model.setValue(value);
                                graphicSellModelArrayList.add(model);
                            }
                        } catch (Exception e) {
                            Log.e("MESSAGE ERROR", e.getMessage());
                        }

                    }
                    listData.postValue(graphicSellModelArrayList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    public LiveData<ArrayList<GraphModel>> getGraphic() {
        return listData;
    }
}
