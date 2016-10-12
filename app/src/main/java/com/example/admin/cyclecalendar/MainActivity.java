package com.example.admin.cyclecalendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[]titles = {"Calendar", "Graph", "Glossary", "Settings"};
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    private Fragment fragment;
    PendingIntent pendingIntent;
    public PendingIntent activity;
    AlarmManager manager;
    public AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("Calendar", R.drawable.ic_calendar));
        listViewItems.add(new ItemObject("Graph", R.drawable.ic_chart));
        listViewItems.add(new ItemObject("Glossary", R.drawable.ic_glossary));
        listViewItems.add(new ItemObject("Settings", R.drawable.ic_settings));

        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                selectItemFragment(position);
            }
        });

        loadInitialFragment();
    }

    public void selectItemFragment(int position){

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            default:
            case 0:
                fragment = new CalendarFragment();
                break;
            case 1:
                fragment = new GraphFragment();
                break;
            case 2:
                fragment = new GlossaryFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            loadInitialFragment();
        }

        if(requestCode == 3) {

        }
    }

    // Load Calendar fragment
    private void loadInitialFragment() {
        // Initial Fragment
        fragment = new CalendarFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
        mDrawerList.setItemChecked(0, true);
        setTitle(titles[0]);
        mDrawerLayout.closeDrawer(mDrawerList);
        generateNextPrediction();
        Log.e("GENERATE","NEXT PREDICTION");
    }



    //GENERATE NEXT PREDICTION

    public void generateNextPrediction() {
        if(CycleCalendarLibrary.fileExist(getApplicationContext())) {
            Date predictionDate = new Date();
            Date currentDate = new Date();
            Date[] myDates = CycleCalendarLibrary.initializeDates(getApplicationContext());
            int[] keys = CycleCalendarLibrary.initializeChart(getApplicationContext());
            for (int i = myDates.length - 1; i >= 0; i--) {
                if (keys[i] == 0) {
                    predictionDate = myDates[i];
                    break;
                }
            }
            Calendar current = Calendar.getInstance();
            current.set(Calendar.HOUR_OF_DAY,7);
            current.set(Calendar.MINUTE,0);
            current.set(Calendar.SECOND, 0);
            current.set(Calendar.MILLISECOND, 0);
            Calendar prediction = Calendar.getInstance();
            prediction.setTime(predictionDate);
            prediction.add(Calendar.DATE, 13);
            prediction.set(Calendar.HOUR_OF_DAY, 7);
            prediction.set(Calendar.MINUTE, 0);
            prediction.set(Calendar.SECOND, 0);
            prediction.set(Calendar.MILLISECOND, 0);

            long diffInMillies = prediction.getTimeInMillis() - current.getTimeInMillis();
            GenerateNextPrediction(diffInMillies);
        }
    }

    public void GenerateNextAlarm(Context context, long delay, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Cycle Calendar")
                .setContentText("Your period begins in about 3 days.")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_report)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_report)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, MainActivity.class);
        activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, com.example.admin.cyclecalendar.Notification.class);
        notificationIntent.putExtra(com.example.admin.cyclecalendar.Notification.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(com.example.admin.cyclecalendar.Notification.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, 7);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        long futureInMillis = current.getTimeInMillis() + delay;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);

        Log.e("HELlO", "ALARM SET");
    }

    public void GenerateNextPrediction(long delay) {
        Intent alarmIntent = new Intent(this, Prediction.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, 7);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        long futureInMillis = current.getTimeInMillis() + delay;

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    //Set Notification
    public void setAlarm(Context context) {
        if(CycleCalendarLibrary.fileExist(context)) {
            Date predictionDate = new Date();
            Date currentDate = new Date();
            Date[] myDates = CycleCalendarLibrary.initializeDates(context);
            int[] keys = CycleCalendarLibrary.initializeChart(context);
            for (int i = myDates.length - 1; i >= 0; i--) {
                if (keys[i] == 0) {
                    predictionDate = myDates[i];
                    break;
                }
            }

            Calendar current = Calendar.getInstance();
            current.set(Calendar.HOUR_OF_DAY, 7);
            current.set(Calendar.MINUTE, 0);
            current.set(Calendar.SECOND, 0);
            current.set(Calendar.MILLISECOND, 0);
            currentDate = current.getTime();

            Calendar c = Calendar.getInstance();
            c.setTime(predictionDate);
            c.add(Calendar.DATE, -3);
            c.set(Calendar.HOUR_OF_DAY,7);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            predictionDate = c.getTime();
            long diffInMillies = c.getTimeInMillis() - current.getTimeInMillis();
            GenerateNextAlarm(context, diffInMillies, 1);
        }
    }
}