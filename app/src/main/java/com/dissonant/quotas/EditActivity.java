package com.dissonant.quotas;

import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.dissonant.quotas.ui.DoughnutSelector;
import com.dissonant.quotas.ui.adapters.ColorSpinnerAdapter;
import com.dissonant.quotas.ui.dialogs.DoughnutDialogFragment;
import com.dissonant.quotas.ui.dialogs.TimePickerFragment;
import com.dissonant.quotas.ui.dialogs.TimeRangeDialogFragment;

public class EditActivity extends Activity {
    private Button sTimeButton;
    private Button eTimeButton;
    private Spinner colorSpinner;

    private Calendar mStartTime;
    private Calendar mEndTime;

    private Integer[] colorArray;
    private ImageButton fabButton;
    private DoughnutSelector mTimeRangeChart;
    private DoughnutSelector mDurationChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        initView();

        // Create click listeners for buttons
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void initView() {
        // get views
        fabButton = (ImageButton)findViewById(R.id.fab);
        colorSpinner = (Spinner) findViewById(R.id.edit_color_spinner);
        mTimeRangeChart = (DoughnutSelector) findViewById(R.id.TimeRangeChart);

        // draw FAB Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline mOutline = new Outline();
        mOutline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(mOutline);

        // Parse int[] resource to Integer[], set colorSpinner colors
        colorArray = getIntegerArray(getResources().getIntArray(R.array.default_color_array));
        colorSpinner.setAdapter(new ColorSpinnerAdapter(this, R.layout.color_spinner, colorArray));

        // Setup TimeRangeChart
        mTimeRangeChart.setTouchEnabled(false);
        mTimeRangeChart.setDrawText(false);
        mTimeRangeChart.setElevation(1);
        mTimeRangeChart.setColor(getResources().getColor(R.color.primary_dark));
    }

    public void initListeners() {
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

        // TimeRangeChart Dialog
        mTimeRangeChart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showTimeRangeChartDialog();
            }
        });
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

    /*
     * show Dialog methods
     */

    public void showStartTimeDialog(View v) {
        TimePickerFragment startTimeFragment = new TimePickerFragment(v);
        startTimeFragment.show(getFragmentManager(), "startTimePicker");
    }

    public void showEndTimeDialog(View v) {
        TimePickerFragment endTimeFragment = new TimePickerFragment(v);
        endTimeFragment.show(getFragmentManager(), "endTimePicker");
    }

    public void showDoughnutDialog() {
        DialogFragment mDoughnutFragment = new DoughnutDialogFragment();
        mDoughnutFragment.show(getFragmentManager(), "doughnutSelector");
    }

    public void showTimeRangeChartDialog() {
        DialogFragment mTimeRangeDialogFragment = new TimeRangeDialogFragment();
        mTimeRangeDialogFragment.show(getFragmentManager(), "TimeRangeChart");
    }
}
