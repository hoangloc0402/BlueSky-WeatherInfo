package com.ttcnpm.group28.weatherapp.huylinh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttcnpm.group28.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {
    private ArrayList<SpinnerItem> itemList;
    private Context context;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SpinnerItem> itemList) {
        super(context, resource, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return spinnerView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return spinnerView(position, convertView, parent);
    }

    private View spinnerView(int possition, @Nullable View view, @NonNull ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView textView = (TextView)customView.findViewById(R.id.tvValue);
        ImageView imageView = (ImageView)customView.findViewById(R.id.ivIcon);
        textView.setText(itemList.get(possition).getValue());
        imageView.setImageResource(itemList.get(possition).getIcon());
        return customView;
    }
}
