package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

//Pop-Up Intent for Clicking the Days
public class PopUpLayoutDetail extends Activity {
    RadioGroup radioGroup;
    Button btnSave;
    RadioButton light;
    RadioButton moderate;
    RadioButton heavy;
    Date currentDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout_daydetail);


        Bundle extras = getIntent().getExtras();
        Log.e("DATE",""+extras.getString("CurrentDate"));
        light =(RadioButton)findViewById(R.id.light);
        moderate =(RadioButton)findViewById(R.id.moderate);
        heavy =(RadioButton)findViewById(R.id.heavy);
        btnSave= (Button)findViewById(R.id.save);
        light.setChecked(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Date> flow = new ArrayList<Date>(Arrays.asList(CycleCalendarLibrary.initializeDates(getApplicationContext())));
            }
        });

    }
}
