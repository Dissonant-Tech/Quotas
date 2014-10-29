package com.dissonant.quotas.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.views.DoughnutSelector;

public class TimeRangeDialogFragment extends DialogFragment {
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

        builder.setView(mDialogView)
            .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    setTimes();
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
        DoughnutSelector mTimeRangeChart = (DoughnutSelector) v.findViewById(R.id.TimeRangeChart);
        mTimeRangeChart.setTouchEnabled(true);
        mTimeRangeChart.setDrawText(true);
        mTimeRangeChart.setElevation(1);
        mTimeRangeChart.setColor(getResources().getColor(R.color.primary));
        mTimeRangeChart.showValue(0.0f, 24.0f, false);
    }

    public void setTimes() {
    }
}
