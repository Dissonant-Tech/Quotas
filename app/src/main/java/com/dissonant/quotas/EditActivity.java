package com.dissonant.quotas;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Outline;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

import com.dissonant.quotas.ui.DoughnutSelector;


public class EditActivity extends Activity {
    private Button sTimeButton;
    private Button eTimeButton;
    private Spinner colorSpinner;

    private Calendar mStartTime;
    private Calendar mEndTime;

    private Integer[] colorArray;
    private ImageButton fabButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        
        initFab();

        // Parse int[] resource to Integer[]
        colorArray = getIntegerArray(getResources().getIntArray(R.array.default_color_array));

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Create onClickListener for buttons, load colorArray spinner
        addButtonListeners();
        initColorSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void initColorSpinner() {
        colorSpinner = (Spinner) findViewById(R.id.edit_color_spinner);
        colorSpinner.setAdapter(new ColorSpinnerAdapter(this, R.layout.color_spinner, colorArray));
    }
    
    public void initFab() {
        fabButton = (ImageButton)findViewById(R.id.fab);

        // FAB Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline mOutline = new Outline();
        mOutline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(mOutline);
    }


    public void addButtonListeners() {
        // StartTime Dialog
        sTimeButton = (Button) findViewById(R.id.edit_start_time);
        sTimeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showStartTimeDialog(v);
            }
        });

        // EndTime Dialog
        eTimeButton = (Button) findViewById(R.id.edit_end_time);
        eTimeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showEndTimeDialog(v);
            }
        });

        // DoughnutSelector Dialog
        Button durationButton = (Button) findViewById(R.id.edit_duration_time);
        durationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDoughnutDialog();
            }
        });
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

    public void initDoughnutSelector(View v) {
        DoughnutSelector mDoughnutSelector = (DoughnutSelector) v.findViewById(R.id.doughnutSelector);
        mDoughnutSelector.setColor(Color.CYAN);
        mDoughnutSelector.setFormatDigits(0);
        mDoughnutSelector.setUnit("");
        mDoughnutSelector.showValue(0, 60, false);
        mDoughnutSelector.setStepSize(1);
        mDoughnutSelector.setTouchEnabled(true);
        mDoughnutSelector.setElevation(1);
        mDoughnutSelector.setmDoubleTapEnabled(false);
    }

    public void genDefaultDuration() {
        if (mStartTime.isSet(Calendar.HOUR_OF_DAY)
                && mEndTime.isSet(Calendar.HOUR_OF_DAY)) {
            long milliSeconds = mStartTime.getTimeInMillis() - mEndTime.getTimeInMillis();
            long hours = milliSeconds/(1000*60*60);
            long mins = milliSeconds%(1000*60*60);
        }
    }

    /*
     * show Dialog methods
     */

    public void showStartTimeDialog(View v) {
        DialogFragment startTimeFragment = new TimePickerFragment(v);
        startTimeFragment.show(getFragmentManager(), "startTimePicker");
    }

    public void showEndTimeDialog(View v) {
        DialogFragment endTimeFragment = new TimePickerFragment(v);
        endTimeFragment.show(getFragmentManager(), "endTimePicker");
    }

    public void showDoughnutDialog() {
        DialogFragment mDoughnutFragment = new DoughnutDialogFragment();
        mDoughnutFragment.show(getFragmentManager(), "doughnutSelector");
    }

    /*
     * Dialog Fragment and Spinner classes
     */

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
                    mStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mStartTime.set(Calendar.MINUTE, minute);
                }
                else if (v.getId() == R.id.edit_end_time) {
                    Button endTimeButton = (Button) findViewById(R.id.edit_end_time);
                    endTimeButton.setText(getTimeAsString(hourOfDay, minute));
                    mEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mEndTime.set(Calendar.MINUTE, minute);
                }
                genDefaultDuration();
            }
    }

    public class DoughnutDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get dialog bulder and inflate the dialog layout
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View mDialogView = inflater.inflate(R.layout.dialog_doughnut_selector, null);

            // Pass the inflated view and initialize the DoughnutSelector
            initDoughnutSelector(mDialogView);

            builder.setView(mDialogView)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // save clicked
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DoughnutDialogFragment.this.getDialog().cancel();
                    }
            });

            // Create the AlertDialog and return it
            return builder.create();
        }
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
}
