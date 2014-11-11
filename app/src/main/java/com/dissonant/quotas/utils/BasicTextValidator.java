package com.dissonant.quotas.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

public class BasicTextValidator extends TextValidator
    implements OnFocusChangeListener {
    boolean isValid = false;

    public BasicTextValidator(TextView textView) {
        super(textView);
    }

    @Override
    public void validate(TextView textView, String text) {
        if (TextUtils.isEmpty(textView.getText().toString())) {
            textView.setError( textView.getHint() + " is required!");
            isValid = false;
        } else {
            textView.setError(null);
            isValid = true;
        }
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }

    public void onFocusChange(View textView, boolean hasFocus) {
        if (!hasFocus) {
            validate((TextView)textView, "");
        }
    }
}
