package com.JackHolm.DayCounter;

import android.util.Log;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Counter
{
    public String Name;
    private int year;
    private int month;
    private int day;
    private long ms;

    // m is 1-12
    Counter(String n, int y, int m, int d)
    {
        Name = n;
        year = y;
        month = m;
        day = d;
        Calendar cal = Calendar.getInstance();
        cal.set(y, m-1, d, 0, 0, 0);
        ms = cal.getTimeInMillis();
    }

    public int Count()
    {
        Calendar cal = Calendar.getInstance();
        System.out.print(cal.get(Calendar.MONTH));
        long difference = cal.getTimeInMillis() - ms;
        return (int)TimeUnit.MILLISECONDS.toDays(difference);
    }
    public String DateSaveString()
    {
        return String.format("%d %d %d\n", year, month, day);
    }
    public String DateString()
    {
        return String.format("%02d/%02d/%d", month, day, year);
    }
}