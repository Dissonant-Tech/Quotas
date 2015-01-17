package com.dissonant.quotas.model;

import java.util.Calendar;

public class QuotaTaskModel {

    private int id;
    private int refId;
    private Calendar taskDate;
    private boolean completed;

    public QuotaTaskModel(int id, int refId, Calendar taskDate, boolean completed) {
        this.id = id;
        this.refId = refId;
        this.taskDate = taskDate;
        this.completed = completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTaskDate(Calendar taskDate) {
        this.taskDate = taskDate;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public Calendar getTaskDate() {
        return taskDate;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean getCompleted() {
        return completed;
    }

}
