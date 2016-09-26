package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//Pop-Up Intent for Clicking the Days
public class PopUpLayoutDetail extends Activity {
    RadioGroup radioGroup;
    Button btnSave;
    RadioButton light;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.popup_layout_daydetail);

        light =(RadioButton)findViewById(R.id.light);
        light.setChecked(true);
    }
}
