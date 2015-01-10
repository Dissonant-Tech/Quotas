package com.dissonant.quotas.ui.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dissonant.quotas.R;
import com.dissonant.quotas.model.ColorListItem;


public class ColorListAdapter extends ArrayAdapter<ColorListItem>
    implements ListAdapter {

    Context context;
    int layoutId;
    ArrayList<ColorListItem> colorArray;

    public ColorListAdapter(Context context, int layoutId, ArrayList<ColorListItem> colorArray) {
        super(context, layoutId, colorArray);

        this.context = context;
        this.layoutId = layoutId;
        this.colorArray = colorArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Initialize layout infalter and rows
        LayoutInflater inflater = (LayoutInflater)
            context.getSystemService( Context.LAYOUT_INFLATER_SERVICE  );
        View row = inflater.inflate(layoutId, parent, false);

        TextView rowLabel = (TextView) row.findViewById(R.id.label);
        ImageView rowColor = (ImageView) row.findViewById(R.id.icon);

        // Setup row
        rowLabel.setText(colorArray.get(position).getName());
        rowColor.setColorFilter(colorArray.get(position).getColor());

        return row;
    }

}
