package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.dissonant.quotas.R;
import com.dissonant.quotas.utils.BasicTextValidator;

public class EditView extends LinearLayout {

    private LinearLayout duration;
    private LinearLayout colorPicker;

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

    public void setTitleValidator(BasicTextValidator validator) {
        ((TextView) this.findViewById(R.id.title)).addTextChangedListener(validator);
    }

    public void setStartTimeListener(OnClickListener listener) {
        ((Button) this.findViewById(R.id.starttime_button)).setOnClickListener(listener);
    }

    public void setEndTimeListener(OnClickListener listener) {
        ((Button) this.findViewById(R.id.endtime_button)).setOnClickListener(listener);
    }
}
