package com.dissonant.quotas.model;

import java.util.Calendar;

import com.dissonant.quotas.db.DaysEnum;

public class QuotaModel {

    private int id;
    private String title = "";
    private String description = "";
    private boolean repeat = false;
    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();
    private boolean isActive = false;
    private DaysEnum[] repeatDays;
    private int[] taskArray;
    private int color;

    public QuotaModel() {

    }

    public QuotaModel(int id, String title, String description,
            boolean repeat, Calendar startTime, Calendar endTime, boolean isActive) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.repeat = repeat;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
    }

    /*
     * Constructor for SQL
     */
    public QuotaModel(int id, String title, String description,
            int repeat, Calendar startTime, Calendar endTime, int isActive) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.setRepeat(repeat);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setIsActive(isActive);
    }

    public int getDurationAsInt() {
        int val = 0;
        return val;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIsActive(int isActive) {
        if (isActive >= 1) {
            this.isActive = true;
        } else {
            this.isActive = false;
        }
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    /**
     * @return the repeatDays
     */
    public DaysEnum[] getRepeatDays() {
        return repeatDays;
    }

    /**
     * @param repeatDays the repeatDays to set
     */
    public void setRepeatDays(DaysEnum[] repeatDays) {
        this.repeatDays = repeatDays;
    }

    /**
     * @return the taskArray
     */
    public int[] getTaskArray() {
        return taskArray;
    }

    /**
     * Array of QuotaTask IDs that correspond to the QuotaModel instance.
     *
     * @param taskArray the taskArray to set
     */
    public void setTaskArray(int[] taskArray) {
        this.taskArray = taskArray;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRepeat(int repeat) {
        if (repeat >= 1) {
            this.repeat = true;
        } else {
            this.repeat = false;
        }
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean getRepeat() {
        return repeat;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "QuotaModel [title=" + title + ", color=" + color + ", start="
            + startTime.getTimeInMillis() + ", end=" + endTime.getTimeInMillis() 
            + "]";
    }

}
