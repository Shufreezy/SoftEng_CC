package com.example.admin.cyclecalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class GraphFragment extends Fragment {

    BarChart barChart;

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
        ArrayList<GraphItem> graphItems = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        int[] keys = CycleCalendarLibrary.initializeChart(this.getContext());
        Date[] Dates = CycleCalendarLibrary.initializeDates(this.getContext());

        //Add to Custom Class for GraphItems
        for(int i=0;i< Dates.length;i++) {
            if(keys[i] == 1 || keys[i] == 2|| keys[i] ==3) {
                graphItems.add(new GraphItem(Dates[i],keys[i]));
            }
        }

        //Sort GraphItems according to Dates
        Collections.sort(graphItems, new Comparator<GraphItem>() {
            @Override
            public int compare(GraphItem o1, GraphItem o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        // Load Date to Graph
        int counter=0;
        for(int j=0;j<graphItems.size();j++) {
            barEntries.add(new BarEntry(graphItems.get(j).getFlowType(),counter));
            theDates.add(dateFormat.format(graphItems.get(j).getDate()));
            counter++;
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
