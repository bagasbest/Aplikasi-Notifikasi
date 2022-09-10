package com.project.aquascaper.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.project.aquascaper.R;
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

        binding.ledLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("LED Lamp");
            }
        });

        binding.ppmValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("PPM");
            }
        });

        binding.solenoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Solenoid");
            }
        });

        binding.fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Fan");
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
            inputValue.setText(temperature);
        } else if (Objects.equals(option, "pH Meter")) {
            inputValue.setText(pHMeter);
        } else if (Objects.equals(option, "Turbidity")) {
            inputValue.setText(turbidity);
        } else if (Objects.equals(option, "PPM")) {
            inputValue.setText(ppm);
        } else if (Objects.equals(option, "LED Lamp")) {
            inputValue.setText(ledLamp);
        } else if (Objects.equals(option, "Fan")) {
            inputValue.setText(fan);
        } else if (Objects.equals(option, "Solenoid")) {
            inputValue.setText(solenoid);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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