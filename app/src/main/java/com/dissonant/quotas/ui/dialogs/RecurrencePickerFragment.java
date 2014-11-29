package com.dissonant.quotas.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.widgets.EndSpinner;


public class RecurrencePickerFragment extends DialogFragment
    implements OnItemSelectedListener, CompoundButton.OnCheckedChangeListener,
               View.OnClickListener {

    private static final String TAG = "RecurrencePicker";

    public interface RecurrencePickerListener {
        void onRecurrenceSet();
    }

    private View mView;

    private Context mContext;
    private RecurrencePickerListener listener;

    private LayoutInflater mInflater;
    private LinearLayout mBodyView;
    private Spinner mFreqSpinner;

    private EndSpinner mEndSpinner;
    private ViewGroup mEndSpinnerView;

    private Resources mResources;

    public RecurrencePickerFragment(Context context, RecurrencePickerListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mInflater = getActivity().getLayoutInflater();
        mView = mInflater.inflate(R.layout.dialog_recurrencepicker, null);
        mResources = getResources();

        setupView();

        builder.setView(mView);
        return builder.create();
    }

    public void setupView() {
        // Setup Recurrence Spinner
        mFreqSpinner = (Spinner) mView.findViewById(R.id.recurrence_spinner);
        mFreqSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurrence_options, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mFreqSpinner.setAdapter(adapter);

        // Setup dialog switch
        Switch dialogToggle = (Switch) mView.findViewById(R.id.recurrence_toggle);
        dialogToggle.setOnCheckedChangeListener(this);

        // Setup dialog body
        mBodyView = (LinearLayout) mView.findViewById(R.id.recurrence_body);
        setBodyView(R.layout.view_recurrence_daily);

        ArrayList<CharSequence> listedItems = getCharSequenceArray(R.array.recurrence_end_label_array);
        ArrayList<CharSequence> selectedItems = getCharSequenceArray(R.array.recurrence_end_array);
        mEndSpinnerView = (ViewGroup) mView.findViewById(R.id.endSpinner);
        mEndSpinner = new EndSpinner(mContext, mEndSpinnerView, listedItems, selectedItems);
    }

    public String genRecurranceString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public ArrayList<CharSequence> getCharSequenceArray(int resourceId) {
        ArrayList<CharSequence> result;
        result = new ArrayList<CharSequence>(Arrays.asList(mResources.getStringArray(resourceId)));
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selection = parent.getItemAtPosition(pos).toString();

        if (parent.getId() == R.id.recurrence_spinner) {
            setBodyView(selection);
        }
    }

    public void setBodyView(String selectedView) {
        if (selectedView == getString(R.string.recurrence_daily)) {
            setBodyView(R.layout.view_recurrence_daily);
        } else if (selectedView == getString(R.string.recurrence_weekly)) {
            setBodyView(R.layout.view_recurrence_weekly);
        } else if (selectedView == getString(R.string.recurrence_monthly)) {
            setBodyView(R.layout.view_recurrence_monthly);
        } else if (selectedView == getString(R.string.recurrence_yearly)) {
            setBodyView(R.layout.view_recurrence_yearly);
        }
    }

    public void setBodyView(int viewId) {
        View newView = mInflater.inflate(viewId, null);
        mBodyView.removeAllViews();
        mBodyView.addView(newView);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LinearLayout layout = (LinearLayout) mBodyView;

        if (isChecked) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setEnabled(true);
            }
        } else {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
    }
}
