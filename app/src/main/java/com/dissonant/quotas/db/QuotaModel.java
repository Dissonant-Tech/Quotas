package com.dissonant.quotas.db;

import java.sql.Time;

public class QuotaModel {

    private int id;
    private String title;
    private String description;
    private Time lengthTime;
    private Time startTime;
    private Time endTime;
    private boolean isActive;

    public QuotaModel(int id, String title, String description, 
            Time lengthTime, Time startTime, Time endTime, boolean isActive) { 

        this.id = id;
        this.title = title;
        this.description = description;
        this.lengthTime = lengthTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
    }
    
    /*
     * Constructor for SQL 
     */
    public QuotaModel(int id, String title, String description, 
            String lengthTime, String startTime, String endTime, int isActive) { 

        this.id = id;
        this.title = title;
        this.description = description;
        this.setLengthTime(lengthTime);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setIsActive(isActive);
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {    
        return description;
    }
    
    public void setLengthTime(String lengthTime) {
        Time t = Time.valueOf(lengthTime);
        this.lengthTime = t;
    }

    public void setLengthTime(Time lengthTime) {
        this.lengthTime = lengthTime;
    }

    public Time getLengthTime() {    
        return lengthTime;
    }

    public void setStartTime(String startTime) {
        Time t = Time.valueOf(startTime);
        this.startTime = t;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getStartTime() {    
        return startTime;
    }
    
    public void setEndTime(String endTime) {
        Time t = Time.valueOf(endTime);
        this.endTime = t;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getEndTime() {    
        return endTime;
    }

}