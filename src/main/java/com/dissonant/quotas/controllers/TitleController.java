package com.dissonant.quotas.controllers;

import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.ui.views.EditView;

public class TitleController implements OnFocusChangeListener {

	private Context mContext;
	private EditView mView;
	private QuotaModel mQuota;

    public TitleController(Context context, EditView view, QuotaModel quota) { 
		mContext = context;
		mView = view;
		mQuota = quota;

        mView.getTitleView().setOnFocusChangeListener(this);

    }

    public void onFocusChange(View view, boolean hasFocus) {
        TextView textView = (TextView) view;
        boolean isValid;
        isValid = mQuota.setTitle(textView.getText().toString());

        if (!isValid) {
            textView.setError( textView.getHint() + " is required!");
        } else {
            textView.setError(null);
        }
    }
}
