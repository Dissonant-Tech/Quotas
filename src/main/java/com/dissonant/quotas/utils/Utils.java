package com.dissonant.quotas.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.text.format.Time;


public class Utils {

    /**
     * Returns an ArrayList of strings denoting the minutes in a range of time.
     *
     * @param start a Date object of the starting time for the range.
     * @param end   a Date object of the ending time for the range.
     * @return ArrayList<String> of time range.
     */
    static public ArrayList<String> getTimeRangeArray(Date start, Date end) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (calendar.getTime().before(end)) {
            calendar.add(calendar.MINUTE, 5);
            result.add(sdFormat.format(calendar.getTime()));
        }
        return result;
    }

    /**
     * Returns a string formatted time
     *
     * @return String of formatted time.
     */
    static public String getTimeAsString(Calendar c, String format) {
        if (format.isEmpty()) {
            format = "hh:mm Hours";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(c.getTimeInMillis());
    }

    static public String getTimeAsString(int hourOfDay, int minute, String format) {
        return getTimeAsString(getTimeFromInt(hourOfDay, minute), format);
    }

    static public String getTimeAsString(long time, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);

        return getTimeAsString(c, format);
    }

    static public Calendar getTimeFromInt(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        return c;
    }

    /**
     * Get first day of week as android.text.format.Time constant.
     *
     * @return the first day of week in android.text.format.Time
     */
    public static int getFirstDayOfWeek(Context context) {
        int startDay = Calendar.SUNDAY;

        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }

    /**
     * Get first day of week as java.util.Calendar constant.
     *
     * @return the first day of week as a java.util.Calendar constant
     */
    public static int getFirstDayOfWeekAsCalendar(Context context) {
        return convertDayOfWeekFromTimeToCalendar(getFirstDayOfWeek(context));
    }

    /**
     * Converts the day of the week from android.text.format.Time to java.util.Calendar
     */
    public static int convertDayOfWeekFromTimeToCalendar(int timeDayOfWeek) {
        switch (timeDayOfWeek) {
            case Time.MONDAY:
                return Calendar.MONDAY;
            case Time.TUESDAY:
                return Calendar.TUESDAY;
            case Time.WEDNESDAY:
                return Calendar.WEDNESDAY;
            case Time.THURSDAY:
                return Calendar.THURSDAY;
            case Time.FRIDAY:
                return Calendar.FRIDAY;
            case Time.SATURDAY:
                return Calendar.SATURDAY;
            case Time.SUNDAY:
                return Calendar.SUNDAY;
            default:
                throw new IllegalArgumentException("Argument must be between Time.SUNDAY and " +
                        "Time.SATURDAY");
        }
    }


}
