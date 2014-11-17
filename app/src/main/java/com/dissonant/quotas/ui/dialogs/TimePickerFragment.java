package com.dissonant.quotas.ui.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dissonant.quotas.R;
import com.dissonant.quotas.utils.Utils;

public class TimePickerFragment extends DialogFragment {

    View view;
    String title;
    OnTimeSetListener timePickerListener;

    public TimePickerFragment(View view, OnTimeSetListener timePickerListener, String title) {
        this.view = view;
        this.title = title;
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
        dialog.setTitle(title);

        return dialog;
    }
}
