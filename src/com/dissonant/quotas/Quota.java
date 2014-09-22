package com.dissonant.quotas;

import java.util.Locale;
import java.util.Calendar;
import java.lang.String;
import java.sql.Time;

public class Quota {
    private String title;
    private String description;

    private Time lengthTime;
    private Time startTime;
    private Time endTime;

    private boolean[] weekDays;
    private boolean[] monthDays;

    private boolean useSpecificDate;
    private Calendar specificDate;

    public Quota(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lengthTime
     */
    public Time getLengthTime() {
        return lengthTime;
    }

    /**
     * @param lengthTime the lengthTime to set
     */
    public void setLengthTime(Time lengthTime) {
        this.lengthTime = lengthTime;
    }

    /**
     * @return the startTime
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the weekDays
     */
    public boolean[] getWeekDays() {
        return weekDays;
    }

    /**
     * @param weekDays the weekDays to set
     */
    public void setWeekDays(boolean[] weekDays) {
        this.weekDays = weekDays;
    }

    /**
     * @return the monthDays
     */
    public boolean[] getMonthDays() {
        return monthDays;
    }

    /**
     * @param monthDays the monthDays to set
     */
    public void setMonthDays(boolean[] monthDays) {
        this.monthDays = monthDays;
    }

    /**
     * @return the useSpecificDate
     */
    public boolean isUseSpecificDate() {
        return useSpecificDate;
    }

    /**
     * @param useSpecificDate the useSpecificDate to set
     */
    public void setUseSpecificDate(boolean useSpecificDate) {
        this.useSpecificDate = useSpecificDate;
    }

    /**
     * @return the specificDate
     */
    public Calendar getSpecificDate() {
        return specificDate;
    }

    /**
     * @param specificDate the specificDate to set
     */
    public void setSpecificDate(Calendar specificDate) {
        this.specificDate = specificDate;
    }

}
