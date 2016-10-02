package com.example.admin.cyclecalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SettingsFragment extends Fragment {
    EditText username;
    EditText cycledays;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        username = (EditText) view.findViewById(R.id.username);
        cycledays = (EditText) view.findViewById(R.id.imagecycledaystext);

        if(CycleCalendarLibrary.getName(this.getContext())!= "") {
            username.setText(CycleCalendarLibrary.getName(this.getContext()));
            cycledays.setText(CycleCalendarLibrary.getCycle(getContext()));
        }

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(CycleCalendarLibrary.getName(this.getContext()) != "") {
            CycleCalendarLibrary.saveName(username.getText().toString(),this.getContext());
        }
    }
}
