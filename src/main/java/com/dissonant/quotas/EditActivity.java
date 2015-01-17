package com.dissonant.quotas;

import java.sql.Time;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dissonant.quotas.controllers.ColorPickerController;
import com.dissonant.quotas.controllers.ColorPickerController.ColorPickerListener;
import com.dissonant.quotas.controllers.RecurrencePickerController;
import com.dissonant.quotas.controllers.TimeRangeController;
import com.dissonant.quotas.controllers.TimeRangeController.TimeRangeListener;
import com.dissonant.quotas.db.models.QuotaModel;
import com.dissonant.quotas.ui.dialogs.RecurrencePickerFragment.RecurrencePickerListener;
import com.dissonant.quotas.ui.views.CircleSelector;
import com.dissonant.quotas.ui.views.EditView;
import com.dissonant.quotas.utils.BasicTextValidator;

public class EditActivity extends Activity
    implements ColorPickerListener, TimeRangeListener, RecurrencePickerListener {

    QuotaModel mQuota = new QuotaModel();
    BasicTextValidator titleValidator;

    EditView editView;
    TimeRangeController timeRangeController;

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

        // Calls onColorSet
        editView.getColorPicker().setOnClickListener(new ColorPickerController(this, this, editView));

        editView.getRepeat().setOnClickListener(new RecurrencePickerController(this, this));

        timeRangeController = new TimeRangeController(this, this, editView);
        editView.getTimeRange().setOnClickListener(timeRangeController);

        titleValidator = new BasicTextValidator((TextView) editView.getTitleView());
        ((TextView) editView.getTitleView()).addTextChangedListener(titleValidator);
    }

    public boolean validate() {
        if (!titleValidator.isValid()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onTimeRangeSet(Time startTime, Time endTime, float val, float maxVal) {
        CircleSelector timeRangeView = (CircleSelector) editView.getTimeRange();
        timeRangeView.showValue(val, maxVal, false);

        mQuota.setStartTime(startTime);
        mQuota.setEndTime(endTime);
    }

    @Override
    public void onColorSet(String name, int color) {
        mQuota.setColor(color);
    }

    @Override
    public void onRecurrenceSet() {

    }
}
