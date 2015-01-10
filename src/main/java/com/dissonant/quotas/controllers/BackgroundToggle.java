package com.dissonant.quotas.controllers;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;

public class BackgroundToggle implements CompoundButton.OnCheckedChangeListener {

    private Drawable bgDrawable;
    private Drawable bgDrawableSelected;

    public BackgroundToggle(Drawable bgDrawable, Drawable bgDrawableSelected) { 
        this.bgDrawable = bgDrawable;
        this.bgDrawableSelected = bgDrawableSelected;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            buttonView.setBackground(bgDrawableSelected);
        } else {
            buttonView.setBackground(bgDrawable);
        }
    }

    /**
     * @return the bgDrawable
     */
    public Drawable getBgDrawable() {
        return bgDrawable;
    }

    /**
     * @param bgDrawable the bgDrawable to set
     */
    public void setBgDrawable(Drawable bgDrawable) {
        this.bgDrawable = bgDrawable;
    }

    /**
     * @return the bgDrawableSelected
     */
    public Drawable getBgDrawableSelected() {
        return bgDrawableSelected;
    }

    /**
     * @param bgDrawableSelected the bgDrawableSelected to set
     */
    public void setBgDrawableSelected(Drawable bgDrawableSelected) {
        this.bgDrawableSelected = bgDrawableSelected;
    }
}
