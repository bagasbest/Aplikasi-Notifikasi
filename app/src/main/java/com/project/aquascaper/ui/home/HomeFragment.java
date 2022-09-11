package com.project.aquascaper.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.aquascaper.R;
import com.project.aquascaper.data.Sensor;
import com.project.aquascaper.databinding.FragmentHomeBinding;
import java.util.Objects;
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String ppm = "";
    private String temperature = "";
    private String pHMeter = "";
    private String turbidity = "";
    private String ledLamp = "";
    private String fan = "";
    private String solenoid = "";

    @Override
    public void onResume() {
        super.onResume();
        fetchDateFromRealtimeDatabase();
    }

    private void fetchDateFromRealtimeDatabase() {
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("inputSensor");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Sensor sensor = dataSnapshot.getValue(Sensor.class);
                ppm = String.valueOf(sensor.getCO2());
                temperature = String.valueOf(sensor.getSuhu());
                pHMeter = String.valueOf(sensor.getpH());
                turbidity = String.valueOf(sensor.getTurbidity());
                ledLamp = sensor.getLED();
                fan = sensor.getFan();
                solenoid = sensor.getSolenoid();

                initData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

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
                    binding.temperatureValue.setText(value);
                } else if (Objects.equals(option, "pH Meter")) {
                    binding.phValue.setText(value);
                } else if (Objects.equals(option, "Turbidity")) {
                    binding.turbidityValue.setText(value);
                } else if (Objects.equals(option, "PPM")) {
                    binding.ppmValue.setText(value);
                }
                processValue(option, pb, value, dialog);
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void processValue(String option, ProgressBar pb, String value, Dialog dialog) {
        pb.setVisibility(View.GONE);
        dialog.dismiss();
    }

    private void processValue(String option) {

    }

    private void showToast(String option) {
        Toast.makeText(getActivity(), option, Toast.LENGTH_SHORT).show();
    }


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}