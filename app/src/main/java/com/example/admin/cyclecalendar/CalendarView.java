package com.example.admin.cyclecalendar;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Princess Lykken on 9/21/2016.
 */
public class CalendarView extends Fragment {
    CustomCalendarView calendarView;

    public CalendarView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendarview, container, false);
        // Inflate the layout for this fragment
        //Initialize CustomCalendarView from layout

        calendarView = (CustomCalendarView) view.findViewById(R.id.calendar_view);

        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);
        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {

                Intent intent = new Intent(CalendarView.this.getActivity(), PopUpLayoutDetail.class);
                startActivity(intent);

          //        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
             //      Toast.makeText(CalendarView.this.getActivity(), df.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthChanged(Date date) {
                // do nothing
            }
        });

        return view;
    }

}