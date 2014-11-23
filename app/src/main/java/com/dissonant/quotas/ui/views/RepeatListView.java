package com.dissonant.quotas.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.dissonant.quotas.R;

public class RepeatListView extends LinearLayout {

	private Context context;
	private AttributeSet attrs;

    public RepeatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
		this.context = context;
		this.attrs = attrs;
    }

    public void setToggleListener(OnCheckedChangeListener listener) {
        ((Switch) this.findViewById(R.id.toggle)).setOnCheckedChangeListener(listener);
    }
}
