package com.dissonant.quotas.ui.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerDialog extends DialogFragment {

    OnTimeSetListener mListener;

    public TimePickerDialog(OnTimeSetListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.TimePickerDialog dialog;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        dialog = new android.app.TimePickerDialog(getActivity(), mListener,
                hour, minute, DateFormat.is24HourFormat(getActivity()));

        return dialog;
    }
}
