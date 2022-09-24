package com.project.aquascaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.aquascaper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /// Logo aplikasi
        Glide.with(MainActivity.this)
                .load(R.drawable.logo)
                .into(binding.logo);

//        createFcmToken();

        /// fungsi untuk delay splash screen selama 3 detik
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        }, 3000);
    }

//    private void createFcmToken() {
//        FirebaseMessaging.getInstance().subscribeToTopic("all");
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if(task.isSuccessful()) {
//                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            DatabaseReference ref = database.getReference("fcm");
//                            ref.child("fcm").setValue(task.getResult());
//                            ref.child("active").setValue(true);
//                        }
//                    }
//                });
//    }
}