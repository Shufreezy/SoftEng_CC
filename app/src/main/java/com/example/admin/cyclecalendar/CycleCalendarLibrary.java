package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * This class is for the static methods used in the Cycle Calendar project
 * v1.0 - initial database handling
 */
public class CycleCalendarLibrary {

    /**
     * CFG filename
     */
    public static final String CONFIG = "cyclecalendar_config.txt";

    /**
     * CFG filename
     */
    public static final String CONFIG_NAME = "cyclecalendar_config_name.txt";

    /**
     * Android format standard
     */
    public static final String FORMAT = "UTF-8";

    /**
     * This method is for saving name to the local storage initially
     */
    public static void saveName(String name, Context context) {
        File path = context.getFilesDir();
        File file = new File(path, CONFIG_NAME);

        //empties database
        try {
            new PrintWriter(file).close();
            Files.write(name, file, Charset.forName(FORMAT));
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }
    }

    /**
     * This method is for changing the auto-cycle settings
     */
    public static void initializeCycle(int cycle, Context context) {
        File path = context.getFilesDir();
        File file = new File(path, CONFIG_NAME);
        try {
            new PrintWriter(file).close();
            Files.write(String.valueOf(cycle), file, Charset.forName(FORMAT));
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }
    }

    /**
     * This method is for getting the auto-cycle settings
     */
    public static int getCycle(Context context) {
        File path = context.getFilesDir();
        File file = new File(path, CONFIG_NAME);
        String cycle = "28";
        try {
            cycle = Files.toString(file, Charset.forName(FORMAT));
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }
        return Integer.parseInt(cycle);
    }


    /**
     * This method is for saving data to the local storage
     */
    public static void saveData(Date[] periodDate, int[] periodChart, Context context) {
        DatabaseAttributes pa = new DatabaseAttributes();
        File path = context.getFilesDir();
        File file = new File(path, CONFIG);

        //empties database
        try {
            new PrintWriter(file).close();
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        pa.periodDate = periodDate;
        pa.periodChart = periodChart;

        try {
            Files.write(gson.toJson(pa), file, Charset.forName(FORMAT));
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }
    }

    /**
     * Initializes dates that have periods
     */
    public static Date[] initializeDates(Context context) {
        Gson gson = new Gson();
        File path = context.getFilesDir();
        File file = new File(path, CONFIG);
        DatabaseAttributes da = null;
        try {
            String contents = Files.toString(file, Charset.forName(FORMAT));
            da = gson.fromJson(contents, DatabaseAttributes.class);
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }

        return da.periodDate;
    }

    /**
     * Initializes days that have periods
     */
    public static int[] initializeChart(Context context) {
        Gson gson = new Gson();
        File path = context.getFilesDir();
        File file = new File(path, CONFIG);
        DatabaseAttributes da = null;
        try {
            String contents = Files.toString(file, Charset.forName(FORMAT));
            da = gson.fromJson(contents, DatabaseAttributes.class);
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }
        return da.periodChart;
    }

    /**
     * gets the name from the database
     * if name == null then do saveName method
     */

    public static String getName(Context context) {
        File path = context.getFilesDir();
        File file = new File(path, CONFIG_NAME);
        try {
            return Files.toString(file, Charset.forName(FORMAT));
        } catch (IOException e) {
            Log.e(null, "I/O error: File manipulation error");
        }
        return null;
    }

    /**
     * set alarm
     */
    public static void setAlarm(Date currentDate, Date predictionDate, Activity activity) {

        long diffInMillies = predictionDate.getTime() - currentDate.getTime();

        AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(activity, ScheduledService.class);
        PendingIntent pi = PendingIntent.getService(activity, 0, i, 0);
        mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + diffInMillies, pi);
    }

    /**
     * cancel alarm if there is a new first day (off schedule)
     */
    public static void cancelAlarm(Activity activity) {
        AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(activity, ScheduledService.class);
        PendingIntent pi = PendingIntent.getService(activity, 0, i, 0);
        if (mgr != null)
            mgr.cancel(pi);
    }

}

/**
 * database values
 */
class DatabaseAttributes {
    //for calendar
    public Date[] periodDate;
    //for graph
    public int[] periodChart;
}

