package com.example.admin.cyclecalendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

        SharedPreferences sharedpref = getActivity().getSharedPreferences("AutoCycleSwitch", Context.MODE_PRIVATE);
        autocycle.setChecked(sharedpref.getBoolean("AutoCycleToggle", true));

        if(CycleCalendarLibrary.getCycle(getContext())!= -1) {
            cycledays.setText("" + CycleCalendarLibrary.getCycle(getContext()));
        }

        autocycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cycledays.setEnabled(true);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("AutoCycleSwitch", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("AutoCycleToggle", true);
                    editor.commit();
                }
                else {
                    cycledays.setText(28+"");
                    cycledays.setEnabled(false);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("AutoCycleSwitch", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("AutoCycleToggle", false);
                    editor.commit();
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
