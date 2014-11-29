package com.dissonant.quotas.ui.adapters;

import java.util.ArrayList;

import com.dissonant.quotas.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class SwapSpinnerAdapter extends ArrayAdapter<CharSequence> {

    private LayoutInflater mInflater;
    private int mItemResourceId;
    private int mTextResourceId;
    private ArrayList<CharSequence> mListedStrings;
    private ArrayList<CharSequence> mSelectedStrings;


    public SwapSpinnerAdapter(Context context, ArrayList<CharSequence> listedStrings,
            ArrayList<CharSequence> selectedStrings, int itemResourceId, int textResourceId) {
        super(context, itemResourceId, listedStrings);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemResourceId = itemResourceId;
        mTextResourceId = textResourceId;
        mListedStrings = listedStrings;
        mSelectedStrings = selectedStrings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        // Check if we can recycle the view
        if (convertView == null) {
            v = mInflater.inflate(mTextResourceId, parent, false);
        } else {
            v = convertView;
        }

        TextView item = (TextView) v.findViewById(R.id.spinner_item);
        item.setText(mSelectedStrings.get(position));

        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v;
        // Check if we can recycle the view
        if (convertView == null) {
            v = mInflater.inflate(mItemResourceId, parent, false);
        } else {
            v = convertView;
        }

        TextView item = (TextView) v.findViewById(R.id.spinner_item);
        item.setText(mListedStrings.get(position));

        return v;
    }
}
