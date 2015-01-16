package com.dissonant.quotas.controllers;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;

public class TimePickerController implements OnClickListener {

    public interface TimePickerListener {
        void onFinishedTimeSet(View view, int hourOfDay, int minute);
    }

    private Context context;
    private TimePickerListener listener;

    public TimePickerController(Context context, TimePickerListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        openTimePickerDialog(v, this.listener);
    }

    public void openTimePickerDialog(View view, TimePickerListener listener) {
        TimePickerFragment tPickerFragment = new TimePickerFragment(view, listener);
        tPickerFragment.show(((Activity) this.context).getFragmentManager(), "timePicker");
    }

    public class TimePickerFragment extends DialogFragment 
            implements OnTimeSetListener {

            View view;
            TimePickerListener listener;

            public TimePickerFragment(View view, TimePickerListener listener) {
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
}
