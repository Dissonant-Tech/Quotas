package com.dissonant.quotas;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dissonant.quotas.controllers.EditController;
import com.dissonant.quotas.controllers.EditController.EditListener;
import com.dissonant.quotas.ui.views.EditView;


public class EditActivity extends Activity implements EditListener {

    EditView mView;
    EditController mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (EditView) View.inflate(this, R.layout.activity_edit, null);
        setContentView(mView);

        mController = new EditController(this, this, mView);

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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finishAfterTransition();
    }

    @Override
    public void onSaved() {
        
    }

}
