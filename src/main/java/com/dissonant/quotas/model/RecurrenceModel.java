package com.dissonant.quotas.model;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;


public class RecurrenceModel implements Parcelable {

    // Defualts
    
    // Update android:maxLength in EditText as needed
    private static final int INTERVAL_MAX = 99;
    private static final int INTERVAL_DEFAULT = 1;
    // Update android:maxLength in EditText as needed
    private static final int COUNT_MAX = 730;
    private static final int COUNT_DEFAULT = 5;

    // Special cases in monthlyByNthDayOfWeek
    private static final int FIFTH_WEEK_IN_A_MONTH = 5;
    private static final int LAST_NTH_DAY_OF_WEEK = -1;

    static final int FREQ_DAILY = 0;
    static final int FREQ_WEEKLY = 1;
    static final int FREQ_MONTHLY = 2;
    static final int FREQ_YEARLY = 3;

    static final int END_NEVER = 0;
    static final int END_BY_DATE = 1;
    static final int END_BY_COUNT = 2;

    static final int MONTHLY_BY_DATE = 0;
    static final int MONTHLY_BY_NTH_DAY_OF_WEEK = 1;

    static final int STATE_NO_RECURRENCE = 0;
    static final int STATE_RECURRENCE = 1;

    public int recurrenceState;

    /**
     * FREQ: Repeat pattern
     *
     */
    public int freq = FREQ_WEEKLY;

    /**
     * INTERVAL: Every n days/weeks/months/years. n >= 1
     */
    public int interval = INTERVAL_DEFAULT;

    /**
     * UNTIL and COUNT: How does the the event end?
     *
     */
    public int end;

    /**
     * UNTIL: Date of the last recurrence. Used when until == END_BY_DATE
     */
    public Time endDate;

    /**
     * COUNT: Times to repeat. Use when until == END_BY_COUNT
     */
    public int endCount = COUNT_DEFAULT;

    /**
     * BYDAY: Days of the week to be repeated. Sun = 0, Mon = 1, etc
     */
    public boolean[] weeklyByDayOfWeek = new boolean[7];

    /**
     * BYDAY AND BYMONTHDAY: How to repeat monthly events? Same date of the
     * month or Same nth day of week.
     *
     */
    public int monthlyRepeat;

    /**
     * Day of the month to repeat. Used when monthlyRepeat ==
     * MONTHLY_BY_DATE
     */
    public int monthlyByMonthDay;

    /**
     * Day of the week to repeat. Used when monthlyRepeat ==
     * MONTHLY_BY_NTH_DAY_OF_WEEK
     */
    public int monthlyByDayOfWeek;

    /**
     * Nth day of the week to repeat. Used when monthlyRepeat ==
     * MONTHLY_BY_NTH_DAY_OF_WEEK 0=undefined, -1=Last, 1=1st, 2=2nd, ..., 5=5th
     *
     * We support 5th, just to handle backwards capabilities with old bug, but it
     * gets converted to -1 once edited.
     */
    public int monthlyByNthDayOfWeek;

    /*
     * (generated method)
     */
    @Override
    public String toString() {
        return "Model [freq=" + freq + ", interval=" + interval + ", end=" + end + ", endDate="
            + endDate + ", endCount=" + endCount + ", weeklyByDayOfWeek="
            + Arrays.toString(weeklyByDayOfWeek) + ", monthlyRepeat=" + monthlyRepeat
            + ", monthlyByMonthDay=" + monthlyByMonthDay + ", monthlyByDayOfWeek="
            + monthlyByDayOfWeek + ", monthlyByNthDayOfWeek=" + monthlyByNthDayOfWeek + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RecurrenceModel() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(freq);
        dest.writeInt(interval);
        dest.writeInt(end);
        dest.writeInt(endDate.year);
        dest.writeInt(endDate.month);
        dest.writeInt(endDate.monthDay);
        dest.writeInt(endCount);
        dest.writeBooleanArray(weeklyByDayOfWeek);
        dest.writeInt(monthlyRepeat);
        dest.writeInt(monthlyByMonthDay);
        dest.writeInt(monthlyByDayOfWeek);
        dest.writeInt(monthlyByNthDayOfWeek);
        dest.writeInt(recurrenceState);
    }

}
