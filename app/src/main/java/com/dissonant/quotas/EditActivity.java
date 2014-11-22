package com.dissonant.quotas;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dissonant.quotas.db.models.QuotaModel;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.TimePickerFragment;
import com.dissonant.quotas.ui.dialogs.TimeRangeDialogFragment;
import com.dissonant.quotas.utils.BasicTextValidator;
import com.dissonant.quotas.utils.Utils;

public class EditActivity extends Activity {
    final QuotaModel quota = new QuotaModel();
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
        final LinearLayout repeatOptions = (LinearLayout) findViewById(R.id.repeat_options);
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

        List<ToggleButton> repeatList = new ArrayList<ToggleButton>();
        final Drawable repeatBgDrawable = getDrawable(R.drawable.circle);
        final Drawable repeatBgDrawableSelected = getDrawable(R.drawable.circle);
        repeatBgDrawableSelected.setColorFilter(Color.BLUE, Mode.SRC_IN);

        for (int i = 0; i < repeatOptions.getChildCount(); i++) {
            repeatList.add((ToggleButton)repeatOptions.getChildAt(i));
        }

        CompoundButton.OnCheckedChangeListener
            repeatToggleListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged( CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setBackground(repeatBgDrawableSelected);
                    } else {
                        buttonView.setBackground(repeatBgDrawable);
                    }
                }
            };

        for (ToggleButton toggle : repeatList) {
            toggle.setOnCheckedChangeListener(repeatToggleListener);
        }

        LinearLayout timeDuration = (LinearLayout) findViewById(R.id.duration_layout);
        timeDuration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimeSet()) {
                    openTimeRangeDialog(v, quota.getStartTime(), quota.getEndTime());
                } else {
                    Toast.makeText(EditActivity.this, getResources()
                        .getString(R.string.time_not_set), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Open TimePicker, update button text and quota startTime
        final Button startTimeButton = (Button) findViewById(R.id.starttime_button);
        startTimeButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                OnTimeSetListener startTimeListener = new OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        quota.setStartTime(Utils.getTimeFromInt(hourOfDay, minute));
                        startTimeButton.setText(Utils.getTimeAsString(quota.getStartTime(), "hh:mm a"));
                    }
                };
                openTimePickerDialog(v, startTimeListener);
            }
        });

        // Open TimePicker, update button text and quota endTime
        final Button endTimeButton = (Button) findViewById(R.id.endtime_button);
        endTimeButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                OnTimeSetListener startTimeListener = new OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        quota.setEndTime(Utils.getTimeFromInt(hourOfDay, minute));
                        endTimeButton.setText(Utils.getTimeAsString(quota.getEndTime(), "hh:mm a"));
                    }
                };
                openTimePickerDialog(v, startTimeListener);
            }
        });

        TextView titleText = (TextView) findViewById(R.id.edit_title);
        titleValidator = new BasicTextValidator(titleText);
        titleText.addTextChangedListener(titleValidator);
    }

    public boolean validate() {
        if (!titleValidator.isValid()) {
            return false;
        } else if (!isTimeSet()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isTimeSet() {
        if (quota.getStartTime() == null ||
                quota.getEndTime() == null ) {
            return false;
        } else {
            return true;
        }
    }

    public void openTimeRangeDialog(View view, Time startTime, Time endTime) {
        TimeRangeDialogFragment trDialog = new TimeRangeDialogFragment(startTime, endTime);
        trDialog.show(getFragmentManager(), "timerange");
    }

    public void openTimePickerDialog(View view, OnTimeSetListener tListener) {
        TimePickerFragment tPickerFragment = new TimePickerFragment(view, tListener);
        tPickerFragment.show(getFragmentManager(), "time");
    }
}
