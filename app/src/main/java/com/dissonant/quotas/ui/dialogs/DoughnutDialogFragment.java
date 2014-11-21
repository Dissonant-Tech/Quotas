package com.dissonant.quotas.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.views.CircleSelector;


public class DoughnutDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get dialog bulder and inflate the dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.dialog_doughnut_selector, null);

        // Pass the inflated view and initialize the CircleSelector
        initCircleSelector(mDialogView);

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
                DoughnutDialogFragment.this.getDialog().cancel();
            }
        });

        // Create the AlertDialog and return it
        return builder.create();
    }

    public void initCircleSelector(View v) {
        CircleSelector mCircleSelector = (CircleSelector) v.findViewById(R.id.doughnutSelector);
        mCircleSelector.setColor(Color.CYAN);
        mCircleSelector.setFormatDigits(0);
        mCircleSelector.setUnit("");
        mCircleSelector.showValue(0, 60, false);
        mCircleSelector.setStepSize(1);
        mCircleSelector.setTouchEnabled(true);
        mCircleSelector.setElevation(1);
        mCircleSelector.setmDoubleTapEnabled(false);
    }
}
