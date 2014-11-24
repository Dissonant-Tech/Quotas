package com.dissonant.quotas.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Utils {

    /**
     * Returns an ArrayList of strings denoting the minutes in a range of time.
     *
     * @param startTime a Date object of the starting time for the range.
     * @param endTime   a Date object of the ending time for the range.
     * @return ArrayList<String> of time range.
     */
    static public ArrayList<String> getTimeRangeArray(Date startTime, Date endTime) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        while (calendar.getTime().before(endTime)) {
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
    static public String getTimeAsString(Time time, String format) {
        if (format.isEmpty()) {
            format = "hh:mm Hours";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(time.getTime());
    }

    static public String getTimeAsString(int hourOfDay, int minute, String format) {
        return getTimeAsString(getTimeFromInt(hourOfDay, minute), format);
    }

    static public Time getTimeFromInt(int hourOfDay, int minute) {
        Time time = Time.valueOf(new StringBuilder()
            .append(hourOfDay).append(":").append(minute).append(":00").toString());

        return time;
    }
}
