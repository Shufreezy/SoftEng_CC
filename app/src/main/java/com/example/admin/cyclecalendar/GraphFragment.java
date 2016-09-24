package com.example.admin.cyclecalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Princess Lykken on 9/23/2016.
 */
public class GraphFragment extends Fragment {

    BarChart barChart;

    ArrayList<String> dates;
    Random random;
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
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(5f,1));
        barEntries.add(new BarEntry(25f,2));
        barEntries.add(new BarEntry(15f,3));
        barEntries.add(new BarEntry(52f,4));
        BarDataSet barDataSet = new BarDataSet(barEntries,"The Dates");


        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");
        theDates.add("September");
        theDates.add("October");

        BarData theData = new BarData(theDates,barDataSet);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);


        return view;
    }

}
