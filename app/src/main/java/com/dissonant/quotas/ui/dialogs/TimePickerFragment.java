package com.dissonant.quotas.ui.dialogs;

import java.util.Calendar;

import com.dissonant.quotas.utils.Utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

    View mView;
    String timePicked;

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
        timePicked = Utils.getTimeAsString(hourOfDay, minute);
    }

    /**
     * @return the timePicked
     */
    public String getTimePicked() {
        return timePicked;
    }
}
