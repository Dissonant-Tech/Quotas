package com.dissonant.quotas.controllers;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.dissonant.quotas.R;
import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.ui.dialogs.TimeRangeFragment;
import com.dissonant.quotas.ui.dialogs.TimeRangeFragment.TimeRangeListener;
import com.dissonant.quotas.ui.views.CircleSelector;
import com.dissonant.quotas.ui.views.EditView;

public class TimeRangeController implements OnClickListener, TimeRangeListener {

    private Context mContext;
    private EditView mView;
    private QuotaModel mQuota;
    private Calendar startTime, endTime;

    public TimeRangeController(Context context, EditView view, QuotaModel quota){
        mContext = context;
        mQuota = quota;
        mView = view;
    }

    @Override
    public void onClick(View v) {
        if (isTimeSet()) {
            showTimeRangeDialog(this.startTime, this.endTime, this);
        } else {
            Toast.makeText(mContext, ((Activity) mContext).getResources()
                    .getString(R.string.time_not_set), Toast.LENGTH_SHORT).show();
        }
    }

    private void showTimeRangeDialog(Calendar startTime, Calendar endTime, TimeRangeListener listener) {
        TimeRangeFragment trDialog = new TimeRangeFragment(startTime, endTime, listener);
        trDialog.show(((Activity) mContext).getFragmentManager(), "timerangePicker");
    }

    @Override
    public void onTimeRangeSet(Calendar startTime, Calendar endTime, float val, float maxVal) {
        CircleSelector timeRangeView = (CircleSelector) mView.getTimeRange();
        timeRangeView.showValue(val, maxVal, false);

        mQuota.setStartTime(startTime.getTimeInMillis());
        mQuota.setEndTime(endTime.getTimeInMillis());
    }

    public boolean isTimeSet() {
        if (this.startTime == null ||
                this.endTime == null ) {
            return false;
        } else {
            return true;
        }
    }
}
