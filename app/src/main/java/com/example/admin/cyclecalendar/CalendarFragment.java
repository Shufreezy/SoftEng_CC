package com.example.admin.cyclecalendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    CustomCalendarView calendarView;
    ArrayList<String> fertileDays;
    String ovulationDay;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

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
                Intent intent = new Intent(CalendarFragment.this.getActivity(), PopUpLayoutDetail.class);
                startActivity(intent);
            }

            @Override
            public void onMonthChanged(Date date) {
                // do nothing
            }
        });

        loadDates();
        List<DayDecorator> decorators = new ArrayList<>();
        decorators.add(new DaysDecorator());
        calendarView.setDecorators(decorators);
        calendarView.refreshCalendar(currentCalendar);

        return view;
    }

    private class DaysDecorator implements DayDecorator {
        @Override
        public void decorate(DayView dayView) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                // Fertile Days
                for(int i=0;i<fertileDays.size();i++) {
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(sdf.parse(fertileDays.get(i)));
                    cal2.setTime(dayView.getDate());
                    boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                    if(sameDay) {
                        dayView.setBackgroundColor(Color.GRAY);
                        dayView.setTextColor(Color.WHITE);
                    }
                }

                //OVULATION DAY
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(sdf.parse(ovulationDay));
                cal2.setTime(dayView.getDate());
                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                if(sameDay) {
                    dayView.setBackgroundColor(Color.BLUE);
                    dayView.setTextColor(Color.WHITE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDates() {
        fertileDays = new ArrayList<String>();
        fertileDays.add("07/09/2016");
        fertileDays.add("08/09/2016");
        fertileDays.add("09/09/2016");
        fertileDays.add("10/9/2016");
        fertileDays.add("11/9/2016");
        fertileDays.add("12/9/2016");
        fertileDays.add("13/9/2016");
        fertileDays.add("14/9/2016");
        fertileDays.add("15/9/2016");
        fertileDays.add("16/9/2016");

        ovulationDay = "13/09/2016";
    }

}