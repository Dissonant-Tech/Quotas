package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dissonant.quotas.R;
import com.dissonant.quotas.utils.BasicTextValidator;

public class EditView extends LinearLayout {

    private TextView title;
    private TextView description;
    private Button startTime;
    private Button endTime;
    private LinearLayout duration;

    public EditView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        title = (TextView) findViewById(R.id.edit_title);
        description = (TextView) findViewById(R.id.edit_description);
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public String getDescription() {
        return description.getText().toString();
    }

    public String getStartTime() {
        return startTime.getText().toString();
    }

    public void setTitleValidator(BasicTextValidator validator) {
        title.addTextChangedListener(validator);
    }
}
