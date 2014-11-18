package com.dissonant.quotas;

import android.app.Activity;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.TimePickerFragment;
import com.dissonant.quotas.ui.dialogs.TimeRangeDialogFragment;
import com.dissonant.quotas.ui.listeners.TimePickerListener;
import com.dissonant.quotas.utils.BasicTextValidator;

public class EditActivity extends Activity {
    BasicTextValidator titleValidator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        createListeners();

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (validate()) {
                Toast.makeText(this, getResources()
                        .getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources()
                        .getString(R.string.save_failed), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finishAfterTransition();
    }

    public void createListeners() {
        LinearLayout colorPicker = (LinearLayout) findViewById(R.id.colorpicker);
        colorPicker.setOnClickListener( new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog cpDialog = new ColorPickerDialog();
                cpDialog.show(getFragmentManager(), "colorpicker");
            }
        });

        Switch repeatSwitch = (Switch) findViewById(R.id.repeat_switch);
        final View repeatOptions = (View) findViewById(R.id.repeat_options);
        repeatSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(
                        CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // toggle is enabled
                            repeatOptions.setVisibility(View.VISIBLE);
                        } else {
                            // toggle is disabled
                            repeatOptions.setVisibility(View.GONE);
                        }
                    }
        });

        LinearLayout timeDuration = (LinearLayout) findViewById(R.id.duration_layout);
        timeDuration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validTimes()) {
                    openTimeRangeDialog(v);
                } else {
                    Toast.makeText(EditActivity.this, getResources()
                        .getString(R.string.invalid_times), Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView titleText = (TextView) findViewById(R.id.edit_title);
        titleValidator = new BasicTextValidator(titleText);
        titleText.addTextChangedListener(titleValidator);

    }

    public boolean validate() {
        if (titleValidator.isValid()) {
            return true;
        }
        return false;
    }

    public boolean validTimes() {
        return false;
    }

    public void openTimeRangeDialog(View view) {
        TimeRangeDialogFragment trDialog = new TimeRangeDialogFragment();
        trDialog.show(getFragmentManager(), "timerange");
    }

    public void openTimePickerDialog(View view, OnTimeSetListener timePickerListener, String title) {
        TimePickerFragment tPickerFragment = new TimePickerFragment(view, timePickerListener, title);
        tPickerFragment.show(getFragmentManager(), "time");
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
