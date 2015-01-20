package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog.ColorPickerListener;
import com.dissonant.quotas.ui.views.EditView;


public class ColorPickerController implements View.OnClickListener, 
       ColorPickerListener {

    private Context mContext;
    private ColorPickerListener mListener;
    private EditView mView;

    public ColorPickerController(Context context, ColorPickerListener listener, 
            EditView view) {
        mContext = context;
        mListener = listener;
        mView = view;
    }

    @Override
    public void onClick(View v) {
        ColorPickerDialog dialog =  new ColorPickerDialog(mListener);
        dialog.show(((Activity) mContext).getFragmentManager(), "colorPicker");
    }

    @Override
    public void onColorSet(String name, int color) {
        mView.setColorPicked(name, color);
    }
}
