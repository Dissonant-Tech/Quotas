package com.dissonant.quotas.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dissonant.quotas.R;

public class ColorSpinnerAdapter extends ArrayAdapter<Integer>
    implements SpinnerAdapter {

    Context context;
    int layoutId;
    Integer[] colorArray;

    // Optional fields
    String[] colorNameArray;
    TextView updateTarget;
    boolean updateView = false;

    public ColorSpinnerAdapter(Context context, int layoutId, Integer[] colorArray) {
        super(context, R.layout.color_spinner, colorArray);
        this.context = context;
        this.colorArray = colorArray;
    }
    
    public ColorSpinnerAdapter(Context context, int layoutId, 
            Integer[] colorArray, String[] colorNameArray, TextView updateTarget) {
        super(context, R.layout.color_spinner, colorArray);

        this.context = context;
        this.colorArray = colorArray;
        this.colorNameArray = colorNameArray;
        this.updateTarget = updateTarget;
        this.updateView = true;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        super.getDropDownView(position, convertView, parent);
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) 
                context.getSystemService( Context.LAYOUT_INFLATER_SERVICE  );
            row = inflater.inflate(R.layout.color_spinner, parent, false);

            update(row, position);
        } else {
            update(row, position);
        }
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) 
                context.getSystemService( Context.LAYOUT_INFLATER_SERVICE  );
            row = inflater.inflate(R.layout.color_spinner, parent, false);

            update(row, position);
        } else {
            update(row, position);
        }
        return row;
    }

    public void update(View row, int position) {
        row.setBackgroundColor(colorArray[position]);

        if (updateView) {
            updateTarget.setText(colorNameArray[position]);
        }
    }
}
