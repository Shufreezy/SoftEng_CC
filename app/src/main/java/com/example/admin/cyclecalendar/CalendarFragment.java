package com.example.admin.cyclecalendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.primitives.Ints;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    CustomCalendarView calendarView;
    TextView starttext;
    TextView fertiletext;
    TextView ovulationtext;
    Date selected;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        starttext = (TextView) view.findViewById(R.id.starttext);
        fertiletext = (TextView) view.findViewById(R.id.fertiletext);
        ovulationtext = (TextView) view.findViewById(R.id.ovulationtext);

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
                selected = date;
                intent.putExtra("CurrentDate", date.getTime());
                startActivityForResult(intent, 2);

                final Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(selected);
                List<DayDecorator> decorators = new ArrayList<>();
                decorators.add(new DaysDecorator(CalendarFragment.this));
                calendarView.setDecorators(decorators);
                calendarView.refreshCalendar(currentCalendar);
            }

            @Override
            public void onMonthChanged(Date date) {
                // do nothing
            }
        });

        List<DayDecorator> decorators = new ArrayList<>();
        decorators.add(new DaysDecorator(this));
        calendarView.setDecorators(decorators);
        calendarView.refreshCalendar(currentCalendar);
        return view;
    }

    // called when popuplayoutactivity finishes, refreshes calendar to reflect changes
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            final Date dateObj = new Date(data.getExtras().getLong("CurrentDate",-1));
            final Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(dateObj);
            List<DayDecorator> decorators = new ArrayList<>();
            decorators.add(new DaysDecorator(this));
            calendarView.setDecorators(decorators);
            calendarView.refreshCalendar(currentCalendar);
       //     Log.e("TEST", data.getExtras().getLong("CurrentDate", -1)+"");
        }
    }

    //Renders Calendar to show Ovulation/Fertilization days
    private class DaysDecorator implements DayDecorator {
        protected CalendarFragment context;

        public DaysDecorator(CalendarFragment _context){
            context = _context;
        }

        @Override
        public void decorate(DayView dayView) {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

            if(CycleCalendarLibrary.fileExist(getContext())) {
                Date[] myDate = CycleCalendarLibrary.initializeDates(getContext());
                int[] myInt = CycleCalendarLibrary.initializeChart(getContext());

                for (int i = 0; i < myDate.length; i++) {
                    if (myInt[i] == 0) {
                        cal1.setTime(dayView.getDate());
                        cal2.setTime(myDate[i]);

                        context.starttext.setText("Period Starts at " + dateFormat.format(myDate[i]));
                        boolean sameDays = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                        if (sameDays) {
                            dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_firstday));
                            dayView.setTextColor(Color.BLACK);
                        }

                        cal2.add(Calendar.DATE, 9);
                        cal3.setTime(cal2.getTime());
                        cal3.add(Calendar.DATE, 6);
                        context.fertiletext.setText("Fertile Days is from " + dateFormat.format(cal2.getTime()) + " to " + dateFormat.format(cal3.getTime()));
                        for (int j = 1; j < 8; j++) {
                            cal1.setTime(dayView.getDate());

                            boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                            if (sameDay) {
                                if (j == 5) {
                                    dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_ovulation));
                                    dayView.setTextColor(Color.WHITE);
                                    context.ovulationtext.setText("Ovulation Day is at " + dateFormat.format(cal2.getTime()));
                                } else {
                                    dayView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_fertile));
                                    dayView.setTextColor(Color.WHITE);
                                }
                            }

                            cal2.add(Calendar.DATE, 1);
                        }
                    }
                }
            }
        }
    }
}