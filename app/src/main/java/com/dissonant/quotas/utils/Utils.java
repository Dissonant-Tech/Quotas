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
     * @param endTime a Date object of the ending time for the range.
     * @return ArrayList<String> of time range.
     */
    public ArrayList<String> genTimeRangeArray(Date startTime, Date endTime) {
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
}
