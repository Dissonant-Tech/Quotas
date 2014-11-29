package com.dissonant.quotas.ui.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dissonant.quotas.R;
import com.dissonant.quotas.ui.adapters.SwapSpinnerAdapter;

public class EndSpinner implements OnItemSelectedListener,
       DatePickerDialog.OnDateSetListener, View.OnClickListener {

	private Context mContext;
	private View mView;
    private ViewGroup mParentView;
    private Spinner mSpinner;
    private SwapSpinnerAdapter mSwapSpinnerAdapter;
	private ArrayList<CharSequence> mListItems;
	private ArrayList<CharSequence> mSelectedItems;
    private SimpleDateFormat sdf;
    private Resources mResources;

    private Button mDateButton;
    private EditText mCountEdit;
    private TextView mCountText;

    public EndSpinner(Context context, AttributeSet attrs) {
        mContext = context;

        init();
    }

    public EndSpinner(Context context, ViewGroup parent, ArrayList<CharSequence> listItems,
            ArrayList<CharSequence> selectedItems) {
		mContext = context;
		mParentView = parent;
		mListItems = listItems;
		mSelectedItems = selectedItems;

        init();
    }

    public EndSpinner(Context context, ViewGroup parent, int listItemsId,
            int selectedItemsId) {
		mContext = context;
		mParentView = parent;
		mListItems = getCharSequenceArray(listItemsId);
		mSelectedItems = getCharSequenceArray(selectedItemsId);

        init();
    }

    public void init() {
        mResources = mContext.getResources();
        sdf = new SimpleDateFormat("MM'/'d'/'yy");

        LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.view_end_spinner, mParentView);

        mSpinner = (Spinner) mView.findViewById(R.id.end_spinner);
        mSpinner.setOnItemSelectedListener(this);

        mSwapSpinnerAdapter = new SwapSpinnerAdapter(((Activity) mContext), mListItems, mSelectedItems,
                R.layout.recurrencepicker_freq_item, R.layout.recurrencepicker_end_text);
        mSwapSpinnerAdapter.setDropDownViewResource(R.layout.recurrencepicker_freq_item);
        mSpinner.setAdapter(mSwapSpinnerAdapter);

        Calendar cal = Calendar.getInstance();
        mDateButton = (Button) mView.findViewById(R.id.end_button);
        mDateButton.setText(sdf.formatToCharacterIterator(cal.getTime()).toString());
        mDateButton.setOnClickListener(this);

        mCountText = (TextView) mView.findViewById(R.id.end_text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pos == 0) {
            mDateButton.setVisibility(View.GONE);
            mCountText.setVisibility(View.GONE);
        } else if (pos == 1) {
            mDateButton.setVisibility(View.VISIBLE);
            mCountText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
    }

    @Override
    public void onClick(View v) {
    }

    public ArrayList<CharSequence> getCharSequenceArray(int resourceId) {
        ArrayList<CharSequence> result;
        result = new ArrayList<CharSequence>(Arrays.asList(mResources.getStringArray(resourceId)));
        return result;
    }
}
