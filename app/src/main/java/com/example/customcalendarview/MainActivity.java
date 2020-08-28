package com.example.customcalendarview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);


        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setEventIndicatorStyle(1);


        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM- yyyy",Locale.ENGLISH);
        String month = dateFormat.format(calendar.getTime());

        TextView txtMonth = (TextView) findViewById(R.id.text_month);
        txtMonth.setText(month);

        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT

        Date date = new Date();
        Long timemili = date.getTime();

        Event ev1 = new Event(Color.GREEN, 1598661780000L, "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);




        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
        Event ev2 = new Event(Color.GREEN, 1433704251000L);
        compactCalendarView.addEvent(ev2);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
        Log.d(TAG, "Events: " + events);

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);


                if (dateClicked.toString().compareTo("Sat Aug 15 00:00:00 GMT+07:00 2020") == 0) {
                    Toast.makeText(MainActivity.this, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(MainActivity.this, "Day was clicked: " + dateClicked + " with events " + events, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                TextView txtMonth = (TextView) findViewById(R.id.text_month);
                txtMonth.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

    }
}