package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                CycleCalendarLibrary.saveName(name.getText().toString(), getApplicationContext());
                CycleCalendarLibrary.initializeCycle(Integer.parseInt(cycledays.getText().toString()), getApplicationContext());

                ArrayList<Date> myDate = new ArrayList<>();
                List<Integer> myKeys = new ArrayList<>();
                Date firstDateLastMens = new GregorianCalendar(year, month, day).getTime();
                Calendar c = Calendar.getInstance();
                c.setTime(firstDateLastMens);
                c.add(Calendar.DATE, 30);
                firstDateLastMens = c.getTime();
                myDate.add(firstDateLastMens);
                myKeys.add(0);

                Date[] Dates = myDate.toArray(new Date[myDate.size()]);
                int[] keys = Ints.toArray(myKeys);

                CycleCalendarLibrary.saveData(Dates, keys, getApplicationContext());
                Intent intent = new Intent();
                setResult(2, intent);
                finish();//finishing activity
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
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

        if(name.getText().toString() == "") {
            flag = false;
        }
        if(cycledays.getText().toString() == "") {
            flag = false;
        }
        if(date.getText().toString() == "") {
            flag = false;
        }

        return flag;
    }

}