package com.dissonant.quotas.utils;

import java.sql.Date;
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
    public ArrayList<String> getTimeRangeArray(Date startTime, Date endTime) {
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
     * Returns a string formatted time from integers of hours and minutes.
     *
     * @param hourOfDay hour of the day as int.
     * @param minute    minute of the hour as an int.
     * @return String of formatted time.
     */
    private String getTimeAsString(int hourOfDay, int minute) {
        StringBuilder result;
        String format;

        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        result = new StringBuilder().append(hourOfDay).append(":").append(minute)
            .append(" ").append(format);

        return result.toString();
    }
}
