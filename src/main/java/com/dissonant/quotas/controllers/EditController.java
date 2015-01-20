package com.dissonant.quotas.controllers;

import java.util.Calendar;

import android.content.Context;
import android.widget.TextView;

import com.dissonant.quotas.controllers.TimeRangeController.TimeRangeListener;
import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.model.RecurrenceModel;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog.ColorPickerListener;
import com.dissonant.quotas.ui.dialogs.RecurrencePickerDialog.RecurrencePickerListener;
import com.dissonant.quotas.ui.views.CircleSelector;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.BasicTextValidator;

public class EditController implements ColorPickerListener, 
       TimeRangeListener, RecurrencePickerListener {

    public interface EditListener {
        void onSaved();
    }

    private Context mContext;
    private EditListener mListener;
    private EditView mView;
    private QuotaModel mQuota;

    BasicTextValidator titleValidator;

    public EditController(Context context, EditListener listener, EditView view) {
        mContext = context;
        mListener = listener;
        mView = view;

        attachListeners();
    }

    public void attachListeners() {
        // Calls onColorSet
        mView.getColorPicker().setOnClickListener(
                new ColorPickerController(mContext, this, mView));

        mView.getRepeat().setOnClickListener(
                new RecurrencePickerController(mContext, this));

        mView.getTimeRange().setOnClickListener(
                new TimeRangeController(mContext, this, mView));

        titleValidator = new BasicTextValidator((TextView) mView.getTitleView());
        ((TextView) mView.getTitleView()).addTextChangedListener(titleValidator);
    }

    public boolean validate() {
        if (!titleValidator.isValid()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onTimeRangeSet(Calendar startTime, Calendar endTime, float val, float maxVal) {
        CircleSelector timeRangeView = (CircleSelector) mView.getTimeRange();
        timeRangeView.showValue(val, maxVal, false);

        mQuota.setStartTime(startTime);
        mQuota.setEndTime(endTime);
    }

    @Override
    public void onColorSet(String name, int color) {
        mQuota.setColor(color);
    }

    @Override
    public void onRecurrenceSet(RecurrenceModel model) {

    }
}
