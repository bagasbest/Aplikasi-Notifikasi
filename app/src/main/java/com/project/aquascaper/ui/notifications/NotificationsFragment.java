package com.project.aquascaper.ui.notifications;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.aquascaper.R;
import com.project.aquascaper.databinding.FragmentNotificationsBinding;
import com.project.aquascaper.ui.home.AboutActivity;
import com.project.aquascaper.ui.home.HelpActivity;

import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.noData.setVisibility(View.VISIBLE);
        binding.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptionMenu();
            }
        });

        SharedPreferences prefs = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        boolean isNotificationEnable = prefs.getBoolean("notification", false);
        binding.switchBtn.setChecked(isNotificationEnable);
        binding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    editor.putBoolean("notification", true);
                    Toast.makeText(getActivity(), "Notifikasi Dinyalakan", Toast.LENGTH_SHORT).show();
                } else  {
                    editor.putBoolean("notification", false);
                    Toast.makeText(getActivity(), "Notifikasi Dimatikan", Toast.LENGTH_SHORT).show();
                }
                editor.apply();
            }
        });

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