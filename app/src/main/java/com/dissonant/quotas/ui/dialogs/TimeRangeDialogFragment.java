package com.dissonant.quotas.ui.dialogs;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.views.CircleSelector;

public class TimeRangeDialogFragment extends DialogFragment
    implements CircleSelector.SelectionListener {

    private Time startTime;
    private Time endTime;

    public TimeRangeDialogFragment(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get dialog bulder and inflate the dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.dialog_time_range, null);

        int backgroundColor = getResources().getColor(
                R.color.abc_background_cache_hint_selector_material_light);

        // Pass the inflated view and initialize the DoughnutSelector
        initTimeRangeSelector(mDialogView, backgroundColor);

        builder.setView(mDialogView);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                TimeRangeDialogFragment.this.getDialog().cancel();
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
        CircleSelector mTimeRangeChart = (CircleSelector) v.findViewById(R.id.TimeRangeChart);

        String[] customText = genCustomText(this.startTime, this.endTime, 5);
        mTimeRangeChart.setCustomText(customText);

        mTimeRangeChart.setTouchEnabled(true);
        mTimeRangeChart.setDrawText(true);
        mTimeRangeChart.setElevation(1);
        mTimeRangeChart.setColor(getResources().getColor(R.color.primary));
        mTimeRangeChart.showValue(0.0f, customText.length, false);
    }

    public void onValueSelected(float val, float maxVal) {

    }

    public void onSelectionUpdate(float val, float maxVal) {

    }

    private String[] genCustomText(Time startTime, Time endTime, int stepSize) {
        int numTimes = 0;
        ArrayList<String> resultArr = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm 'Hours'");

        while (calendar.getTime().before(endTime)) {
           resultArr.add(sdf.format(calendar.getTime()));
           calendar.add(Calendar.MINUTE, stepSize);
           numTimes++;
        }

        String[] result = new String[numTimes];

        for (int i = 0; i < resultArr.size(); i++) {
            result[i] = resultArr.get(i);
        }
        return result;
    }
}
