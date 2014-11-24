package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dissonant.quotas.R;

public class EditView extends LinearLayout {

    public EditView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public String getTitle() {
        return ((TextView) this.findViewById(R.id.title)).getText().toString();
    }

    public String getDescription() {
        return ((TextView) this.findViewById(R.id.description)).getText().toString();
    }

    /*
     * Start and End Time buttons
     */

    public View getStartTime() {
        return this.findViewById(R.id.starttime_button);
    }

    public View getEndTime() {
        return this.findViewById(R.id.endtime_button);
    }

    public void setStartTime(String text) {
        ((Button) this.findViewById(R.id.starttime_button)).setText(text);
    }

    public void setEndTime(String text) {
        ((Button) this.findViewById(R.id.endtime_button)).setText(text);
    }

    /**
     * Color Picker
     */

    public View getColorPicker() {
        return this.findViewById(R.id.colorpicker);
    }
}
