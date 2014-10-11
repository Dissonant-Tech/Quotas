package com.dissonant.quotas.ui.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.dissonant.quotas.R;

public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {
    private Calendar mStartTime = Calendar.getInstance();
    private Calendar mEndTime = Calendar.getInstance();

    View mView;

    public TimePickerFragment(View mView) {
        this.mView = mView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if (mView.getId() == R.id.edit_start_time) {
            Button startTimeButton = (Button) mView.findViewById(R.id.edit_start_time);
            startTimeButton.setText(getTimeAsString(hourOfDay, minute));
            mStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mStartTime.set(Calendar.MINUTE, minute);
        }
        else if (mView.getId() == R.id.edit_end_time) {
            Button endTimeButton = (Button) mView.findViewById(R.id.edit_end_time);
            endTimeButton.setText(getTimeAsString(hourOfDay, minute));
            mEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mEndTime.set(Calendar.MINUTE, minute);
        }
        genDefaultDuration();
    }

    public void genDefaultDuration() {
        if (mStartTime.isSet(Calendar.HOUR_OF_DAY)
                && mEndTime.isSet(Calendar.HOUR_OF_DAY)) {
            long milliSeconds = mStartTime.getTimeInMillis() - mEndTime.getTimeInMillis();
            long hours = milliSeconds/(1000*60*60);
            long mins = milliSeconds%(1000*60*60);
        }
    }
    
    public String getTimeAsString(int hourOfDay, int minute) {
        StringBuilder result;
        String format;

        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        result = new StringBuilder().append(hourOfDay).append(":").append(minute)
            .append(" ").append(format);

        return result.toString();
    }

}
