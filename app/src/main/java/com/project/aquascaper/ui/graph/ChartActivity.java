package com.project.aquascaper.ui.graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.project.aquascaper.databinding.ActivityChartBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class ChartActivity extends AppCompatActivity {

    public static final String EXTRA_WEEK = "week";
    public static final String EXTRA_PARAM = "param";
    public static final String EXTRA_DATA = "data";
    private ActivityChartBinding binding;
    private BarChart barChart;
    private String paramName;
    private int week;

    private ArrayList<GraphModel> graphList;

    /// day init
    private double senin = 0;
    private double selasa = 0;
    private double rabu = 0;
    private double kamis = 0;
    private double jumat = 0;
    private double sabtu = 0;
    private double minggu = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        barChart = binding.barChartView;

        graphList = getIntent().getParcelableArrayListExtra(EXTRA_DATA);
        paramName = getIntent().getStringExtra(EXTRA_PARAM);
        week = getIntent().getIntExtra(EXTRA_WEEK, 0);

        initBarChart();
        showBarChart();

        binding.paramName.setText("Parameter " + paramName + " di minggu ke " + week);



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


        ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Senin");
        xAxisLabel.add("Selasa");
        xAxisLabel.add("Rabu");
        xAxisLabel.add("Kamis");
        xAxisLabel.add("Jumat");
        xAxisLabel.add("Sabtu");
        xAxisLabel.add("Minggu");

        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setLabelCount(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
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
        senin = 0;
        selasa = 0;
        rabu = 0;
        kamis = 0;
        jumat = 0;
        sabtu = 0;
        minggu = 0;

        ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Hari : ";

        for(int i=0; i<graphList.size(); i++) {
            if(Objects.equals(graphList.get(i).getParameter(), paramName) && Objects.equals(graphList.get(i).getWeek(), week)){
                switch (graphList.get(i).getDayOfWeek()) {
                    case "Senin":
                        senin = graphList.get(i).getValue();
                        break;
                    case "Selasa":
                        selasa = graphList.get(i).getValue();
                        break;
                    case "Rabu":
                        rabu = graphList.get(i).getValue();
                        break;
                    case "Kamis":
                        kamis = graphList.get(i).getValue();
                        break;
                    case "Jumat":
                        jumat = graphList.get(i).getValue();
                        break;
                    case "Sabtu":
                        sabtu = graphList.get(i).getValue();
                        break;
                    case "Minggu":
                        minggu = graphList.get(i).getValue();
                        break;
                }
            }
        }

        //input data
        for(int i = 0; i < 7; i++){
            switch (i) {
                case 0:
                    valueList.add((double) senin);
                    break;
                case 1:
                    valueList.add((double) selasa);
                    break;
                case 2:
                    valueList.add((double) rabu);
                    break;
                case 3:
                    valueList.add((double) kamis);
                    break;
                case 4:
                    valueList.add((double) jumat);
                    break;
                case 5:
                    valueList.add((double) sabtu);
                    break;
                case 6:
                    valueList.add((double) minggu);
                    break;
            }
        }

        //fit the data into a bar
        for (int i = 0; i < 7; i++) {
            Log.e("adasada", String.valueOf(i+1));
            Log.e("adasada", String.valueOf(valueList.get(i).floatValue()));
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        initBarDataSet(barDataSet);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();
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
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}