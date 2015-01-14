package com.dissonant.quotas.controllers;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.dissonant.quotas.R;
import com.dissonant.quotas.model.ColorListItem;
import com.dissonant.quotas.ui.adapters.ColorListAdapter;
import com.dissonant.quotas.ui.views.EditView;

public class ColorPickerController extends DialogFragment implements OnClickListener {

    private Context context;
    private ColorPickerListener listener;
    private EditView editView;

    private LayoutInflater inflater;
    private View dialogView;

    private ColorListAdapter colorListAdapter;
    private ArrayList<ColorListItem> colorArray;

    public ColorPickerController(Context context, ColorPickerListener listener, EditView editView){
        this.context = context;
        this.listener = listener;
        this.editView = editView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        colorArray = initColorArray();
        // Get dialog bulder and inflate the dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.dialog_colorpicker, null);

        colorListAdapter = new ColorListAdapter(getActivity().getBaseContext(),
                R.layout.colorlist_row, colorArray);

        builder.setView(dialogView);
        builder.setAdapter(colorListAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                listener.onColorSet(colorArray.get(which).getName(), colorArray.get(which).getColor());
                editView.setColorPicked(colorArray.get(which).getName(), colorArray.get(which).getColor());
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

    @Override
    public void onClick(View v) {
        this.show(((Activity) this.context).getFragmentManager(), "colorPicker");
    }

    public interface ColorPickerListener {
        void onColorSet(String name, int color);
    }
}
