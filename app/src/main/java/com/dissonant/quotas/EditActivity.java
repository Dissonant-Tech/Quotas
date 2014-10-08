package com.dissonant.quotas;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class EditActivity extends Activity {
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private TimePicker lengthTimePicker;

    private Button sTimeButton;
    private Button eTimeButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        addButtonListeners();
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
}
