package com.dissonant.quotas.ui.dialogs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dissonant.quotas.R;
import com.dissonant.quotas.model.ColorListItem;
import com.dissonant.quotas.ui.adapters.ColorListAdapter;

public class ColorPickerDialog extends DialogFragment {
    AlertDialog.Builder builder;
    LayoutInflater inflater;
    View m_DialogView;

    ColorListAdapter m_cListAdapter;
    ArrayList<ColorListItem> colorArray;
    TextView colorNameView;
    ImageView colorView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        colorArray = initColorArray();
        // Get dialog bulder and inflate the dialog layout
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();

        m_DialogView = inflater.inflate(R.layout.dialog_colorpicker, null);
        colorNameView = (TextView) getActivity().findViewById(R.id.color_value);
        colorView = (ImageView) getActivity().findViewById(R.id.color_icon);

        m_cListAdapter = new ColorListAdapter(getActivity().getBaseContext(),
                R.layout.colorlist_row, colorArray);

        builder.setView(m_DialogView);
        builder.setAdapter(m_cListAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                colorNameView.setText(colorArray.get(which).getName());
                colorView.setColorFilter(colorArray.get(which).getColor());
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Safety check
        if (getDialog() == null) {
            return;
        }

        // Set the animation to use on showing and hiding the dialog
        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_slide_up);
    }
    
    public ArrayList<ColorListItem> initColorArray() {
        ArrayList<ColorListItem> cArrayResult = new ArrayList<ColorListItem>();

        int[] colorArray = getResources()
                .getIntArray(R.array.default_color_array);
        String[] colorNameArray = getResources()
            .getStringArray(R.array.color_name_array);

        for (int i = 0; i < colorArray.length; i++) {
            ColorListItem cListItem = new ColorListItem(
                    colorArray[i], colorNameArray[i]);
            cArrayResult.add(cListItem);
        }
        return cArrayResult;
    }
}
