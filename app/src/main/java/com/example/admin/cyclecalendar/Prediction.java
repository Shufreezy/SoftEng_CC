package com.example.admin.cyclecalendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Prediction extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // For our recurring task, we'll just display a message
        Date predictionDate = new Date();
        Date[] myDates = CycleCalendarLibrary.initializeDates(arg0);
        int[] keys = CycleCalendarLibrary.initializeChart(arg0);
        ArrayList<Date> myDate = new ArrayList<Date>(Arrays.asList(CycleCalendarLibrary.initializeDates(arg0)));
        List<Integer> myKeys = new ArrayList<Integer>(Ints.asList(CycleCalendarLibrary.initializeChart(arg0)));


        for (int i = myDates.length - 1; i >= 0; i--) {
            if (keys[i] == 0) {
                predictionDate = myDates[i];
                break;
            }
        }

        Calendar c = Calendar.getInstance();
        c.setTime(predictionDate);
        c.add(Calendar.DATE, CycleCalendarLibrary.getCycle(arg0));
        predictionDate = c.getTime();
        myDate.add(predictionDate);
        myKeys.add(0);

        Date[] Dates = myDate.toArray(new Date[myDate.size()]);
        int[] mykeys = Ints.toArray(myKeys);
        CycleCalendarLibrary.saveData(Dates, mykeys, arg0);
    }

}