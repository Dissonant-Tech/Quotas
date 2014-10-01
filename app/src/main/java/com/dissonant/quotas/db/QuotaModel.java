package com.dissonant.quotas.db;

import java.sql.Time;

public class QuotaModel {

	private String title;
	private boolean isActive;
	private String description;
	private Time lengthTime;
	private Time startTime;
	private Time endTime;

    public QuotaModel(String title, boolean isActive, String description, Time lengthTime, Time startTime, Time endTime) { 
		this.title = title;
		this.isActive = isActive;
		this.description = description;
		this.lengthTime = lengthTime;
		this.startTime = startTime;
		this.endTime = endTime;
    }

	public void setTitle(String title) {
    	this.title = title;
	}

	public String getTitle() {    
		return title;
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

	public void setLengthTime(Time lengthTime) {
    	this.lengthTime = lengthTime;
	}

	public Time getLengthTime() {    
		return lengthTime;
	}

	public void setStartTime(Time startTime) {
    	this.startTime = startTime;
	}

	public Time getStartTime() {    
		return startTime;
	}

	public void setEndTime(Time endTime) {
    	this.endTime = endTime;
	}

	public Time getEndTime() {    
		return endTime;
	}

}
