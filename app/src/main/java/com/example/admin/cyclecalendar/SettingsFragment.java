package com.example.admin.cyclecalendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

    boolean error;
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

        cycledays.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 0) {
                    cycledays.setError("Please enter cycle days");
                    error = true;
                }
                else {
                    if (Integer.parseInt(s.toString()) < 14) {
                        cycledays.setError("Cycle days must be between 14 to 100 days.");
                        error = true;
                    }
                    else if (Integer.parseInt(s.toString()) > 100) {
                        cycledays.setError("Cycle days must be between 14 to 100 days.");
                        error = true;
                    }
                    else {
                        if (Integer.parseInt(s.toString()) == 28) {
                            autocycle.setChecked(false);
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("AutoCycleSwitch", Context.MODE_PRIVATE).edit();
                            editor.putBoolean("AutoCycleToggle", false);
                            editor.commit();
                            error = false;
                        } else {
                            autocycle.setChecked(true);
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("AutoCycleSwitch", Context.MODE_PRIVATE).edit();
                            editor.putBoolean("AutoCycleToggle", true);
                            editor.commit();
                            error = false;
                        }
                    }
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(!error) {
            if (CycleCalendarLibrary.getCycle(getContext()) != -1) {
                CycleCalendarLibrary.initializeCycle(Integer.parseInt(cycledays.getText().toString()), getContext());
            }
        }
    }
}
