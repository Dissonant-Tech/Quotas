package com.dissonant.quotas.ui.dialogs;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.dissonant.quotas.ui.views.CircleDisplay;
import com.dissonant.quotas.R;

public class TimeRangeFragment extends DialogFragment
    implements CircleDisplay.SelectionListener {

    private static final String TAG = "TimeRangeFragment";

    private int mStepSize;
    private float mDuration, mMaxDuration;
    private long mStart, mEnd;
    private TimeRangeListener listener;
    private CircleDisplay mDialogView;
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

        mDialogView = new CircleDisplay(getActivity());
        mDialogView.setSelectionListener(this);

        int backgroundColor = getResources().getColor(
                R.color.abc_background_cache_hint_selector_material_light);

        // Pass the inflated view and initialize the CircleDisplay
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

    @Override
    public void onValueSelected(float val, float maxVal) {
        mDuration = val;
        mMaxDuration = maxVal;
    }

    @Override
    public void onSelectionUpdate(float val, float maxVal) {
    }

    public void initTimeRangeSelector(View v, int backgroundColor) {
        // TODO: Fix CircleDisplay "array not long enough" errors
        String[] customText = genCustomText(mStart, mEnd, mStepSize);
        mDialogView.setCustomText(customText);

        mDialogView.setUnit("");
        mDialogView.setTouchEnabled(true);
        mDialogView.setDrawText(true);
        mDialogView.setElevation(1);
        mDialogView.setStepSize(1.0f);
        mDialogView.setColor(getResources().getColor(R.color.primary));
        mDialogView.showValue(0.0f, customText.length, false);
    }

    private String[] genCustomText(long start, long end, int stepSize) {
        int hours = 0;
        int minutes = 0;

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        startTime.setTimeInMillis(start);
        endTime.setTimeInMillis(end);

        ArrayList<String> resultArr = new ArrayList<String>();

        while (startTime.before(endTime)) {
            if (minutes >= 59) {
                minutes = 0;
                hours++;
            } else {
                minutes++;
            }

            resultArr.add(genTimeSentence(hours, minutes));
            startTime.add(Calendar.MINUTE, 1);
        }

        String[] result = new String[resultArr.size()];

        for (int i = 0; i < resultArr.size(); i++) {
            result[i] = resultArr.get(i);
        }

        return result;
    }

    public String genTimeSentence(int hours, int minutes) {
        String strHours = "hours";
        String strMinutes = "minutes";
        String strAnd = " and ";
        String pad = " ";

        StringBuilder sb = new StringBuilder();

        if (hours == 0) {
            sb.append(minutes).append(pad).append(strMinutes);
        } else {
            sb.append(hours).append(pad).append(strHours)
                .append(strAnd).append(minutes).append(pad).append(strMinutes);
        }

        return sb.toString();
    }
}
