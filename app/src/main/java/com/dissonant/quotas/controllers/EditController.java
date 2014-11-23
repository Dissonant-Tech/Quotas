package com.dissonant.quotas.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.views.EditView;

public class EditController implements OnClickListener {

    private final String TAG = "Edit Controller";

    private EditView editView;
    private Context context;

    public EditController(Context context, EditView editView){
        this.context = context;
        this.editView = editView;
    }

    @Override
    public void onClick(View v) {
    }
}
