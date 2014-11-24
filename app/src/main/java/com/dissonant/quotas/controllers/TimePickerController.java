package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.dissonant.quotas.ui.dialogs.TimePickerFragment;
import com.dissonant.quotas.ui.dialogs.TimePickerFragment.TimePickerDialogListener;

public class TimePickerController implements OnClickListener {

    private Context context;
    private TimePickerDialogListener listener;

    public TimePickerController(Context context, TimePickerDialogListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        openTimePickerDialog(v, this.listener);
    }

    public void openTimePickerDialog(View view, TimePickerDialogListener listener) {
        TimePickerFragment tPickerFragment = new TimePickerFragment(view, listener);
        tPickerFragment.show(((Activity) this.context).getFragmentManager(), "timePicker");
    }
}
