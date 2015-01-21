package com.dissonant.quotas.controllers;

import android.content.Context;

import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.BasicTextValidator;

public class TitleController {

	private Context mContext;
	private EditView mView;
	private QuotaModel mQuota;

    BasicTextValidator mValidator;

    public TitleController(Context context, EditView view, QuotaModel quota) { 
		mContext = context;
		mView = view;
		mQuota = quota;
    }
}
