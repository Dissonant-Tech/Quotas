package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.dissonant.quotas.ui.dialogs.ColorPickerFragment;
import com.dissonant.quotas.ui.dialogs.ColorPickerFragment.ColorPickerListener;

public class ColorPickerController implements OnClickListener {

    private Context context;
    private ColorPickerListener listener;

    public ColorPickerController(Context context, ColorPickerListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        ColorPickerFragment dialog = new ColorPickerFragment(this.listener);
        dialog.show(((Activity) this.context).getFragmentManager(), "colorPicker");
    }
}
