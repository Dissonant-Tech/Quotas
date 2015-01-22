package com.dissonant.quotas;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dissonant.quotas.controllers.ColorPickerController;
import com.dissonant.quotas.controllers.RecurrencePickerController;
import com.dissonant.quotas.controllers.TimeRangeController;
import com.dissonant.quotas.controllers.TitleController;
import com.dissonant.quotas.model.QuotaModel;
import com.dissonant.quotas.ui.views.EditView;


public class EditActivity extends Activity {

    private static final String TAG = "EditActivity";

    private EditView mView;
    private QuotaModel mQuota;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (EditView) View.inflate(this, R.layout.activity_edit, null);
        setContentView(mView);

        mQuota = new QuotaModel();
        attachListeners();

        // Load default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }
    public void attachListeners() {
        mView.getColorPicker().setOnClickListener(
                new ColorPickerController(this, mView, mQuota));

        mView.getRepeat().setOnClickListener(
                new RecurrencePickerController(this, mQuota, mView));

        mView.getTimeRange().setOnClickListener(
                new TimeRangeController(this, mView, mQuota));

        TitleController titleController = new TitleController(this, mView, mQuota);
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
            Log.i(TAG, mQuota.toString());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finishAfterTransition();
    }
}
