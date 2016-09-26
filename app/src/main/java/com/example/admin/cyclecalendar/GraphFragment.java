package com.example.admin.cyclecalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;

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
        barEntries.add(new BarEntry(1,0));
        barEntries.add(new BarEntry(1,1));
        barEntries.add(new BarEntry(1,2));
        barEntries.add(new BarEntry(2,3));
        barEntries.add(new BarEntry(2,4));
        barEntries.add(new BarEntry(3,5));
        barEntries.add(new BarEntry(3,6));
        barEntries.add(new BarEntry(3,7));
        barEntries.add(new BarEntry(2,8));
        barEntries.add(new BarEntry(2,9));
        barEntries.add(new BarEntry(1,10));
        barEntries.add(new BarEntry(1,11));
        barEntries.add(new BarEntry(1,12));
        BarDataSet barDataSet = new BarDataSet(barEntries,"The Dates");


        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("09/01/2016");
        theDates.add("09/02/2016");
        theDates.add("09/03/2016");
        theDates.add("09/04/2016");
        theDates.add("09/05/2016");
        theDates.add("09/06/2016");
        theDates.add("09/07/2016");
        theDates.add("09/08/2016");
        theDates.add("09/09/2016");
        theDates.add("09/10/2016");
        theDates.add("09/11/2016");
        theDates.add("09/12/2016");
        theDates.add("09/13/2016");

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
