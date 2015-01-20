package com.dissonant.quotas.ui.dialogs;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
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
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dissonant.quotas.R;
import com.dissonant.quotas.model.RecurrenceModel;
import com.dissonant.quotas.utils.Utils;


public class RecurrencePickerDialog extends DialogFragment implements OnItemSelectedListener,
        OnCheckedChangeListener, OnClickListener,
        android.widget.RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "RecurrencePickerDialog";

    // in dp's
    private static final int MIN_SCREEN_WIDTH_FOR_SINGLE_ROW_WEEK = 450;

    // Update android:maxLength in EditText as needed
    private static final int INTERVAL_MAX = 99;
    private static final int INTERVAL_DEFAULT = 1;
    // Update android:maxLength in EditText as needed
    private static final int COUNT_MAX = 730;
    private static final int COUNT_DEFAULT = 5;

    // Special cases in monthlyByNthDayOfWeek
    private static final int FIFTH_WEEK_IN_A_MONTH = 5;
    private static final int LAST_NTH_DAY_OF_WEEK = -1;

    private DatePickerDialog mDatePickerDialog;

    public interface RecurrencePickerListener {
        void onRecurrenceSet();
    }

    class minMaxTextWatcher implements TextWatcher {
        private int mMin;
        private int mMax;
        private int mDefault;

        public minMaxTextWatcher(int min, int defaultInt, int max) {
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

    private Resources mResources;
    private Time mTime = new Time(); // TODO timezone?
    private RecurrenceModel mModel = new RecurrenceModel();
    private Toast mToast;

    private final int[] TIME_DAY_TO_CALENDAR_DAY = new int[] {
            Calendar.SUNDAY,
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY,
            Calendar.SATURDAY,
    };

    // Call mStringBuilder.setLength(0) before formatting any string or else the
    // formatted text will accumulate.
    // private final StringBuilder mStringBuilder = new StringBuilder();
    // private Formatter mFormatter = new Formatter(mStringBuilder);

    private Context mContext;
    private View mView;

    private Spinner mFreqSpinner;

    public static final String BUNDLE_START_TIME_MILLIS = "bundle_event_start_time";
    public static final String BUNDLE_TIME_ZONE = "bundle_event_time_zone";
    public static final String BUNDLE_RRULE = "bundle_event_rrule";

    private static final String BUNDLE_MODEL = "bundle_model";
    private static final String BUNDLE_END_COUNT_HAS_FOCUS = "bundle_end_count_has_focus";

    private static final String FRAG_TAG_DATE_PICKER = "tag_date_picker_frag";

    private Switch mRepeatSwitch;

    private EditText mInterval;
    private TextView mIntervalPreText;
    private TextView mIntervalPostText;

    private int mIntervalResId = -1;

    private Spinner mEndSpinner;
    private TextView mEndDateTextView;
    private EditText mEndCount;
    private TextView mPostEndCount;
    private boolean mHidePostEndCount;

    private ArrayList<CharSequence> mEndSpinnerArray = new ArrayList<CharSequence>(3);
    private EndSpinnerAdapter mEndSpinnerAdapter;
    private String mEndNeverStr;
    private String mEndDateLabel;
    private String mEndCountLabel;

    /** Hold toggle buttons in the order per user's first day of week preference */
    private LinearLayout mWeekGroup;
    private LinearLayout mWeekGroup2;
    // Sun = 0
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

    private RecurrencePickerListener mListener;

    public RecurrencePickerDialog(Context context, RecurrencePickerListener listener) {
        mContext = context;
        mListener = listener;
    }

    static public boolean isSupportedMonthlyByNthDayOfWeek(int num) {
        // We only support monthlyByNthDayOfWeek when it is greater then 0 but less then 5.
        // Or if -1 when it is the last monthly day of the week.
        return (num > 0 && num <= FIFTH_WEEK_IN_A_MONTH) || num == LAST_NTH_DAY_OF_WEEK;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        boolean endCountHasFocus = false;
        if (savedInstanceState != null) {
            RecurrenceModel m = (RecurrenceModel) savedInstanceState.get(BUNDLE_MODEL);
            if (m != null) {
                mModel = m;
            }
            endCountHasFocus = savedInstanceState.getBoolean(BUNDLE_END_COUNT_HAS_FOCUS);
        } else {
            Bundle b = getArguments();
            if (b != null) {
                mTime.set(b.getLong(BUNDLE_START_TIME_MILLIS));

                String tz = b.getString(BUNDLE_TIME_ZONE);
                if (!TextUtils.isEmpty(tz)) {
                    mTime.timezone = tz;
                }
                mTime.normalize(false);

                // Time days of week: Sun=0, Mon=1, etc
                mModel.weeklyByDayOfWeek[mTime.weekDay] = true;
                String rrule = b.getString(BUNDLE_RRULE);
                if (!TextUtils.isEmpty(rrule)) {
                    mModel.recurrenceState = RecurrenceModel.STATE_RECURRENCE;
                    // Leave today's day of week as checked by default in weekly view.
                }

            } else {
                mTime.setToNow();
            }
        }
        
        mResources = getResources();
        mView = inflater.inflate(R.layout.dialog_recurrencepicker, container, true);

        final Activity activity = getActivity();
        final Configuration config = activity.getResources().getConfiguration();

        mRepeatSwitch = (Switch) mView.findViewById(R.id.recurrence_switch);
        mRepeatSwitch.setChecked(mModel.recurrenceState == RecurrenceModel.STATE_RECURRENCE);
        mRepeatSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mModel.recurrenceState = isChecked ? RecurrenceModel.STATE_RECURRENCE
                        : RecurrenceModel.STATE_NO_RECURRENCE;
                togglePickerOptions();
            }
        });

        mFreqSpinner = (Spinner) mView.findViewById(R.id.freq_spinner);
        mFreqSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> freqAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurrence_freq, R.layout.recurrencepicker_freq_item);
        freqAdapter.setDropDownViewResource(R.layout.recurrencepicker_freq_item);
        mFreqSpinner.setAdapter(freqAdapter);

        mInterval = (EditText) mView.findViewById(R.id.interval);
        mInterval.addTextChangedListener(new minMaxTextWatcher(1, INTERVAL_DEFAULT, INTERVAL_MAX) {
            @Override
            void onChange(int v) {
                if (mIntervalResId != -1 && mInterval.getText().toString().length() > 0) {
                    mModel.interval = v;
                    updateIntervalText();
                    mInterval.requestLayout();
                }
            }
        });
        mIntervalPreText = (TextView) mView.findViewById(R.id.interval_pre);
        mIntervalPostText = (TextView) mView.findViewById(R.id.interval_post);
        
        mEndNeverStr = mResources.getString(R.string.recurrence_end_continously);
        mEndDateLabel = mResources.getString(R.string.recurrence_end_date_label);
        mEndCountLabel = mResources.getString(R.string.recurrence_end_count_label);

        mEndSpinnerArray.add(mEndNeverStr);
        mEndSpinnerArray.add(mEndDateLabel);
        mEndSpinnerArray.add(mEndCountLabel);
        mEndSpinner = (Spinner) mView.findViewById(R.id.end_spinner);
        mEndSpinner.setOnItemSelectedListener(this);
        mEndSpinnerAdapter = new EndSpinnerAdapter(getActivity(), mEndSpinnerArray,
                R.layout.recurrencepicker_freq_item, R.layout.recurrencepicker_end_text);
        mEndSpinnerAdapter.setDropDownViewResource(R.layout.recurrencepicker_freq_item);
        mEndSpinner.setAdapter(mEndSpinnerAdapter);

        mEndCount = (EditText) mView.findViewById(R.id.end_count);
        mEndCount.addTextChangedListener(new minMaxTextWatcher(1, COUNT_DEFAULT, COUNT_MAX) {
            @Override
            void onChange(int v) {
                if (mModel.endCount != v) {
                    mModel.endCount = v;
                    updateEndCountText();
                    mEndCount.requestLayout();
                }
            }
        });
        mPostEndCount = (TextView) mView.findViewById(R.id.end_count_post);

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

        mWeekGroup = (LinearLayout) mView.findViewById(R.id.week_group);
        mWeekGroup2 = (LinearLayout) mView.findViewById(R.id.week_group2);

        // In Calendar.java day of week order e.g Sun = 1 ... Sat = 7
        String[] dayOfWeekString = new DateFormatSymbols().getWeekdays();

        mMonthRepeatByDayOfWeekStrs = new String[7][];
        // from Time.SUNDAY as 0 through Time.SATURDAY as 6
        mMonthRepeatByDayOfWeekStrs[0] = mResources.getStringArray(R.array.repeat_by_nth_sun);
        mMonthRepeatByDayOfWeekStrs[1] = mResources.getStringArray(R.array.repeat_by_nth_mon);
        mMonthRepeatByDayOfWeekStrs[2] = mResources.getStringArray(R.array.repeat_by_nth_tues);
        mMonthRepeatByDayOfWeekStrs[3] = mResources.getStringArray(R.array.repeat_by_nth_wed);
        mMonthRepeatByDayOfWeekStrs[4] = mResources.getStringArray(R.array.repeat_by_nth_thurs);
        mMonthRepeatByDayOfWeekStrs[5] = mResources.getStringArray(R.array.repeat_by_nth_fri);
        mMonthRepeatByDayOfWeekStrs[6] = mResources.getStringArray(R.array.repeat_by_nth_sat);

        // Setup WeekDay toggles
        // In Time.java day of week order e.g. Sun = 0
        int idx = Utils.getFirstDayOfWeek(getActivity());

        // In Calendar.java day of week order e.g Sun = 1 ... Sat = 7
        dayOfWeekString = new DateFormatSymbols().getShortWeekdays();

        int numOfButtonsInRow1;
        int numOfButtonsInRow2;

        if (mResources.getConfiguration().screenWidthDp > MIN_SCREEN_WIDTH_FOR_SINGLE_ROW_WEEK) {
            numOfButtonsInRow1 = 7;
            numOfButtonsInRow2 = 0;
            mWeekGroup2.setVisibility(View.GONE);
            mWeekGroup2.getChildAt(3).setVisibility(View.GONE);
        } else {
            numOfButtonsInRow1 = 4;
            numOfButtonsInRow2 = 3;

            mWeekGroup2.setVisibility(View.VISIBLE);
            // Set rightmost button on the second row invisible so it takes up
            // space and everything centers properly
            mWeekGroup2.getChildAt(3).setVisibility(View.INVISIBLE);
        }

        /* First row */
        for (int i = 0; i < 7; i++) {
            if (i >= numOfButtonsInRow1) {
                mWeekGroup.getChildAt(i).setVisibility(View.GONE);
                continue;
            }

            mWeekByDayButtons[idx] = (ToggleButton) mWeekGroup.getChildAt(i);
            mWeekByDayButtons[idx].setTextOff(dayOfWeekString[TIME_DAY_TO_CALENDAR_DAY[idx]]);
            mWeekByDayButtons[idx].setTextOn(dayOfWeekString[TIME_DAY_TO_CALENDAR_DAY[idx]]);
            mWeekByDayButtons[idx].setOnCheckedChangeListener(this);

            if (++idx >= 7) {
                idx = 0;
            }
        }

        /* 2nd Row */
        for (int i = 0; i < 3; i++) {
            if (i >= numOfButtonsInRow2) {
                mWeekGroup2.getChildAt(i).setVisibility(View.GONE);
                continue;
            }
            mWeekByDayButtons[idx] = (ToggleButton) mWeekGroup2.getChildAt(i);
            mWeekByDayButtons[idx].setTextOff(dayOfWeekString[TIME_DAY_TO_CALENDAR_DAY[idx]]);
            mWeekByDayButtons[idx].setTextOn(dayOfWeekString[TIME_DAY_TO_CALENDAR_DAY[idx]]);
            mWeekByDayButtons[idx].setOnCheckedChangeListener(this);

            if (++idx >= 7) {
                idx = 0;
            }
        }

        mMonthGroup = (LinearLayout) mView.findViewById(R.id.monthGroup);
        mMonthRepeatByRadioGroup = (RadioGroup) mView.findViewById(R.id.monthGroup);
        mMonthRepeatByRadioGroup.setOnCheckedChangeListener(this);
        mRepeatMonthlyByNthDayOfWeek = (RadioButton) mView
                .findViewById(R.id.repeatMonthlyByNthDayOfTheWeek);
        mRepeatMonthlyByNthDayOfMonth = (RadioButton) mView
                .findViewById(R.id.repeatMonthlyByNthDayOfMonth);

        mDone = (Button) mView.findViewById(R.id.done);
        mDone.setOnClickListener(this);

        togglePickerOptions();
        updateDialog();
        if (endCountHasFocus) {
            mEndCount.requestFocus();
        }
        return mView;
    }

    private void togglePickerOptions() {
        if (mModel.recurrenceState == RecurrenceModel.STATE_NO_RECURRENCE) {
            mFreqSpinner.setEnabled(false);
            mEndSpinner.setEnabled(false);
            mIntervalPreText.setEnabled(false);
            mInterval.setEnabled(false);
            mIntervalPostText.setEnabled(false);
            mMonthRepeatByRadioGroup.setEnabled(false);
            mEndCount.setEnabled(false);
            mPostEndCount.setEnabled(false);
            mEndDateTextView.setEnabled(false);
            mRepeatMonthlyByNthDayOfWeek.setEnabled(false);
            mRepeatMonthlyByNthDayOfMonth.setEnabled(false);
            for (Button button : mWeekByDayButtons) {
                button.setEnabled(false);
            }
        } else {
            mView.findViewById(R.id.interval_layout).setEnabled(true);
            mFreqSpinner.setEnabled(true);
            mEndSpinner.setEnabled(true);
            mIntervalPreText.setEnabled(true);
            mInterval.setEnabled(true);
            mIntervalPostText.setEnabled(true);
            mMonthRepeatByRadioGroup.setEnabled(true);
            mEndCount.setEnabled(true);
            mPostEndCount.setEnabled(true);
            mEndDateTextView.setEnabled(true);
            mRepeatMonthlyByNthDayOfWeek.setEnabled(true);
            mRepeatMonthlyByNthDayOfMonth.setEnabled(true);
            for (Button button : mWeekByDayButtons) {
                button.setEnabled(true);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_MODEL, mModel);
        if (mEndCount.hasFocus()) {
            outState.putBoolean(BUNDLE_END_COUNT_HAS_FOCUS, true);
        }
    }

    public void updateDialog() {
        // Interval
        // Checking before setting because this causes infinite recursion
        // in afterTextWatcher
        final String intervalStr = Integer.toString(mModel.interval);
        if (!intervalStr.equals(mInterval.getText().toString())) {
            mInterval.setText(intervalStr);
        }

        mFreqSpinner.setSelection(mModel.freq);
        mWeekGroup.setVisibility(mModel.freq == RecurrenceModel.FREQ_WEEKLY ? View.VISIBLE : View.GONE);
        mWeekGroup2.setVisibility(mModel.freq == RecurrenceModel.FREQ_WEEKLY ? View.VISIBLE : View.GONE);
        mMonthGroup.setVisibility(mModel.freq == RecurrenceModel.FREQ_MONTHLY ? View.VISIBLE : View.GONE);

        switch (mModel.freq) {
            case RecurrenceModel.FREQ_DAILY:
                mIntervalResId = R.plurals.recurrence_interval_daily;
                break;

            case RecurrenceModel.FREQ_WEEKLY:
                mIntervalResId = R.plurals.recurrence_interval_weekly;
                for (int i = 0; i < 7; i++) {
                    mWeekByDayButtons[i].setChecked(mModel.weeklyByDayOfWeek[i]);
                }
                break;

            case RecurrenceModel.FREQ_MONTHLY:
                mIntervalResId = R.plurals.recurrence_interval_monthly;

                if (mModel.monthlyRepeat == RecurrenceModel.MONTHLY_BY_DATE) {
                    mMonthRepeatByRadioGroup.check(R.id.repeatMonthlyByNthDayOfMonth);
                } else if (mModel.monthlyRepeat == RecurrenceModel.MONTHLY_BY_NTH_DAY_OF_WEEK) {
                    mMonthRepeatByRadioGroup.check(R.id.repeatMonthlyByNthDayOfTheWeek);
                }

                if (mMonthRepeatByDayOfWeekStr == null) {
                    if (mModel.monthlyByNthDayOfWeek == 0) {
                        mModel.monthlyByNthDayOfWeek = (mTime.monthDay + 6) / 7;
                        // Since not all months have 5 weeks, we convert 5th NthDayOfWeek to
                        // -1 for last monthly day of the week
                        if (mModel.monthlyByNthDayOfWeek >= FIFTH_WEEK_IN_A_MONTH) {
                            mModel.monthlyByNthDayOfWeek = LAST_NTH_DAY_OF_WEEK;
                        }
                        mModel.monthlyByDayOfWeek = mTime.weekDay;
                    }

                    String[] monthlyByNthDayOfWeekStrs =
                            mMonthRepeatByDayOfWeekStrs[mModel.monthlyByDayOfWeek];

                    // TODO(psliwowski): Find a better way handle -1 indexes
                    int msgIndex = mModel.monthlyByNthDayOfWeek < 0 ? FIFTH_WEEK_IN_A_MONTH :
                            mModel.monthlyByNthDayOfWeek;
                    mMonthRepeatByDayOfWeekStr =
                            monthlyByNthDayOfWeekStrs[msgIndex - 1];
                    mRepeatMonthlyByNthDayOfWeek.setText(mMonthRepeatByDayOfWeekStr);
                }
                break;

            case RecurrenceModel.FREQ_YEARLY:
                mIntervalResId = R.plurals.recurrence_interval_yearly;
                break;
        }
        updateIntervalText();
        updateDoneButtonState();

        mEndSpinner.setSelection(mModel.end);
        if (mModel.end == RecurrenceModel.END_BY_DATE) {
            final String dateStr = DateUtils.formatDateTime(getActivity(),
                    mModel.endDate.toMillis(false), DateUtils.FORMAT_NUMERIC_DATE);
            mEndDateTextView.setText(dateStr);
        } else {
            if (mModel.end == RecurrenceModel.END_BY_COUNT) {
                // Checking before setting because this causes infinite
                // recursion
                // in afterTextWatcher
                final String countStr = Integer.toString(mModel.endCount);
                if (!countStr.equals(mEndCount.getText().toString())) {
                    mEndCount.setText(countStr);
                }
            }
        }
    }

    /**
     * @param endDateString
     */
    private void setEndSpinnerEndDateStr(final String endDateString) {
        mEndSpinnerArray.set(1, endDateString);
        mEndSpinnerAdapter.notifyDataSetChanged();
    }

    private void doToast() {
        Log.e(TAG, "Model = " + mModel.toString());
        String rrule = "Test Toast";
        if (mModel.recurrenceState == RecurrenceModel.STATE_NO_RECURRENCE) {
            rrule = "Not repeating";
        } else {
        }

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText((Activity) mContext, rrule,
                Toast.LENGTH_LONG);
        mToast.show();
    }

    // TODO Test and update for Right-to-Left
    private void updateIntervalText() {
        if (mIntervalResId == -1) {
            return;
        }

        final String INTERVAL_COUNT_MARKER = "%d";
        String intervalString = mResources.getQuantityString(mIntervalResId, mModel.interval);
        int markerStart = intervalString.indexOf(INTERVAL_COUNT_MARKER);

        if (markerStart != -1) {
          int postTextStart = markerStart + INTERVAL_COUNT_MARKER.length();
          mIntervalPostText.setText(intervalString.substring(postTextStart,
                  intervalString.length()).trim());
          mIntervalPreText.setText(intervalString.substring(0, markerStart).trim());
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

    // Implements OnItemSelectedListener interface
    // Freq spinner
    // End spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mFreqSpinner) {
            mModel.freq = position;
        } else if (parent == mEndSpinner) {
            switch (position) {
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
                    } else if (mModel.endCount > COUNT_MAX) {
                        mModel.endCount = COUNT_MAX;
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
        updateDialog();
    }

    // Implements OnItemSelectedListener interface
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (mModel.endDate == null) {
            mModel.endDate = new Time(mTime.timezone);
            mModel.endDate.hour = mModel.endDate.minute = mModel.endDate.second = 0;
        }
        mModel.endDate.year = year;
        mModel.endDate.month = monthOfYear;
        mModel.endDate.monthDay = dayOfMonth;
        mModel.endDate.normalize(false);
        updateDialog();
    }

    // Implements OnCheckedChangeListener interface
    // Week repeat by day of week
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int itemIdx = -1;
        for (int i = 0; i < 7; i++) {
            if (itemIdx == -1 && buttonView == mWeekByDayButtons[i]) {
                itemIdx = i;
                mModel.weeklyByDayOfWeek[i] = isChecked;
            }
        }
        updateDialog();
    }

    // Implements android.widget.RadioGroup.OnCheckedChangeListener interface
    // Month repeat by radio buttons
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.repeatMonthlyByNthDayOfMonth) {
            mModel.monthlyRepeat = RecurrenceModel.MONTHLY_BY_DATE;
        } else if (checkedId == R.id.repeatMonthlyByNthDayOfTheWeek) {
            mModel.monthlyRepeat = RecurrenceModel.MONTHLY_BY_NTH_DAY_OF_WEEK;
        }
        updateDialog();
    }

    // Implements OnClickListener interface
    // EndDate button
    // Done button
    @Override
    public void onClick(View v) {
        if (mEndDateTextView == v) {
            if (mDatePickerDialog != null) {
                mDatePickerDialog.dismiss();
            }
            //mDatePickerDialog = DatePickerDialog.newInstance(this, mModel.endDate.year,
            //        mModel.endDate.month, mModel.endDate.monthDay);
            //mDatePickerDialog.setFirstDayOfWeek(Utils.getFirstDayOfWeekAsCalendar(getActivity()));
            //mDatePickerDialog.setYearRange(Utils.YEAR_MIN, Utils.YEAR_MAX);
            //mDatePickerDialog.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
        } else if (mDone == v) {
            String rrule;
            if (mModel.recurrenceState == RecurrenceModel.STATE_NO_RECURRENCE) {
                rrule = null;
            } else {
            }
            dismiss();
        }
    }

    public interface OnRecurrenceSetListener {
        void onRecurrenceSet(String rrule);
    }

    public void setOnRecurrenceSetListener(OnRecurrenceSetListener l) {
    }

    private class EndSpinnerAdapter extends ArrayAdapter<CharSequence> {
        final String END_DATE_MARKER = "%s";
        final String END_COUNT_MARKER = "%d";

        private LayoutInflater mInflater;
        private int mItemResourceId;
        private int mTextResourceId;
        private ArrayList<CharSequence> mStrings;
        private String mEndDateString;
        private boolean mUseFormStrings;

        /**
         * @param context
         * @param textViewResourceId
         * @param objects
         */
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

            TextView item = (TextView) v.findViewById(R.id.spinner_item);
            int markerStart;
            switch (position) {
                case RecurrenceModel.END_NEVER:
                    item.setText(mStrings.get(RecurrenceModel.END_NEVER));
                    break;
                case RecurrenceModel.END_BY_DATE:
                    markerStart = mEndDateString.indexOf(END_DATE_MARKER);

                    if (markerStart != -1) {
                        if (mUseFormStrings || markerStart == 0) {
                            // If we get here, the translation of "Until" doesn't work correctly,
                            // so we'll just set the whole "Until a date" string.
                            item.setText(mEndDateLabel);
                        } else {
                            item.setText(mEndDateString.substring(0, markerStart).trim());
                        }
                    }
                    break;
                case RecurrenceModel.END_BY_COUNT:
                    String endString = mResources.getQuantityString(R.plurals.recurrence_end_count,
                            mModel.endCount);
                    markerStart = endString.indexOf(END_COUNT_MARKER);

                    if (markerStart != -1) {
                        if (mUseFormStrings || markerStart == 0) {
                            // If we get here, the translation of "For" doesn't work correctly,
                            // so we'll just set the whole "For a number of events" string.
                            item.setText(mEndCountLabel);
                            // Also, we'll hide the " events" that would have been at the end.
                            mPostEndCount.setVisibility(View.GONE);
                            // Use this flag so the onItemSelected knows whether to show it later.
                            mHidePostEndCount = true;
                        } else {
                            int postTextStart = markerStart + END_COUNT_MARKER.length();
                            mPostEndCount.setText(endString.substring(postTextStart,
                                    endString.length()).trim());
                            // In case it's a recycled view that wasn't visible.
                            if (mModel.end == RecurrenceModel.END_BY_COUNT) {
                                mPostEndCount.setVisibility(View.VISIBLE);
                            }
                            if (endString.charAt(markerStart - 1) == ' ') {
                                markerStart--;
                            }
                            item.setText(endString.substring(0, markerStart).trim());
                        }
                    }
                    break;
                default:
                    v = null;
                    break;
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
