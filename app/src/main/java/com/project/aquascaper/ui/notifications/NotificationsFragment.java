package com.project.aquascaper.ui.notifications;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.aquascaper.R;
import com.project.aquascaper.databinding.FragmentNotificationsBinding;
import com.project.aquascaper.ui.home.AboutActivity;
import com.project.aquascaper.ui.home.HelpActivity;
public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        initRecyclerView();
        initViewModel();
        return binding.getRoot();
    }

    /// FUNGSI UNTUK MENAMPILKAN LIST DATA
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.notificationRv.setLayoutManager(layoutManager);
        adapter = new NotificationAdapter();
        binding.notificationRv.setAdapter(adapter);
    }

    /// FUNGSI UNTUK MENDAPATKAN LIST DATA notification DARI FIREBASE
    private void initViewModel() {
        NotificationViewModel viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.setListNotification();
        viewModel.getNotification().observe(getViewLifecycleOwner(), notificationList -> {
            if (notificationList.size() > 0) {
                binding.noData.setVisibility(View.GONE);
                adapter.setData(notificationList);
            } else {
                binding.noData.setVisibility(View.VISIBLE);
            }
            binding.progressBar.setVisibility(View.GONE);
        });
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

        /// menyimpan settingan on / off notifikasi
        SharedPreferences prefs = getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        boolean isNotificationEnable = prefs.getBoolean("notification", false);
        binding.switchBtn.setChecked(isNotificationEnable);
        binding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    editor.putBoolean("notification", true);
                    Toast.makeText(getActivity(), "Notifikasi Dinyalakan", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("notification", false);
                    Toast.makeText(getActivity(), "Notifikasi Dimatikan", Toast.LENGTH_SHORT).show();
                }
                editor.apply();
            }
        });


        /// Hapus notifikasi
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        /// refresh riwayat notifikasi
        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRecyclerView();
                initViewModel();
            }
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Konfirmasi Menghapus Notifikasi")
                .setIcon(R.drawable.ic_baseline_delete_24)
                .setCancelable(false)
                .setMessage("Apa anda yakin ingin menghapus seluruh riwayat notifikasi ini ?")
                .setPositiveButton("YA", (dialogDismiss, i) -> {
                   deleteNotification();
                    dialogDismiss.dismiss();
                })
                .setNegativeButton("TIDAK", null)
                .show();
    }


    /// fungsi untuk hapus notifikasi
    private void deleteNotification() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("notification");
        ref.removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                initRecyclerView();
                initViewModel();
                Toast.makeText(getActivity(), "Berhasil menghapus riwayat notifikasi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Gagal menghapus riwayat notifikasi", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(requireContext(), HelpActivity.class));
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), AboutActivity.class));
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