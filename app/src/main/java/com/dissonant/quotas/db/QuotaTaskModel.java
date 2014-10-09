package com.dissonant.quotas.db;

import java.util.Calendar;

public class QuotaTaskModel {

    private int id;
    private Calendar taskDate;
    private boolean completed;

    public QuotaTaskModel(int id, Calendar taskDate, boolean completed) {
        this.id = id;
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
