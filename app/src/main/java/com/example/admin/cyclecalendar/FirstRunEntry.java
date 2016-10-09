package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FirstRunEntry extends Activity {
    private DatePicker datePicker;
    private EditText date;
    private EditText name;
    private EditText cycledays;
    Button save;

    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_run_entry);

        //Initialize views
        save = (Button) findViewById(R.id.settingsave);
        date = (EditText) findViewById(R.id.firstdatelastmens);
        date.clearFocus();
        name = (EditText) findViewById(R.id.username);
        cycledays = (EditText) findViewById(R.id.cycledays);

        //Get current date
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(errorchecking()) {
                    //Save name and cycle days to config
                    CycleCalendarLibrary.saveName(name.getText().toString(), getApplicationContext());
                    CycleCalendarLibrary.initializeCycle(Integer.parseInt(cycledays.getText().toString()), getApplicationContext());

                    ArrayList<Date> myDate = new ArrayList<>();
                    List<Integer> myKeys = new ArrayList<>();
                    Date firstDateLastMens = new GregorianCalendar(year, month, day).getTime();
                    Date first = firstDateLastMens;
                    myDate.add(first);
                    myKeys.add(0);
                    Calendar c = Calendar.getInstance();
                    c.setTime(firstDateLastMens);
                    c.add(Calendar.DATE, CycleCalendarLibrary.getCycle(getApplicationContext()));
                    firstDateLastMens = c.getTime();
                    myDate.add(firstDateLastMens);
                    myKeys.add(0);

                    Date[] Dates = myDate.toArray(new Date[myDate.size()]);
                    int[] keys = Ints.toArray(myKeys);

                    CycleCalendarLibrary.saveData(Dates, keys, getApplicationContext());
                    Intent intent = new Intent(FirstRunEntry.this, MainActivity.class);
                    startActivityForResult(intent, 2);
                }
                else {
                    Toast.makeText(FirstRunEntry.this,"PLEASE SUPPLY MISSING FIELDS",Toast.LENGTH_LONG);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            year = arg1;
            month = arg2;
            day = arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private boolean errorchecking() {
        boolean flag = true;
        String message ="";
        String scycledays = cycledays.getText().toString();
        if(scycledays.isEmpty()) {
            message = "Please enter cycle days";
            flag = false;
        }
        int ncycledays = Integer.parseInt(scycledays);
        if(ncycledays < 14) {
            message = "Cycle days must be between 14 to 100 days.";
            flag = false;
        }
        if(ncycledays > 100) {
            message = "Cycle days must be between 14 to 100 days.";
            flag = false;
        }
        if(date.getText().toString().isEmpty()) {
            message = "Please select date";
            flag = false;
        }
        if(name.getText().toString().isEmpty()) {
            message = "Please enter name";
            flag = false;
        }

        if(!flag)
            snackBarCall(message);
        return flag;
    }

    private void snackBarCall(String message) {

        Snackbar.make(findViewById(R.id.main_layout), message, Snackbar.LENGTH_LONG).setAction("YES!", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();

    }
}