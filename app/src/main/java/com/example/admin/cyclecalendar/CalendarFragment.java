package com.example.admin.cyclecalendar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    SimpleDateFormat sdf;
    String ovulationDay;
    String firstDay;


    TextView first;
    TextView ovulation;
    TextView fertile;

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
        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

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
                Log.e("DATES:",""+date);
                intent.putExtra("CurrentDate", date.getTime());
                startActivity(intent);
                List<DayDecorator> decorators = new ArrayList<>();
                decorators.add(new DaysDecorator());
                calendarView.setDecorators(decorators);
                calendarView.refreshCalendar(currentCalendar);
            }

            @Override
            public void onMonthChanged(Date date) {
                // do nothing
            }
        });

        List<DayDecorator> decorators = new ArrayList<>();
        decorators.add(new DaysDecorator());
        calendarView.setDecorators(decorators);
        calendarView.refreshCalendar(currentCalendar);
        return view;
    }

    private class DaysDecorator implements DayDecorator {
        @Override
        public void decorate(DayView dayView) {
            CycleCalendarLibrary cyc = new CycleCalendarLibrary();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Date[] myDate = cyc.initializeDates(getContext());
            int[] myInt = cyc.initializeChart(getContext());
            for(int i=0;i<myDate.length;i++) {
                if(myInt[i] == 0) {
                    cal1.setTime(dayView.getDate());
                    cal2.setTime(myDate[i]);

                    boolean sameDays = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                    if(sameDays) {
                        dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_firstday));
                        dayView.setTextColor(Color.BLACK);
                    }

                    cal2.add(Calendar.DATE,9);
                    for(int j=1; j<8;j++) {
                        cal1.setTime(dayView.getDate());

                        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                        if(sameDay) {
                            if(j==5) {
                                dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_ovulation));
                                dayView.setTextColor(Color.WHITE);
                            }
                            else {
                                dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_fertile));
                                dayView.setTextColor(Color.WHITE);
                            }
                        }

                        cal2.add(Calendar.DATE,1);
                    }



                }


                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                if(sameDay) {
                    dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_fertile));
                    dayView.setTextColor(Color.WHITE);
                }
            }

   /*         try {
                // FERTILE DAYS
                for(int i=0;i<fertileDays.size();i++) {
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(sdf.parse(fertileDays.get(i)));
                    cal2.setTime(dayView.getDate());
                    boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                    if(sameDay) {
                        dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_fertile));
                        dayView.setTextColor(Color.WHITE);
                    }
                }

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Calendar cal3 = Calendar.getInstance();
                cal1.setTime(sdf.parse(ovulationDay));
                cal2.setTime(dayView.getDate());
                cal3.setTime(sdf.parse(firstDay));

                //OVULATION DAY
                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                if(sameDay) {
                    dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_ovulation));
                    dayView.setTextColor(Color.WHITE);
                }

                //FIRST DAY
                sameDay = cal3.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal3.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                if(sameDay) {
                    dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_firstday));
                    dayView.setTextColor(Color.BLACK);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }
    }
}