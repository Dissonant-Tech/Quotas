package com.dissonant.quotas.model;

import com.dissonant.quotas.db.DaysEnum;

public class QuotaModel {

    private Long id;
    private String title = "";
    private String description = "";
    private boolean repeat = false;
    private long startTime = 0;
    private long endTime = 0;
    private long duration = 0;
    private boolean isActive = false;
    private DaysEnum[] repeatDays;
    private int[] taskArray;
    private int color;

    public QuotaModel() {

    }

    /*
     * Constructor for SQL
     */
    public QuotaModel(Long id, String title, String description,
            int repeat, long startTime, long endTime, int isActive) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.setRepeat(repeat);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setIsActive(isActive);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (ensureValid(title)) {
            this.title = title;
        }
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

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
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

    @Override
    public String toString() {
        return "QuotaModel [title=" + title + ", color=" + color + ", start="
            + startTime + ", end=" + endTime
            + "]";
    }

    public boolean validate() {
        boolean isValid = true;
        if (!ensureValid(this.title)) {
            isValid = false;
        }

        return isValid;
    }

    private boolean ensureValid(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
