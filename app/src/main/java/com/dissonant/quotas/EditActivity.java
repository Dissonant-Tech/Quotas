package com.dissonant.quotas;

import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.dissonant.quotas.ui.adapters.ColorSpinnerAdapter;

public class EditActivity extends Activity {
    private Spinner colorSpinner;

    private Integer[] colorArray;
    private ImageButton fabButton;
    private Switch repeatSwitch;
    private View repeatOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


        // Create click listeners for buttons
        createClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void initView() {
        // get views
        fabButton = (ImageButton)findViewById(R.id.fab);
        colorSpinner = (Spinner) findViewById(R.id.edit_color_spinner);
        repeatSwitch = (Switch) findViewById(R.id.repeat_switch);
        repeatOptions = (View) findViewById(R.id.repeat_options);

        // draw FAB Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline mOutline = new Outline();
        mOutline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(mOutline);

        // Parse int[] resource to Integer[], set colorSpinner colors
        colorArray = getAsIntegerArray(getResources().getIntArray(R.array.default_color_array));
        colorSpinner.setAdapter(new ColorSpinnerAdapter(this, R.layout.color_spinner, colorArray));

    }

    public void createClickListeners() {
    }

    public void onRepeatToggled(View v) {
        boolean on = ((Switch) v).isChecked();

        if (on) {
            repeatOptions.setVisibility(View.VISIBLE);
        } else {
            repeatOptions.setVisibility(View.GONE);
        }
    }

    public Integer[] getAsIntegerArray(int[] intArray) {
        Integer[] integerArray = new Integer[intArray.length];
        int counter = 0;

        for (int i : intArray) {
            integerArray[counter] = i;
            counter++;
        }
        return integerArray;
    }

}
