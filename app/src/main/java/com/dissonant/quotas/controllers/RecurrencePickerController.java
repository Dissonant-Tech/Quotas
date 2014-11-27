package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.dissonant.quotas.ui.dialogs.RecurrencePickerFragment;
import com.dissonant.quotas.ui.dialogs.RecurrencePickerFragment.RecurrencePickerListener;


public class RecurrencePickerController implements View.OnClickListener {

    private Context context;
    private RecurrencePickerListener listener;

    public RecurrencePickerController(Context context, RecurrencePickerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        RecurrencePickerFragment dialog =  new RecurrencePickerFragment(this.context, this.listener);
        dialog.show(((Activity) this.context).getFragmentManager(), "recurrencePicker");
    }
}
