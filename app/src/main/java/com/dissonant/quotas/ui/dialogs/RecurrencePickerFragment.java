package com.dissonant.quotas.ui.dialogs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dissonant.quotas.R;


public class RecurrencePickerFragment extends DialogFragment
    implements OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    public interface RecurrencePickerListener {
        void onRecurrenceSet();
    }

    private View mView;

    private Context context;
    private RecurrencePickerListener listener;

    private LayoutInflater mInflater;
    private LinearLayout mBodyView;

    private Spinner mFreqSpinner;

    private String mEndNeverStr;
    private String mEndDateLabel;
    private String mEndCountLabel;
    private Spinner mEndSpinner;
    private ArrayList<CharSequence> mEndSpinnerArray = new ArrayList<CharSequence>(3);
    private EndSpinnerAdapter mEndSpinnerAdapter;

    public RecurrencePickerFragment(Context context, RecurrencePickerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mInflater = getActivity().getLayoutInflater();
        mView = mInflater.inflate(R.layout.dialog_recurrancepicker, null);

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

    }

    public void setupEndSpinner() {
        // Setup end spinner
        mEndNeverStr = getString(R.string.recurrence_end_continously);
        mEndDateLabel = getString(R.string.recurrence_end_date_label);
        mEndCountLabel = getString(R.string.recurrence_end_count_label);

        mEndSpinnerArray.add(mEndNeverStr);
        mEndSpinnerArray.add(mEndDateLabel);
        mEndSpinnerArray.add(mEndCountLabel);
        mEndSpinner = (Spinner) mView.findViewById(R.id.endSpinner);
        mEndSpinner.setOnItemSelectedListener(this);

        mEndSpinnerAdapter = new EndSpinnerAdapter(getActivity(), mEndSpinnerArray,
                R.layout.recurrencepicker_freq_item, R.layout.recurrencepicker_end_text);
        mEndSpinnerAdapter.setDropDownViewResource(R.layout.recurrencepicker_freq_item);
        mEndSpinner.setAdapter(mEndSpinnerAdapter);
    }

    public String genRecurranceString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selection = parent.getItemAtPosition(pos).toString();

        if (parent.getId() == R.id.recurrence_spinner) {
            setBodyView(selection);
        } else if (parent.getId() == R.id.endSpinner) {
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
        setupBodyView(viewId);
    }

    public void setupBodyView(int viewId) {
        if (viewId == R.layout.view_recurrence_daily) {
            setupEndSpinner();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    private class EndSpinnerAdapter extends ArrayAdapter<CharSequence> {

        private String END_DATE_MARKER = "%s";
        private String END_COUNT_MARKER = "%d";

        private LayoutInflater mInflater;
        private int mItemResourceId;
        private int mTextResourceId;
        private ArrayList<CharSequence> mStrings;
        private String mEndDateString;
        private boolean mUseFormStrings;

        public EndSpinnerAdapter(Context context, ArrayList<CharSequence> strings,
                int itemResourceId, int textResourceId) {
            super(context, itemResourceId, strings);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mItemResourceId = itemResourceId;
            mTextResourceId = textResourceId;
            mStrings = strings;
            mEndDateString = getResources().getString(R.string.recurrence_end_date);

            // If either date or count strings don't translate well, such that we aren't assured
            // to have some text available to be placed in the spinner, then we'll have to use
            // the more form-like versions of both strings instead.
            int markerStart = mEndDateString.indexOf(END_DATE_MARKER);
            if (markerStart <= 0) {
                // The date string does not have any text before the "%s" so we'll have to use the
                // more form-like strings instead.
                mUseFormStrings = true;
            } else {
                String countEndStr = getResources().getQuantityString(
                        R.plurals.recurrence_end_count, 1);
                markerStart = countEndStr.indexOf(END_COUNT_MARKER);
                if (markerStart <= 0) {
                    // The count string does not have any text before the "%d" so we'll have to use
                    // the more form-like strings instead.
                    mUseFormStrings = true;
                }
            }

            if (mUseFormStrings) {
                // We'll have to set the layout for the spinner to be weight=0 so it doesn't
                // take up too much space.
                mEndSpinner.setLayoutParams(
                        new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            // Check if we can recycle the view
            if (convertView == null) {
                v = mInflater.inflate(mTextResourceId, parent, false);
            } else {
                v = convertView;
            }

            return v;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v;
            // Check if we can recycle the view
            if (convertView == null) {
                v = mInflater.inflate(mItemResourceId, parent, false);
            } else {
                v = convertView;
            }

            TextView item = (TextView) v.findViewById(R.id.spinner_item);
            item.setText(mStrings.get(position));

            return v;
        }
    }
}
