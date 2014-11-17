package com.dissonant.quotas.ui.listeners;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dissonant.quotas.utils.Utils;

public class TimePickerListener implements OnTimeSetListener {

    private TextView tView;
    private String timeSet;

    public TimePickerListener(TextView tView) {
        this.tView = tView;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeSet = Utils.getTimeAsString(hourOfDay, minute);
        tView.setText(timeSet);
    }

    /**
     * @return the timeSet
     */
    public String getTimeSet() {
        return timeSet;
    }
}
