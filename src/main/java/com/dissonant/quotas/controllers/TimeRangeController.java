package com.dissonant.quotas.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.dissonant.quotas.R;
import com.dissonant.quotas.controllers.TimePickerController.TimePickerListener;
import com.dissonant.quotas.ui.views.CircleSelector;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.Utils;

public class TimeRangeController implements OnClickListener, TimePickerListener {

    private Context context;
    private TimeRangeListener listener;
    private EditView editView;
    private Calendar startTime, endTime;

    public interface TimeRangeListener {
        void onTimeRangeSet(Calendar startTime, Calendar endTime, float val, float maxVal);
    }

    public TimeRangeController(Context context, TimeRangeListener listener, EditView editView){
        this.context = context;
        this.listener = listener;
        this.editView = editView;

        // Calls onFinishedTimeSet()
        editView.getStartTime().setOnClickListener(new TimePickerController(context, this));
        editView.getEndTime().setOnClickListener(new TimePickerController(context, this));
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
            editView.setStartTime(Utils.getTimeAsString(startTime, "hh:mm a"));
        } else if (editView.getEndTime().getId() == view.getId()) {
            endTime = Utils.getTimeFromInt(hourOfDay, minute);
            editView.setEndTime(Utils.getTimeAsString(endTime, "hh:mm a"));
        }
    }

    private void showTimeRangeDialog(Calendar startTime, Calendar endTime, TimeRangeListener listener) {
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

    public class TimeRangeFragment extends DialogFragment
            implements CircleSelector.SelectionListener {

            private static final String TAG = "TimeRangeFragment";

            private Calendar startTime;
            private Calendar endTime;
            private TimeRangeListener listener;
            private CircleSelector mDialogView;
            float val, maxVal;

            public TimeRangeFragment(Calendar startTime, Calendar endTime, TimeRangeListener listener) {
                this.startTime = startTime;
                this.endTime = endTime;
                this.listener = listener;
            }

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Get dialog bulder and inflate the dialog layout
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                mDialogView = new CircleSelector(getActivity());
                mDialogView.setSelectionListener(this);

                int backgroundColor = getResources().getColor(
                        R.color.abc_background_cache_hint_selector_material_light);

                // Pass the inflated view and initialize the DoughnutSelector
                initTimeRangeSelector(mDialogView, backgroundColor);

                builder.setView(mDialogView);

                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onTimeRangeSet(startTime, endTime, val, maxVal);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TimeRangeFragment.this.getDialog().cancel();
                    }
                });

                // Create the AlertDialog and return it
                return builder.create();
            }

            @Override
            public void onStart() {
                super.onStart();

                // Safety check
                if (getDialog() == null) {
                    return;
                }

                // Set the animation to use on showing and hiding the dialog
                getDialog().getWindow().setWindowAnimations(
                        R.style.dialog_animation_slide_up);
            }

            public void initTimeRangeSelector(View v, int backgroundColor) {
                String[] customText = genCustomText(this.startTime, this.endTime, 5);
                mDialogView.setCustomText(customText);

                mDialogView.setTouchEnabled(true);
                mDialogView.setDrawText(true);
                mDialogView.setElevation(1);
                mDialogView.setColor(getResources().getColor(R.color.primary));
                mDialogView.setStepSize(1);
                mDialogView.showValue(0.0f, customText.length, false);
            }

            public void onValueSelected(float val, float maxVal) {
                this.val = val;
                this.maxVal = maxVal;
            }

            public void onSelectionUpdate(float val, float maxVal) {
            }

            private String[] genCustomText(Calendar startTime, Calendar endTime, int stepSize) {
                int numTimes = 0;
                ArrayList<String> resultArr = new ArrayList<String>();

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm 'Hours'");

                while (startTime.before(endTime)) {
                    resultArr.add(sdf.format(startTime.getTimeInMillis()));
                    startTime.add(Calendar.MINUTE, stepSize);
                    numTimes++;
                }

                String[] result = new String[numTimes];

                for (int i = 0; i < resultArr.size(); i++) {
                    result[i] = resultArr.get(i);
                }
                return result;
            }
    }
}
