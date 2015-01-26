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
    private long mStart, mEnd;

    public TimeRangeController(Context context, EditView view, QuotaModel quota){
        mContext = context;
        mQuota = quota;
        mView = view;
    }

    @Override
    public void onClick(View v) {
        if (isTimeSet()) {
            showTimeRangeDialog(mStart, mEnd, this);
        } else {
            Toast.makeText(mContext, ((Activity) mContext).getResources()
                    .getString(R.string.time_not_set), Toast.LENGTH_SHORT).show();
        }
    }

    private void showTimeRangeDialog(long start, long end, TimeRangeListener listener) {
        TimeRangeFragment trDialog = new TimeRangeFragment(start, end, listener);
        trDialog.show(((Activity) mContext).getFragmentManager(), "timerangePicker");
    }

    @Override
    public void onTimeRangeSet(float val, float maxVal) {
        CircleSelector timeRangeView = (CircleSelector) mView.getTimeRange();
        timeRangeView.showValue(val, maxVal, false);

    }

    private boolean isTimeSet() {
        if (mQuota.getStartTime() == 0 ||
                mQuota.getEndTime() == 0 ) {
            return false;
        } else {
            return true;
        }
    }
}
