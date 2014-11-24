package com.dissonant.quotas.ui.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment 
    implements OnTimeSetListener {

    public interface TimePickerDialogListener {
        void onFinishedTimeSet(View view, int hourOfDay, int minute);
    }

    View view;
    TimePickerDialogListener listener;

    public TimePickerFragment(View view, TimePickerDialogListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog dialog;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        dialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

        return dialog;
    }

    @Override 
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        listener.onFinishedTimeSet(this.view, hourOfDay, minute);
    }
}
