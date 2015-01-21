package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog.ColorPickerListener;
import com.dissonant.quotas.ui.views.EditView;


public class ColorPickerController implements View.OnClickListener, ColorPickerListener {

    private  static final String TAG = "ColorPickerController";

    private Context mContext;
    private EditView mView;
    private QuotaModel mQuota;

    public ColorPickerController(Context context, EditView view, QuotaModel quota) {
        mContext = context;
        mView = view;
        mQuota = quota;
    }

    @Override
    public void onClick(View v) {
        ColorPickerDialog dialog =  new ColorPickerDialog(this);
        dialog.show(((Activity) mContext).getFragmentManager(), "colorPicker");
    }

    @Override
    public void onColorSet(String name, int color) {
        mView.setColorPicked(name, color);
        mQuota.setColor(color);
        Log.i(TAG, "color= " + mQuota.getColor());
    }
}
