package com.dissonant.quotas.controllers;

import android.view.View;
import android.widget.CompoundButton;


public class VisibilityToggle implements CompoundButton.OnCheckedChangeListener {

    private View toggledView;

    public VisibilityToggle(View toggledView) {
        this.toggledView = toggledView;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            toggledView.setVisibility(View.VISIBLE);
        } else {
            toggledView.setVisibility(View.GONE);
        }
    }

    /**
     * @param toggledView the toggledView to set
     */
    public void setToggledView(View toggledView) {
        this.toggledView = toggledView;
    }
}
