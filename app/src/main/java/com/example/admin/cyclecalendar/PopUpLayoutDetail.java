package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    Button btnSetFirst;
    RadioButton light;
    RadioButton moderate;
    RadioButton heavy;

    DateFormat dateFormat;
    ArrayList<Date> flowDate;
    List<Integer> flowType;

    int index;
    boolean set;

    Date currentDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout_daydetail);

        // Initialize views
        light =(RadioButton)findViewById(R.id.light);
        moderate =(RadioButton)findViewById(R.id.moderate);
        heavy =(RadioButton)findViewById(R.id.heavy);
        btnSave= (Button)findViewById(R.id.save);
        btnSetFirst = (Button) findViewById(R.id.firstday);
        light.setChecked(true);
        index = -1;
        set = false;

        // Initialize values
        final Date dateObj = new Date(getIntent().getExtras().getLong("CurrentDate", -1));
        final Date curr = dateObj;
        currentDate = curr;
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
                // set new start date
                if(set) {
                    for (int i = flowType.size() - 1; i >= 0; i--) {
                        if (flowType.get(i) == 0) {
                            flowType.remove(i);
                            flowDate.remove(i);
                            flowDate.add(dateObj);
                            flowType.add(0);
                            break;
                        }
                    }
                }

                int type;
                if(light.isChecked())
                    type = 1;
                else if(moderate.isChecked())
                    type = 2;
                else
                    type = 3;

                // overwrite previous data if date exist
                if(index != -1) {
                    flowType.set(index,type);
                }
                else {
                    flowDate.add(dateObj);
                    flowType.add(type);
                }

                Date[] Dates = flowDate.toArray(new Date[flowDate.size()]);
                int[] keys = Ints.toArray(flowType);
                CycleCalendarLibrary.saveData(Dates, keys, getApplicationContext());
                Intent intent = new Intent();
                intent.putExtra("CurrentDate",curr.getTime());
                Log.e("TEST", curr.getTime()+"");
                setResult(2, intent);
                finish();//finishing activity
            }
        });

        btnSetFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set) {
                    set = false;
                    btnSetFirst.setBackgroundColor(Color.parseColor("#C71B1B"));
                    btnSetFirst.setTextColor(Color.WHITE);
                }
                else {
                    set = true;
                    btnSetFirst.setBackgroundColor(Color.parseColor("#78DDBB"));
                    btnSetFirst.setTextColor(Color.WHITE);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("CurrentDate",currentDate.getTime());
        Log.e("TEST", currentDate.getTime()+"");
        setResult(2, intent);
        finish();//finishing activity
    }
}
