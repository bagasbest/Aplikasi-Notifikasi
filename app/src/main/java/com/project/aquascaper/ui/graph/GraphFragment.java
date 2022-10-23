package com.project.aquascaper.ui.graph;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.project.aquascaper.R;
import com.project.aquascaper.databinding.FragmentGraphBinding;
import com.project.aquascaper.ui.home.AboutActivity;
import com.project.aquascaper.ui.home.HelpActivity;

import java.util.ArrayList;
import java.util.Objects;

public class GraphFragment extends Fragment {

    private FragmentGraphBinding binding;
    private BarChart barChart;
    private String paramName;
    private ArrayList<GraphModel> graphList;
    private int totalWeek = 10;

    /// week init
    private int week1 = 0;
    private int week2 = 0;
    private int week3 = 0;
    private int week4 = 0;
    private int week5 = 0;
    private int week6 = 0;
    private int week7 = 0;
    private int week8 = 0;
    private int week9 = 0;
    private int week10 = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        barChart = binding.barChartView;

        return binding.getRoot();
    }

    private void initViewModel() {
        GraphViewModel viewModel = new ViewModelProvider(this).get(GraphViewModel.class);

        viewModel.setGraph();
        viewModel.getGraphic().observe(getViewLifecycleOwner(), notificationList -> {
            Log.e("Adasada", String.valueOf(notificationList.size()));

            if (notificationList.size() > 0) {
                binding.noData.setVisibility(View.GONE);
                binding.graphWrapper.setVisibility(View.VISIBLE);
                graphList = new ArrayList<>();
                graphList.addAll(notificationList);

                Log.e("Adasada", String.valueOf(graphList.size()));


                initBarChart();
                showBarChart();
            } else {
                binding.noData.setText("Tidak Ada Data");
                binding.noData.setVisibility(View.VISIBLE);
                binding.graphWrapper.setVisibility(View.GONE);
            }
        });
    }

    private void initBarChart() {
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart.animateX(1000);

        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barChart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);
    }

    private void showBarChart() {
        week1 = 0;
        week2 = 0;
        week3 = 0;
        week4 = 0;
        week5 = 0;
        week6 = 0;
        week7 = 0;
        week8 = 0;
        week9 = 0;
        week10 = 0;

        ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Minggu ke: ";



        for(int i=0; i<graphList.size(); i++) {
            if(Objects.equals(graphList.get(i).getParameter(), paramName)){
                switch (graphList.get(i).getWeek()) {
                    case 1:
                        week1++;
                        break;
                    case 2:
                        week2++;
                        break;
                    case 3:
                        week3++;
                        break;
                    case 4:
                        week4++;
                        break;
                    case 5:
                        week5++;
                        break;
                    case 6:
                        week6++;
                        break;
                    case 7:
                        week7++;
                        break;
                    case 8:
                        week8++;
                        break;
                    case 9:
                        week9++;
                        break;
                    case 10:
                        week10++;
                        break;
                }
            }
        }

        //input data
        for(int i = 0; i < totalWeek; i++){
            switch (i) {
                case 0:
                    valueList.add((double) week1);
                    break;
                case 1:
                    valueList.add((double) week2);
                    break;
                case 2:
                    valueList.add((double) week3);
                    break;
                case 3:
                    valueList.add((double) week4);
                    break;
                case 4:
                    valueList.add((double) week5);
                    break;
                case 5:
                    valueList.add((double) week6);
                    break;
                case 6:
                    valueList.add((double) week7);
                    break;
                case 7:
                    valueList.add((double) week8);
                    break;
                case 8:
                    valueList.add((double) week9);
                    break;
                case 9:
                    valueList.add((double) week10);
                    break;
            }
        }

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i+1, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        initBarDataSet(barDataSet);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String week = String.valueOf(Float.valueOf(e.getX()).intValue());

                ArrayList<GraphModel> newList = new ArrayList<>();
                for(int i=0; i<graphList.size(); i++) {
                    int weekDB = graphList.get(i).getWeek();
                    String paramDB = graphList.get(i).getParameter();
                    if((weekDB == Float.valueOf(e.getX()).intValue()) && (Objects.equals(paramDB, paramName))){
                        newList.add(graphList.get(i));
                    }
                }


                Intent intent = new Intent(getActivity(), ChartActivity.class);
                intent.putExtra(ChartActivity.EXTRA_WEEK, Float.valueOf(e.getX()).intValue());
                intent.putExtra(ChartActivity.EXTRA_PARAM, paramName);
                intent.putExtra(ChartActivity.EXTRA_DATA, newList);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void initBarDataSet(BarDataSet barDataSet) {
        //Changing the color of the bar
        barDataSet.setColor(Color.parseColor("#304567"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(16f);
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



    }

    private void showOptionMenu() {
        TextView temperature, pH, turbidity, ppm;
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_menu_option);

        temperature = dialog.findViewById(R.id.about);
        pH  = dialog.findViewById(R.id.help);
        turbidity = dialog.findViewById(R.id.menu3);
        ppm = dialog.findViewById(R.id.menu4);

        turbidity.setVisibility(View.VISIBLE);
        ppm.setVisibility(View.VISIBLE);


        temperature.setText("Suhu");
        pH.setText("pH");
        turbidity.setText("Kekeruhan");
        ppm.setText("PPM/CO2");
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paramName = "Suhu";
                initViewModel();
                binding.paramName.setText("Total Notifikasi " + paramName);
                binding.graphWrapper.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });


        pH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paramName = "pH";
                initViewModel();
                binding.paramName.setText("Total Notifikasi " + paramName);
                binding.graphWrapper.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        turbidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paramName = "Kekeruhan";
                initViewModel();
                binding.paramName.setText("Total Notifikasi " + paramName);
                binding.graphWrapper.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        ppm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paramName = "PPM/CO2";
                initViewModel();
                binding.paramName.setText("Total Notifikasi " + paramName);
                binding.graphWrapper.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}