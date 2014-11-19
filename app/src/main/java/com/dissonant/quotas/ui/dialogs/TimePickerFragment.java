package com.dissonant.quotas.ui.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

public class TimePickerFragment extends DialogFragment {

    View view;
    OnTimeSetListener timePickerListener;

    public TimePickerFragment(View view, OnTimeSetListener timePickerListener) {
        this.view = view;
        this.timePickerListener = timePickerListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog dialog;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        dialog = new TimePickerDialog(getActivity(), timePickerListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

        return dialog;
    }
}
