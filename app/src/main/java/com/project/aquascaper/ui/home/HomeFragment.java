package com.project.aquascaper.ui.home;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.aquascaper.HomeActivity;
import com.project.aquascaper.MainActivity;
import com.project.aquascaper.R;
import com.project.aquascaper.data.Sensor;
import com.project.aquascaper.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class HomeFragment extends Fragment {

    /// kumpulan variabel sensor sengaja di taruh diatas supaya bisa di akses seluruh class
    private FragmentHomeBinding binding;
    private String ppm = "";
    private String temperature = "";
    private String pHMeter = "";
    private String turbidity = "";
    private String ledLamp = "";
    private String fan = "";
    private String solenoid = "";
    private boolean isWaiting = false;
    private int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        /// fungsi untuk set data sensor dari firebase
        fetchDateFromRealtimeDatabase();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionMenu();
            }
        });

        binding.onFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueNoOff("Fan", "ON");
                binding.onFan.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
                binding.onFan.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

                binding.offFan.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_on_off));
                binding.offFan.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

                showToast("Fan ON");
            }
        });

        binding.offFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueNoOff("Fan", "OFF");
                binding.offFan.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
                binding.offFan.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

                binding.onFan.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_on_off));
                binding.onFan.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

                showToast("Fan OFF");
            }
        });

        binding.onLedLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueNoOff("LED", "ON");
                binding.onLedLamp.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
                binding.onLedLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

                binding.offLedLamp.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_on_off));
                binding.offLedLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

                showToast("LED Lamp ON");
            }
        });

        binding.offLedLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueNoOff("LED", "OFF");
                binding.offLedLamp.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
                binding.offLedLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

                binding.onLedLamp.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_on_off));
                binding.onLedLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

                showToast("LED Lamp OFF");
            }
        });

        binding.onSolenoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueNoOff("Solenoid", "ON");
                binding.onSolenoid.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
                binding.onSolenoid.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

                binding.offSolenoid.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_on_off));
                binding.offSolenoid.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

                showToast("Solenoid ON");
            }
        });

        binding.offSolenoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueNoOff("Solenoid", "OFF");
                binding.offSolenoid.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
                binding.offSolenoid.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

                binding.onSolenoid.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_border_on_off));
                binding.onSolenoid.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));


                showToast("Solenoid OFF");
            }
        });

        binding.temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Temperature");
            }
        });

        binding.phMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("pH Meter");
            }
        });

        binding.turbidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Turbidity");
            }
        });

        binding.ppm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("PPM");
            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDateFromRealtimeDatabase();
            }
        });
    }



    //======================================== FUNCTION ==================================================

    private void fetchDateFromRealtimeDatabase() {
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("inputSensor");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /// membaca data dari firebase dan dimasukkan kedalam variable yang sudah dibuat
                Sensor sensor = dataSnapshot.getValue(Sensor.class);
                ppm = String.valueOf(sensor.getCO2());
                temperature = String.valueOf(sensor.getSuhu());
                pHMeter = String.valueOf(sensor.getpH());
                turbidity = String.valueOf(sensor.getTurbidity());
                ledLamp = sensor.getLED();
                fan = sensor.getFan();
                solenoid = sensor.getSolenoid();

                try {
                    /// fungsi untuk menampilkan selutuh nilai berdasarkan variabel dari atas
                    initData();
                } catch (Exception e) {
                    Log.e("ERROR HOME", e.getMessage());
                }

                if(!isWaiting) {
                    isWaiting = true;
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        //// fungsi untuk mengecek apakah melebihi batas / belum, sehingga notifikasi nanti bisa tampil
                        initNotification();
                        isWaiting = false;
                    }, 5000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void initNotification() {
        if(Double.parseDouble(turbidity) > 25.0) {
            Log.e("1", "1");
            String body = "Air terlalu keruh, pastikan segera membersihkan aquascape";
            sendNotif(body);
            saveNotif(body);
        }

        if(Double.parseDouble(ppm) > 800.0 && Objects.equals(solenoid, "ON")) {
            Log.e("1", "2");
            String body = "CO2 terlalu tinggi, pastikan segera matikan solenoid";
            sendNotif(body);
            saveNotif(body);
        } else if (Double.parseDouble(ppm) < 400.0&& Objects.equals(solenoid, "OFF")){
            Log.e("1", "3");
            Log.e("dasadasa", "soleeeee on");
            String body = "CO2 terlalu rendah, pastikan segera menyalakan solenoid";
            sendNotif(body);
            saveNotif(body);
        }


        if(Double.parseDouble(pHMeter) > 8.0) {
            Log.e("1", "4");
            String body = "pH terlalu basa, pastikan segera melakukan tindakan menurunkan kadar pH";
            sendNotif(body);
            saveNotif(body);
        } else if (Double.parseDouble(pHMeter) < 6.0){
            Log.e("1", "5");
            String body = "pH terlalu asam, pastikan segera melakukan tindakan menaikkan kadar pH";
            sendNotif(body);
            saveNotif(body);
        }


        if(Double.parseDouble(temperature) > 28.0 && Objects.equals(fan, "OFF")) {
            Log.e("1", "6");

            String body = "Suhu terlalu panas, pastikan untuk segera menyalakan kipas pada aquascape";
            sendNotif(body);
            saveNotif(body);
        } else if (Double.parseDouble(temperature) < 24.0 && Objects.equals(ledLamp, "OFF")){
            Log.e("1", "7");
            String body = "Suhu terlalu dingin, pastikan untuk segera menyalakan lampu pada aquascape";
            sendNotif(body);
            saveNotif(body);
        }
    }

    /// fungsi untuk menyimpan notifikasi di firebase
    private void saveNotif(String body) {
        long timeInMillis = System.currentTimeMillis();
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        String hour = formatter.format(calendar.getTime());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("notification/"+ timeInMillis);
        ref.child("notification").setValue(body);
        ref.child("hour").setValue(hour);
    }

    private void initData() {
        binding.temperatureValue.setText(temperature+ "°C");
        binding.phValue.setText(pHMeter+ "");
        binding.turbidityValue.setText(turbidity+ " NTU");
        binding.ppmValue.setText(ppm+ "");

        if(!Objects.equals(ledLamp, "OFF")) {
            binding.onLedLamp.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
            binding.onLedLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        } else{
            binding.offLedLamp.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
            binding.offLedLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        }

        if(!Objects.equals(fan, "OFF")) {
            binding.onFan.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
            binding.onFan.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        } else {
            binding.offFan.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
            binding.offFan.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        }

        if(!Objects.equals(solenoid, "OFF")) {
            binding.onSolenoid.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
            binding.onSolenoid.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        } else {
            binding.offSolenoid.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_on_off));
            binding.offSolenoid.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        }

        binding.swipeRefresh.setRefreshing(false);
    }



    /// fungsi untuk update value sensor
    private void updateValueNoOff(String sensor, String option) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("inputSensor");
        ref.child(sensor).setValue(option);
    }

    /// fungsi untuk menampilkan kolom inputan ketika salah satu item sensor di klik untuk di update
    private void showDialog(String option) {
        Dialog dialog = new Dialog(getActivity());
        TextView title;
        EditText inputValue;
        ProgressBar pb;
        Button saveButton;
        dialog.setContentView(R.layout.popup_menu_input);
        title = dialog.findViewById(R.id.title);
        inputValue = dialog.findViewById(R.id.inputValue);
        pb = dialog.findViewById(R.id.progressBar);
        saveButton = dialog.findViewById(R.id.saveButton);
        title.setText(option);

        if(Objects.equals(option, "Temperature")) {
            inputValue.setHint("Input Temperature");
            inputValue.setText(temperature);
        } else if (Objects.equals(option, "pH Meter")) {
            inputValue.setHint("Input pH Meter");
            inputValue.setText(pHMeter);
        } else if (Objects.equals(option, "Turbidity")) {
            inputValue.setHint("Input Turbidity");
            inputValue.setText(turbidity);
        } else if (Objects.equals(option, "PPM")) {
            inputValue.setHint("Input PPM");
            inputValue.setText(ppm);
        }

        saveButton.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            String value = inputValue.getText().toString().trim();
            if(value.isEmpty()) {
                showToast("Nilai " + option + " wajib diisi");
                pb.setVisibility(View.GONE);
            } else {
                if(Objects.equals(option, "Temperature")) {
                    binding.temperatureValue.setText(value + "°C");
                    updateValueSensor("suhu", value);
                } else if (Objects.equals(option, "pH Meter")) {
                    binding.phValue.setText(value);
                    updateValueSensor("pH", value);
                } else if (Objects.equals(option, "Turbidity")) {
                    binding.turbidityValue.setText(value+" NTU");
                    updateValueSensor("turbidity", value);
                } else if (Objects.equals(option, "PPM")) {
                    binding.ppmValue.setText(value);
                    updateValueSensor("CO2", value);
                }
                processValue(option, pb, value, dialog);
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    /// update nilai sensor
    private void updateValueSensor(String sensor, String value) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("inputSensor");
        ref.child(sensor).setValue(Double.parseDouble(value));

        showToast("Anda mengupdate nilai " + sensor + " menjadi " + value);
    }

    /// hilangkan kolom inputan setelah update data
    private void processValue(String option, ProgressBar pb, String value, Dialog dialog) {
        pb.setVisibility(View.GONE);
        dialog.dismiss();
    }

    private void showToast(String option) {
        Toast.makeText(getActivity(), option, Toast.LENGTH_SHORT).show();
    }


    /// menampilkan pilihan help atau aboutr apps
    private void showOptionMenu() {
        TextView about, help;
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_menu_option);

        about = dialog.findViewById(R.id.about);
        help = dialog.findViewById(R.id.help);


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), AboutActivity.class));
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), HelpActivity.class));
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    /// menampilkan notifikasi
    private void sendNotif(String notification) {

        SharedPreferences prefs = getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_PRIVATE);
        boolean isNotificationEnable = prefs.getBoolean("notification", false);

        if(isNotificationEnable) {
            buildNotification(notification);
        }
    }

    /// fungsi untuk menampilkan notifikasi
    private void buildNotification(String notification) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final int NOTIFICATION_ID = (int)System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), NOTIFICATION_ID,intent, PendingIntent.FLAG_IMMUTABLE);

        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Aquascaper")
                .setContentText(notification)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Aquascaper", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}