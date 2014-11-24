package com.dissonant.quotas.controllers;

import java.sql.Time;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.dialogs.TimePickerFragment.TimePickerListener;
import com.dissonant.quotas.ui.dialogs.TimeRangeFragment;
import com.dissonant.quotas.ui.dialogs.TimeRangeFragment.TimeRangeListener;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.Utils;

public class TimeRangeController implements OnClickListener, TimePickerListener {

    private Context context;
    private TimeRangeListener listener;
    private EditView editView;
    private Time startTime, endTime;

    public TimeRangeController(Context context, TimeRangeListener listener, EditView editView){
        this.context = context;
        this.listener = listener;
        this.editView = editView;
    }

    @Override
    public void onClick(View v) {
        if (isTimeSet()) {
            showTimeRangeDialog(this.startTime, this.endTime, this.listener);
        } else {
            Toast.makeText(this.context, ((Activity) this.context).getResources()
                    .getString(R.string.time_not_set), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishedTimeSet(View view, int hourOfDay, int minute) {
        if (editView.getStartTime().getId() == view.getId()) {
            startTime = Utils.getTimeFromInt(hourOfDay, minute);
        } else if (editView.getEndTime().getId() == view.getId()) {
            endTime = Utils.getTimeFromInt(hourOfDay, minute);
        }
    }

    private void showTimeRangeDialog(Time startTime, Time endTime, TimeRangeListener listener) {
        TimeRangeFragment trDialog = new TimeRangeFragment(startTime, endTime, listener);
        trDialog.show(((Activity) this.context).getFragmentManager(), "timerangePicker");
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
