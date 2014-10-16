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
import com.dissonant.quotas.ui.views.DoughnutSelector;


public class DoughnutDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get dialog bulder and inflate the dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.dialog_doughnut_selector, null);

        // Pass the inflated view and initialize the DoughnutSelector
        initDoughnutSelector(mDialogView);

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

    public void initDoughnutSelector(View v) {
        DoughnutSelector mDoughnutSelector = (DoughnutSelector) v.findViewById(R.id.doughnutSelector);
        mDoughnutSelector.setColor(Color.CYAN);
        mDoughnutSelector.setFormatDigits(0);
        mDoughnutSelector.setUnit("");
        mDoughnutSelector.showValue(0, 60, false);
        mDoughnutSelector.setStepSize(1);
        mDoughnutSelector.setTouchEnabled(true);
        mDoughnutSelector.setElevation(1);
        mDoughnutSelector.setmDoubleTapEnabled(false);
    }
}
