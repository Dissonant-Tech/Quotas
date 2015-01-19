package com.dissonant.quotas.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dissonant.quotas.R;
import com.dissonant.quotas.model.RecurrenceModel;
import com.dissonant.quotas.ui.adapters.SwapSpinnerAdapter;


public class RecurrencePickerFragment extends DialogFragment
    implements OnItemSelectedListener, CompoundButton.OnCheckedChangeListener,
               View.OnClickListener {

    private static final String TAG = "RecurrencePicker";

    public interface RecurrencePickerListener {
        void onRecurrenceSet();
    }

    private View mView;
    private Context context;
    private RecurrencePickerListener listener;
    private LayoutInflater mInflater;

    private EditText mInterval;
    private TextView mIntervalPre;
    private TextView mIntervalPost;
    private int mIntervalResId = -1;

    private RecurrenceModel mModel;
    private LinearLayout mFreqView;
    private Spinner mFreqSpinner;

    private Spinner mEndSpinner;
    private SwapSpinnerAdapter mSwapSpinnerAdapter;
    private TextView mEndDateTextView;
    private EditText mEndCount;
    private TextView mPostEndCount;
    private boolean mHidePostEndCount;

    private Resources mResources;

    private LinearLayout mWeekGroup;
    private LinearLayout mWeekGroup2;

    // Mon = 0
    private ToggleButton[] mWeekByDayButtons = new ToggleButton[7];
    /** A double array of Strings to hold the 7x5 list of possible strings of the form:
     *  "on every [Nth] [DAY_OF_WEEK]", e.g. "on every second Monday",
     *  where [Nth] can be [first, second, third, fourth, last] */
    private String[][] mMonthRepeatByDayOfWeekStrs;

    private LinearLayout mMonthGroup;
    private RadioGroup mMonthRepeatByRadioGroup;
    private RadioButton mRepeatMonthlyByNthDayOfWeek;
    private RadioButton mRepeatMonthlyByNthDayOfMonth;
    private String mMonthRepeatByDayOfWeekStr;

    private Button mDone;
    private Time mTime = new Time();

    private int[] mRecurrenceViewIds;

    public RecurrencePickerFragment(Context context, RecurrencePickerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    static public boolean isSupportedMonthlyByNthDayOfWeek(int num) {
        // We only support monthlyByNthDayOfWeek when it is greater then 0 but less then 5.
        // Or if -1 when it is the last monthly day of the week.
        return (num > 0 && num <= RecurrenceModel.FIFTH_WEEK_IN_A_MONTH)
            || num == RecurrenceModel.LAST_NTH_DAY_OF_WEEK;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mInflater = getActivity().getLayoutInflater();
        mView = mInflater.inflate(R.layout.dialog_recurrencepicker, null);
        mResources = getResources();
        mModel = new RecurrenceModel();

        setupView();

        builder.setView(mView);
        return builder.create();
    }

    private void setupView() {
        // Setup Recurrence Spinner
        mFreqSpinner = (Spinner) mView.findViewById(R.id.recurrence_spinner);
        mFreqSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurrence_options, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mFreqSpinner.setAdapter(adapter);
        
        mRecurrenceViewIds = new int[]{ R.id.recurrence_daily, R.id.recurrence_weekly,
            R.id.recurrence_monthly, R.id.recurrence_yearly };

        // Setup dialog body
        mFreqView = (LinearLayout) mView.findViewById(R.id.recurrence_body);
        View recurrenceView = mInflater.inflate(R.layout.view_recurrence, null);
        mFreqView.addView(recurrenceView);
        setFreqView(R.id.recurrence_weekly);

        // Setup WeekDay toggles
        mWeekGroup = (LinearLayout) mView.findViewById(R.id.week_group);
        mWeekGroup2 = (LinearLayout) mView.findViewById(R.id.week_group2);

        int daysInGroup1 = mWeekGroup.getChildCount();

        for (int i = 0; i < mWeekGroup.getChildCount(); i++) {
            mWeekByDayButtons[i] = (ToggleButton) mWeekGroup.getChildAt(i);
        }
        for (int i = 0; i < mWeekGroup2.getChildCount(); i++) {
            mWeekByDayButtons[i + daysInGroup1] = (ToggleButton) mWeekGroup2.getChildAt(i);
        }

        for (ToggleButton toggleBtn : mWeekByDayButtons) {
            toggleBtn.setOnCheckedChangeListener( new ToggleButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton btn, boolean checked) {
                    LinearLayout parent = (LinearLayout) btn.getParent();
                    int index = parent.indexOfChild(btn);
                    if (checked) {
                        mModel.weeklyByDayOfWeek[index] = true;
                    } else {
                        mModel.weeklyByDayOfWeek[index] = false;
                    }
                }
            });
        }

        // Setup dialog switch
        Switch mRecurrenceSwitch = (Switch) mView.findViewById(R.id.recurrence_toggle);
        mRecurrenceSwitch.setOnCheckedChangeListener( new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
                mModel.recurrenceState = isChecked ? RecurrenceModel.STATE_RECURRENCE
                    : RecurrenceModel.STATE_NO_RECURRENCE;
                toggleViewOptions();
            }
        });


        // Setup Interval EditText
        mInterval = (EditText) mView.findViewById(R.id.interval_count);

        mInterval.addTextChangedListener(new MinMaxTextWatcher(1,
                    RecurrenceModel.INTERVAL_DEFAULT, RecurrenceModel.INTERVAL_MAX) {
            @Override
            void onChange(int v) {
                if (mIntervalResId != -1 && mInterval.getText().toString().length() > 0) {
                    mModel.interval = v;
                    updateIntervalText();
                    mInterval.requestLayout();
                }
            }
        });
        mIntervalPre = (TextView) mView.findViewById(R.id.interval_pre);
        mIntervalPost = (TextView) mView.findViewById(R.id.interval_post);

        // Setup Recurrence End Spinner
        mEndSpinner = (Spinner) mView.findViewById(R.id.endSpinner);
        mEndSpinner.setOnItemSelectedListener(this);

        ArrayList<CharSequence> endLabelArray =
            getCharSequenceArray(R.array.recurrence_end_label_array);
        ArrayList<CharSequence> endArray =
            getCharSequenceArray(R.array.recurrence_end_array);

        mSwapSpinnerAdapter = new SwapSpinnerAdapter(getActivity(),
                endLabelArray, endArray, R.layout.recurrencepicker_freq_item,
                R.layout.recurrencepicker_end_text);
        mSwapSpinnerAdapter.setDropDownViewResource(R.layout.recurrencepicker_freq_item);
        mEndSpinner.setAdapter(mSwapSpinnerAdapter);

        mEndCount = (EditText) mView.findViewById(R.id.end_count);
        mEndCount.addTextChangedListener(new MinMaxTextWatcher(1,
                    RecurrenceModel.COUNT_DEFAULT, RecurrenceModel.COUNT_MAX) {
            @Override
            void onChange(int v) {
                if (mModel.endCount != v) {
                    mModel.endCount = v;
                    updateEndCountText();
                    mEndCount.requestLayout();
                }
            }
        });
        mPostEndCount = (TextView) mView.findViewById(R.id.end_post);

        mEndDateTextView = (TextView) mView.findViewById(R.id.end_date);
        mEndDateTextView.setOnClickListener(this);
        if (mModel.endDate == null) {
            mModel.endDate = new Time(mTime);
            switch (mModel.freq) {
                case RecurrenceModel.FREQ_DAILY:
                case RecurrenceModel.FREQ_WEEKLY:
                    mModel.endDate.month += 1;
                    break;
                case RecurrenceModel.FREQ_MONTHLY:
                    mModel.endDate.month += 3;
                    break;
                case RecurrenceModel.FREQ_YEARLY:
                    mModel.endDate.year += 3;
                    break;
            }
            mModel.endDate.normalize(false);
        }

        mDone = (Button) mView.findViewById(R.id.done);
        mDone.setOnClickListener(this);
    }

    private void updateIntervalText() {
        if (mIntervalResId == -1) {
            return;
        }

        final String INTERVAL_COUNT_MARKER = "%d";
        String intervalString = mResources.getQuantityString(mIntervalResId,
                mModel.interval);
        int markerStart = intervalString.indexOf(INTERVAL_COUNT_MARKER);

        if (markerStart != -1) {
            int postTextStart = markerStart + INTERVAL_COUNT_MARKER.length();
            mIntervalPost.setText(intervalString.substring(postTextStart,
                        intervalString.length()).trim());
            mIntervalPre.setText(intervalString.substring(0, markerStart).trim());
        }
    }

    /**
     * Update the "Repeat for N events" end option with the proper string values
     * based on the value that has been entered for N.
     */
    private void updateEndCountText() {
        final String END_COUNT_MARKER = "%d";
        String endString = mResources.getQuantityString(R.plurals.recurrence_end_count,
                mModel.endCount);
        int markerStart = endString.indexOf(END_COUNT_MARKER);

        if (markerStart != -1) {
            if (markerStart == 0) {
                Log.e(TAG, "No text to put in to recurrence's end spinner.");
            } else {
                int postTextStart = markerStart + END_COUNT_MARKER.length();
                mPostEndCount.setText(endString.substring(postTextStart,
                            endString.length()).trim());
            }
        }
    }

    private ArrayList<CharSequence> getCharSequenceArray(int resourceId) {
        ArrayList<CharSequence> result;
        result = new ArrayList<CharSequence>
            (Arrays.asList(mResources.getStringArray(resourceId)));

        return result;
    }

    private void setFreqView(String selectedView) {
        if (selectedView == getString(R.string.recurrence_daily)) {
            setFreqView(R.id.recurrence_daily);
        } else if (selectedView == getString(R.string.recurrence_weekly)) {
            setFreqView(R.id.recurrence_weekly);
        } else if (selectedView == getString(R.string.recurrence_monthly)) {
            setFreqView(R.id.recurrence_monthly);
        } else if (selectedView == getString(R.string.recurrence_yearly)) {
            setFreqView(R.id.recurrence_yearly);
        }
    }

    public void setFreqView(int viewId) {
        for (int id : mRecurrenceViewIds) {
            mFreqView.findViewById(id).setVisibility(View.GONE);
        }
        mFreqView.findViewById(viewId).setVisibility(View.VISIBLE);
    }

    private void toggleViewOptions() {
        if (mModel.recurrenceState == RecurrenceModel.STATE_NO_RECURRENCE) {
            mFreqView.setEnabled(false);
            mFreqSpinner.setEnabled(false);
            mEndSpinner.setEnabled(false);
            mEndCount.setEnabled(false);
            mPostEndCount.setEnabled(false);
            mEndDateTextView.setEnabled(false);
            mInterval.setEnabled(false);
            mIntervalPre.setEnabled(false);
            mIntervalPost.setEnabled(false);
            for (ToggleButton btn : mWeekByDayButtons) {
                btn.setEnabled(false);
            }
        } else {
            mFreqView.setEnabled(true);
            mFreqSpinner.setEnabled(true);
            mEndSpinner.setEnabled(true);
            mEndCount.setEnabled(true);
            mPostEndCount.setEnabled(true);
            mEndDateTextView.setEnabled(true);
            mInterval.setEnabled(true);
            mIntervalPre.setEnabled(true);
            mIntervalPost.setEnabled(true);
            for (ToggleButton btn : mWeekByDayButtons) {
                btn.setEnabled(true);
            }
        }
        updateDoneButtonState();
    }

    private void updateDoneButtonState() {
        if (mModel.recurrenceState == RecurrenceModel.STATE_NO_RECURRENCE) {
            mDone.setEnabled(true);
            return;
        }

        if (mInterval.getText().toString().length() == 0) {
            mDone.setEnabled(false);
            return;
        }

        if (mEndCount.getVisibility() == View.VISIBLE &&
                mEndCount.getText().toString().length() == 0) {
            mDone.setEnabled(false);
            return;
                }

        if (mModel.freq == RecurrenceModel.FREQ_WEEKLY) {
            for (CompoundButton b : mWeekByDayButtons) {
                if (b.isChecked()) {
                    mDone.setEnabled(true);
                    return;
                }
            }
            mDone.setEnabled(false);
            return;
        }

        mDone.setEnabled(true);
    }

    private void setRecurrenceFreq(int freq) {
        switch (freq) {
            case RecurrenceModel.FREQ_DAILY:
                mModel.freq = RecurrenceModel.FREQ_DAILY;
                break;
            case RecurrenceModel.FREQ_WEEKLY:
                mModel.freq = RecurrenceModel.FREQ_WEEKLY;
                break;
            case RecurrenceModel.FREQ_MONTHLY:
                mModel.freq = RecurrenceModel.FREQ_MONTHLY;
                break;
            case RecurrenceModel.FREQ_YEARLY:
                mModel.freq = RecurrenceModel.FREQ_YEARLY;
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selection = parent.getItemAtPosition(pos).toString();

        if (parent == mFreqSpinner) {
            setFreqView(selection);
            setRecurrenceFreq(pos);
        } else if (parent == mEndSpinner) {
            switch (pos) {
                case RecurrenceModel.END_NEVER:
                    mModel.end = RecurrenceModel.END_NEVER;
                    break;
                case RecurrenceModel.END_BY_DATE:
                    mModel.end = RecurrenceModel.END_BY_DATE;
                    break;
                case RecurrenceModel.END_BY_COUNT:
                    mModel.end = RecurrenceModel.END_BY_COUNT;

                    if (mModel.endCount <= 1) {
                        mModel.endCount = 1;
                    } else if (mModel.endCount > RecurrenceModel.COUNT_MAX) {
                        mModel.endCount = RecurrenceModel.COUNT_MAX;
                    }
                    updateEndCountText();
                    break;
            }
            mEndCount.setVisibility(mModel.end == RecurrenceModel.END_BY_COUNT ? View.VISIBLE
                    : View.GONE);
            mEndDateTextView.setVisibility(mModel.end == RecurrenceModel.END_BY_DATE ? View.VISIBLE
                    : View.GONE);
            mPostEndCount.setVisibility(
                    mModel.end == RecurrenceModel.END_BY_COUNT  && !mHidePostEndCount?
                    View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    @Override
    public void onClick(View v) {

    }

    public class MinMaxTextWatcher implements TextWatcher {
        private int mMin;
        private int mMax;
        private int mDefault;

        public MinMaxTextWatcher(int min, int defaultInt, int max) {
            mMin = min;
            mMax = max;
            mDefault = defaultInt;
        }

        @Override
        public void afterTextChanged(Editable s) {

            boolean updated = false;
            int value;
            try {
                value = Integer.parseInt(s.toString());
            } catch (NumberFormatException e) {
                value = mDefault;
            }

            if (value < mMin) {
                value = mMin;
                updated = true;
            } else if (value > mMax) {
                updated = true;
                value = mMax;
            }

            // Update UI
            if (updated) {
                s.clear();
                s.append(Integer.toString(value));
            }

            updateDoneButtonState();
            onChange(value);
        }

        /** Override to be called after each key stroke */
        void onChange(int value) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
