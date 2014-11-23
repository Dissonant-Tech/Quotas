package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
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

    public String getStartTime() {
        return ((Button) this.findViewById(R.id.starttime_button)).getText().toString();
    }

    public String getEndTime() {
        return ((Button) this.findViewById(R.id.endtime_button)).getText().toString();
    }

    public void setListeners(OnClickListener listener) {
        this.setOnClickListener(listener);
    }
}
