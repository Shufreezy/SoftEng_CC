package com.example.admin.cyclecalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Princess Lykken on 9/21/2016.
 */
public class PopUpLayoutDayDetail extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout_daydetail);
    }
}
