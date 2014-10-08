package com.dissonant.quotas;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

import com.dissonant.quotas.ui.DoughnutSelector;

public class EditActivity extends Activity {
    private Button sTimeButton;
    private Button eTimeButton;
    private Spinner colorSpinner;

    private Integer[] colorArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Parse int[] resource to Integer[]
        colorArray = getIntegerArray(getResources().getIntArray(R.array.default_color_array));

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Create onClickListener for buttons, load colorArray spinner
        addButtonListeners();
        setupColorSpinner();
        setupDurationPicker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
            View v;

            public TimePickerFragment(View v) {
                this.v = v;
            }

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (v.getId() == R.id.edit_start_time) {
                    Button startTimeButton = (Button) findViewById(R.id.edit_start_time);
                    startTimeButton.setText(getTimeAsString(hourOfDay, minute));
                }
                else if (v.getId() == R.id.edit_end_time) {
                    Button endTimeButton = (Button) findViewById(R.id.edit_end_time);
                    endTimeButton.setText(getTimeAsString(hourOfDay, minute));
                }
            }
    }

    public void addButtonListeners() {
        sTimeButton = (Button) findViewById(R.id.edit_start_time);

        sTimeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DialogFragment startTimeFragment = new TimePickerFragment(v);
                startTimeFragment.show(getFragmentManager(), "startTimePicker");
            }
        });

        eTimeButton = (Button) findViewById(R.id.edit_end_time);

        eTimeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DialogFragment endTimeFragment = new TimePickerFragment(v);
                endTimeFragment.show(getFragmentManager(), "endTimePicker");
            }
        });
    }

    public class ColorSpinnerAdapter extends ArrayAdapter<Integer>
            implements SpinnerAdapter {

        Context context;
        int layoutId;
        Integer[] colorArray;

        public ColorSpinnerAdapter(Context context, int layoutId, Integer[] colorArray) {
            super(context,R.layout.color_spinner, colorArray);
            this.context = context;
            this.colorArray = colorArray;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            super.getDropDownView(position, convertView, parent);
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.color_spinner, parent, false);

                row.setBackgroundColor(colorArray[position]);
            } else {
                row.setBackgroundColor(colorArray[position]);
            }
            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.color_spinner, parent, false);

                row.setBackgroundColor(colorArray[position]);
            } else {
                row.setBackgroundColor(colorArray[position]);
            }
            return row;
        }
    }

    public void setupColorSpinner() {
        colorSpinner = (Spinner) findViewById(R.id.edit_color_spinner);
        colorSpinner.setAdapter(new ColorSpinnerAdapter(this, R.layout.color_spinner, colorArray));
    }

    public String getTimeAsString(int hourOfDay, int minute) {
        StringBuilder result;
        String format;

        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        result = new StringBuilder().append(hourOfDay).append(":").append(minute)
            .append(" ").append(format);

        return result.toString();
    }

    public Integer[] getIntegerArray(int[] intArray) {
        Integer[] integerArray = new Integer[intArray.length];
        int counter = 0;

        for (int i : intArray) {
            integerArray[counter] = i;
            counter++;
        }
        return integerArray;
    }

    public void setupDurationPicker() {
        DoughnutSelector mDoughnutSelector = (DoughnutSelector) findViewById(R.id.doughnutSelector);
        mDoughnutSelector.setColor(Color.CYAN);
        mDoughnutSelector.setFormatDigits(0);
        mDoughnutSelector.setUnit("");
        mDoughnutSelector.showValue(0, 60, false);
        mDoughnutSelector.setStepSize(1);
        mDoughnutSelector.setTouchEnabled(true);
        mDoughnutSelector.setElevation(1);
        mDoughnutSelector.setmDoubleTapEnabled(false);
    }
}
