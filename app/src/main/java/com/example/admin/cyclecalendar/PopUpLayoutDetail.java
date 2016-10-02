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

import com.google.common.primitives.Ints;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        final Date dateObj = new Date(getIntent().getExtras().getLong("CurrentDate", -1));
        Log.e("DATE: ", dateObj + "");
        light =(RadioButton)findViewById(R.id.light);
        moderate =(RadioButton)findViewById(R.id.moderate);
        heavy =(RadioButton)findViewById(R.id.heavy);
        btnSave= (Button)findViewById(R.id.save);
        light.setChecked(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Date> flowDate = new ArrayList<Date>(Arrays.asList(CycleCalendarLibrary.initializeDates(getApplicationContext())));
                List<Integer> flowType = Ints.asList(CycleCalendarLibrary.initializeChart(getApplicationContext()));
                Log.e("NUM: ",""+flowType.size());

               flowDate.add(dateObj);
                if(light.isChecked())
                    flowType.add(1);
                else if(moderate.isChecked())
                    flowType.add(2);
                else
                    flowType.add(3);

                for(int i=0;i<flowType.size();i++) {
                    Log.e("CHECK: ", "" + flowType.get(i));
                }
            //    Date[] Dates = flowDate.toArray(new Date[flowDate.size()]);
            //    int[] keys = {0,0,1,2,3};
           //     CycleCalendarLibrary.saveData(Dates,keys,getApplicationContext());
                Log.e("CLICKED","SAVED");
            }
        });

    }
}
