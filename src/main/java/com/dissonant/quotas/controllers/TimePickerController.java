package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;

import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.ui.dialogs.TimePickerDialog;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.Utils;

public class TimePickerController implements OnClickListener, OnTimeSetListener {

    private Context mContext;
    private EditView mView;
    private QuotaModel mQuota;

    public TimePickerController(Context context, EditView view, QuotaModel quota){
        mContext = context;
        mView = view;
        mQuota = quota;
    }

    @Override
    public void onClick(View v) {
        TimePickerDialog tPickerDialog = new TimePickerDialog(this);
        tPickerDialog.show(((Activity) this.mContext).getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        long time;
        if (mView.getStartView().getId() == view.getId()) {
            mQuota.setStartTime(
                    Utils.getTimeFromInt(hourOfDay, minute).getTimeInMillis());
            mView.setStartView(Utils.getTimeAsString(mQuota.getStartTime(), "hh:mm a"));
        } else if (mView.getEndView().getId() == view.getId()) {
            mQuota.setEndTime(
                    Utils.getTimeFromInt(hourOfDay, minute).getTimeInMillis());
            mView.setEndView(Utils.getTimeAsString(mQuota.getEndTime(), "hh:mm a"));
        }
    }
}
