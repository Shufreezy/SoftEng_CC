package com.example.admin.cyclecalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GraphFragment extends Fragment {

    BarChart barChart;

    ArrayList<BarEntry> barEntries;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        barChart = (BarChart) view.findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> theDates = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        int[] keys = CycleCalendarLibrary.initializeChart(this.getContext());
        Date[] Dates = CycleCalendarLibrary.initializeDates(this.getContext());
        int counter = 0;
        for(int i=0;i< Dates.length;i++) {
            if(keys[i] == 1 || keys[i] == 2|| keys[i] ==3) {
                barEntries.add(new BarEntry(keys[i],counter));
                theDates.add(dateFormat.format(Dates[i]));
                counter++;
            }
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"The Dates");
        BarData theData = new BarData(theDates,barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        theData.setValueTextColor(Color.WHITE);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setValueFormatter(new MyValueFormatter());
        return view;
    }

    public class MyValueFormatter implements YAxisValueFormatter {

        String myValue;

        public MyValueFormatter() {
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            // write your logic here
            if(value == 1) {
                myValue = "Light";
            }
            else if(value == 2) {
                myValue = "Moderate";
            }
            else {
                myValue = "Heavy";
            }
            return myValue;
        }
    }
}
