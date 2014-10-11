package com.dissonant.quotas.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.DoughnutSelector;

public class TimeRangeDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get dialog bulder and inflate the dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.dialog_time_range, null);

        // Pass the inflated view and initialize the DoughnutSelector
        initTimeRangeSelector(mDialogView);

        builder.setView(mDialogView)
            .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // save clicked
                }
            })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                TimeRangeDialogFragment.this.getDialog().cancel();
            }
        });

        // Create the AlertDialog and return it
        return builder.create();
    }
    public void initTimeRangeSelector(View v) {
        DoughnutSelector mTimeRangeChart = (DoughnutSelector) v.findViewById(R.id.TimeRangeChart);
        mTimeRangeChart.setTouchEnabled(true);
        mTimeRangeChart.setDrawText(false);
        mTimeRangeChart.setElevation(1);
        mTimeRangeChart.setColor(getResources().getColor(R.color.primary_dark));
    }
}
