package com.dissonant.quotas;

import java.sql.Time;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.TimeRangeDialogFragment;

public class EditActivity extends Activity {

    private TextView colorNameView;
    private LinearLayout colorPicker;

    private ImageButton fabButton;
    private View repeatOptions;

    private Time startTime;
    private Time endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();
        setupView();
        createListeners();

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
        this.finishAfterTransition();
    }

    public void init() {
        fabButton = (ImageButton) findViewById(R.id.fab);
        colorPicker = (LinearLayout) findViewById(R.id.colorpicker);
        repeatOptions = (View) findViewById(R.id.repeat_options);
    }

    public void setupView() {
    }

    public void createListeners() {
        colorPicker.setOnClickListener( new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog cpDialog = new ColorPickerDialog();
                cpDialog.show(getFragmentManager(), "colorpicker");
            }
        });

        Switch repeatSwitch = (Switch) findViewById(R.id.repeat_switch);
        repeatSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // toggle is enabled
                            repeatOptions.setVisibility(View.VISIBLE);
                        } else {
                            // toggle is disabled
                            repeatOptions.setVisibility(View.GONE);
                        }
                    }
        });
    }

    public boolean validate() {
        TextView title = (TextView) findViewById(R.id.edit_title);
        TextView description = (TextView) findViewById(R.id.edit_description);

        if (title.getText() == "" || title.getText() == null) {
            return false;
        }
        if (description.getText() == "" || description.getText() == null) {
            return false;
        }

        return true;
    }

    public void onRepeatToggled(View v) {
    }

    public void openTimeRangeDialog(View v) {
        TimeRangeDialogFragment trDialog = new TimeRangeDialogFragment();
        trDialog.show(getFragmentManager(), "timerange");
    }

    public void openDurationDialog(View v) {
        TimeRangeDialogFragment trDialog = new TimeRangeDialogFragment();
        trDialog.show(getFragmentManager(), "duration");
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

    /**
     * @return the startTime
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

}
