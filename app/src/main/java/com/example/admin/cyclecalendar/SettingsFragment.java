package com.example.admin.cyclecalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
    EditText username;
    EditText cycledays;
    Switch autocycle;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        username = (EditText) view.findViewById(R.id.username);
        cycledays = (EditText) view.findViewById(R.id.imagecycledaystext);
        autocycle = (Switch) view.findViewById(R.id.cycletoggle);
        if(CycleCalendarLibrary.getCycle(getContext())!= -1) {
            cycledays.setText("" + CycleCalendarLibrary.getCycle(getContext()));
            if(CycleCalendarLibrary.getCycle(getContext())!= 28) {
                autocycle.setChecked(true);
            }
        }

        autocycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cycledays.setEnabled(true);
                }
                else {
                    cycledays.setText(28+"");
                    cycledays.setEnabled(false);
                }
            }
        });

        return view;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(CycleCalendarLibrary.getCycle(getContext())!= -1) {
            CycleCalendarLibrary.initializeCycle(Integer.parseInt(cycledays.getText().toString()), getContext());
        }
    }
}
