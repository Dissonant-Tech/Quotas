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
import android.widget.TextView;

import com.dissonant.quotas.ui.adapters.ColorSpinnerAdapter;
import com.dissonant.quotas.ui.dialogs.TimeRangeDialogFragment;

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
        colorArray = getAsIntegerArray(getResources()
                .getIntArray(R.array.default_color_array));
        String[] colorNameArray = getResources()
            .getStringArray(R.array.color_name_array);
        TextView target = (TextView) findViewById(R.id.color_value);
        ColorSpinnerAdapter mSpinnerAdapter = new ColorSpinnerAdapter(this,
                    R.layout.color_spinner, colorArray,
                    colorNameArray, target);
        colorSpinner.setAdapter(mSpinnerAdapter);
        colorSpinner.setOnItemSelectedListener(mSpinnerAdapter);
    }

    public void onRepeatToggled(View v) {
        boolean on = ((Switch) v).isChecked();
        TextView repeatText = (TextView) findViewById(R.id.repeat_value);

        if (on) {
            repeatText.setText(R.string.on);
            repeatOptions.setVisibility(View.VISIBLE);
        } else {
            repeatText.setText(R.string.off);
            repeatOptions.setVisibility(View.GONE);
        }
    }

    public void openTimeRangeDialog(View v) {
        TimeRangeDialogFragment trDialog = new TimeRangeDialogFragment();
        trDialog.show(getFragmentManager(), "timerange");
    }

    public void openDurationDialog(View v) {
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
