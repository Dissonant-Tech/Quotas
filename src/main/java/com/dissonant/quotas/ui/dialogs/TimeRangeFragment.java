package com.dissonant.quotas.ui.dialogs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.views.CircleSelector;

public class TimeRangeFragment extends DialogFragment
    implements CircleSelector.SelectionListener {

    private static final String TAG = "TimeRangeFragment";

    private int mStepSize;
    private float mDuration, mMaxDuration;
    private long mStart, mEnd;
    private TimeRangeListener listener;
    private CircleSelector mDialogView;
    float val, maxVal;

    public interface TimeRangeListener {
        void onTimeRangeSet(float val, float maxVal);
    }

    public TimeRangeFragment(long start, long end, TimeRangeListener listener) {
        mStart = start;
        mEnd = end;
        mStepSize = 1;
        mMaxDuration = mStart - mEnd;
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
                listener.onTimeRangeSet(mDuration, mMaxDuration);
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
        String[] customText = genCustomText(mStart, mEnd, mStepSize);
        mDialogView.setCustomText(customText);

        mDialogView.setTouchEnabled(true);
        mDialogView.setDrawText(true);
        mDialogView.setElevation(1);
        mDialogView.setColor(getResources().getColor(R.color.primary));
        mDialogView.setStepSize(1);
        mDialogView.showValue(0.0f, customText.length, false);
    }

    @Override
    public void onValueSelected(float val, float maxVal) {
        mDuration = val;
        mMaxDuration = maxVal;
    }

    @Override
    public void onSelectionUpdate(float val, float maxVal) {
    }

    private String[] genCustomText(long start, long end, int stepSize) {
        int numTimes = 0;
        ArrayList<String> resultArr = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm 'Hours'");
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(start);

        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(end);

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
