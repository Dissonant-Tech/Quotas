package com.dissonant.quotas;

import java.sql.Time;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dissonant.quotas.controllers.BackgroundToggle;
import com.dissonant.quotas.controllers.TimePickerController;
import com.dissonant.quotas.controllers.VisibilityToggle;
import com.dissonant.quotas.db.models.QuotaModel;
import com.dissonant.quotas.ui.dialogs.ColorPickerDialog;
import com.dissonant.quotas.ui.dialogs.TimePickerFragment.TimePickerDialogListener;
import com.dissonant.quotas.ui.dialogs.TimeRangeDialogFragment;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.BasicTextValidator;
import com.dissonant.quotas.utils.Utils;

public class EditActivity extends Activity
    implements TimePickerDialogListener {

    final QuotaModel quota = new QuotaModel();
    BasicTextValidator titleValidator;

    EditView editView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editView = (EditView) View.inflate(this, R.layout.activity_edit, null);
        setContentView(editView);

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
        editView.getColorPicker().setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog dialog = new ColorPickerDialog(v);
                dialog.show(getFragmentManager(), "colorpicker");
            }
        });

        Switch repeatSwitch = (Switch) findViewById(R.id.toggle);
        final LinearLayout repeatOptions = (LinearLayout) findViewById(R.id.toggle_list);
        repeatSwitch.setOnCheckedChangeListener(new VisibilityToggle((View) findViewById(R.id.toggle_list)));

        Drawable repeatBgDrawable = getDrawable(R.drawable.circle);
        Drawable repeatBgDrawableSelected = getDrawable(R.drawable.circle);
        repeatBgDrawableSelected.setColorFilter(Color.BLUE, Mode.SRC_IN);

        BackgroundToggle repeatToggleListener = new BackgroundToggle(repeatBgDrawable, repeatBgDrawableSelected);

        for (int i = 0; i < repeatOptions.getChildCount(); i++) {
            ((ToggleButton)repeatOptions.getChildAt(i)).setOnCheckedChangeListener(repeatToggleListener);
        }

        //LinearLayout timeDuration = (LinearLayout) findViewById(R.id.duration_layout);
//      timeDuration.setOnClickListener( new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              if (isTimeSet()) {
//                  openTimeRangeDialog(v, quota.getStartTime(), quota.getEndTime());
//              } else {
//                  Toast.makeText(EditActivity.this, getResources()
//                      .getString(R.string.time_not_set), Toast.LENGTH_SHORT).show();
//              }
//          }
//      });
//
        // Open TimePicker, update button text and quota startTime
        editView.getStartTime().setOnClickListener(new TimePickerController(this, this));

        // Open TimePicker, update button text and quota endTime
        editView.getEndTime().setOnClickListener(new TimePickerController(this, this));

        TextView titleText = (TextView) findViewById(R.id.title);
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

    public void onFinishedTimeSet(View view, int hourOfDay, int minute) {
        if (editView.getStartTime().getId() == view.getId()) {
            quota.setStartTime(Utils.getTimeFromInt(hourOfDay, minute));
            editView.setStartTime(Utils.getTimeAsString(quota.getStartTime(), "hh:mm a"));
        } else if (editView.getEndTime().getId() == view.getId()) {
            quota.setEndTime(Utils.getTimeFromInt(hourOfDay, minute));
            editView.setEndTime(Utils.getTimeAsString(quota.getEndTime(), "hh:mm a"));
        }
    }

    public void openTimeRangeDialog(View view, Time startTime, Time endTime) {
        TimeRangeDialogFragment trDialog = new TimeRangeDialogFragment(startTime, endTime);
        trDialog.show(getFragmentManager(), "timerange");
    }
}
