package com.dissonant.quotas.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.dissonant.quotas.R;

public class RecurrencePickerFragment extends DialogFragment
    implements OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    public interface RecurrencePickerListener {
        void onRecurrenceSet();
    }

	private Context context;
	private RecurrencePickerListener listener;

    private LayoutInflater inflater;
    private View dialogView;
    private View dialogBodyView;

    public RecurrencePickerFragment(Context context, RecurrencePickerListener listener) {
		this.context = context;
		this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_recurrancepicker, null);

        setupView();

        builder.setView(dialogView);
        return builder.create();
    }

    public void setupView() {
        // Setup Spinner
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.recurrence_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurrence_options, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Setup dialog body
        dialogBodyView = dialogView.findViewById(R.id.recurrence_body);

        // Setup dialog switch
        Switch dialogToggle = (Switch) dialogView.findViewById(R.id.recurrence_toggle);
        dialogToggle.setOnCheckedChangeListener(this);
    }

    public String genRecurranceString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = parent.getItemAtPosition(pos).toString();

        if (selected == getString(R.string.recurrence_daily)) {

        } else if (selected == getString(R.string.recurrence_weekly)) {

        } else if (selected == getString(R.string.recurrence_monthly)) {

        } else if (selected == getString(R.string.recurrence_yearly)) {

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            dialogView.setEnabled(isChecked);
    }
}
