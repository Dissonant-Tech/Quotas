package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.model.RecurrenceModel;
import com.dissonant.quotas.ui.dialogs.RecurrencePickerDialog;
import com.dissonant.quotas.ui.dialogs.RecurrencePickerDialog.RecurrencePickerListener;
import com.dissonant.quotas.ui.views.EditView;


public class RecurrencePickerController implements View.OnClickListener,
       RecurrencePickerListener {

    private Context mContext;
    private QuotaModel mQuota;
    private EditView mView;

    public RecurrencePickerController(Context context, QuotaModel quota, EditView view ) {
        mContext = context;
        mQuota = quota;
        mView = view;
    }

    @Override
    public void onClick(View v) {
        RecurrencePickerDialog dialog =  new RecurrencePickerDialog(mContext, this);
        dialog.show(((Activity) mContext).getFragmentManager(), "recurrencePicker");
    }

    @Override
    public void onRecurrenceSet(RecurrenceModel model) {

    }
}
