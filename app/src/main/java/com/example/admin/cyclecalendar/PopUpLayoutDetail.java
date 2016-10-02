package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import com.google.common.primitives.Ints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//Pop-Up Intent for Clicking the Days
public class PopUpLayoutDetail extends Activity {
    Button btnSave;
    RadioButton light;
    RadioButton moderate;
    RadioButton heavy;

    DateFormat dateFormat;
    ArrayList<Date> flowDate;
    List<Integer> flowType;

    int index;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout_daydetail);

        // Initialize views
        light =(RadioButton)findViewById(R.id.light);
        moderate =(RadioButton)findViewById(R.id.moderate);
        heavy =(RadioButton)findViewById(R.id.heavy);
        btnSave= (Button)findViewById(R.id.save);
        light.setChecked(true);
        index = -1;

        // Initialize values
        final Date dateObj = new Date(getIntent().getExtras().getLong("CurrentDate", -1));
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        flowDate = new ArrayList<Date>(Arrays.asList(CycleCalendarLibrary.initializeDates(getApplicationContext())));
        flowType = new ArrayList<Integer>(Ints.asList(CycleCalendarLibrary.initializeChart(getApplicationContext())));

        // Load flow type if previous date is loaded
        for(int i=0;i<flowDate.size();i++) {
            int key = flowType.get(i);
            if(key == 1 || key == 2 || key ==3 ) {
                if(dateFormat.format(flowDate.get(i)).equals(dateFormat.format(dateObj))) {
                    index = i;
                    if(key == 1)
                        light.setChecked(true);
                    else if(key == 2)
                        moderate.setChecked(true);
                    else
                        heavy.setChecked(true);
                    break;
                }
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type;
                if(light.isChecked())
                    type = 1;
                else if(moderate.isChecked())
                    type = 2;
                else
                    type = 3;

                if(index != -1) {
                    flowType.set(index,type);
                }
                else {
                    flowDate.add(dateObj);
                    flowType.add(type);
                }

                Date[] Dates = flowDate.toArray(new Date[flowDate.size()]);
                int[] keys = Ints.toArray(flowType);
                CycleCalendarLibrary.saveData(Dates,keys,getApplicationContext());
                finish();
            }
        });

    }
}
