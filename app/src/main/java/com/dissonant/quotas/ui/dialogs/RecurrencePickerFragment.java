package com.dissonant.quotas.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dissonant.quotas.R;

public class RecurrencePickerFragment extends DialogFragment {

    public interface RecurrencePickerListener {
        void onRecurrenceSet();
    }

	private Context context;
	private RecurrencePickerListener listener;
    private View dialogView;

    public RecurrencePickerFragment(Context context, RecurrencePickerListener listener) {
		this.context = context;
		this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_recurrancepicker, null);

        setupView();

        builder.setView(dialogView);
        return builder.create();
    }

    public void setupView() {
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurrence_options, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public String genRecurranceString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
