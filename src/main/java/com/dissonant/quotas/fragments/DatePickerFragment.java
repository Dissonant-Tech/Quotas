package com.dissonant.quotas.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class DatePickerFragment extends DialogFragment {

    private Context mContext;
    private OnDateSetListener mListener;
    private int mYear;
    private int mMonth;
    private int mDay;

    public DatePickerFragment(Context context, OnDateSetListener listener,
            int year, int month, int day ) {
        mContext = context;
        mListener = listener;
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(mContext, mListener, mYear, mMonth, mDay);
    }
}
