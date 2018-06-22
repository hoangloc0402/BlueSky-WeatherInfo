package com.ttcnpm.group28.weatherapp.huylinh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ttcnpm.group28.weatherapp.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class QuantityPopup extends IntervalPopup {

    public QuantityPopup(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void initIntervals() {
        TextView textView = (TextView)findViewById(R.id.txt_dia);
        textView.setText("Quantity of forecast days");
        textView = (TextView)findViewById(R.id.tv_intervalValue);
        this.value = Setting.getQuantityOfDaysSetting(sharedPreferences);
        textView.setText(String.valueOf(this.value));
        seekBar.setMax(7);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView textView = (TextView)findViewById(R.id.tv_intervalValue);
                textView.setText(String.valueOf(i+7));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(this.value-7);
        //Up and down button
        ImageButton button = (ImageButton)findViewById(R.id.ibtn_IntervalUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = seekBar.getProgress();
                if(value<7){
                    seekBar.setProgress(value + 1);
                }
            }
        });
        button = (ImageButton)findViewById(R.id.ibtn_IntervalDown);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = seekBar.getProgress();
                if(value>0){
                    seekBar.setProgress(value-1);
                }
            }
        });
    }

    @Override
    public void setValue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        this.value = seekBar.getProgress()+7;
        Setting.setQuantityOfDays(sharedPreferences,this.value);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_interOk:
                setValue();
                String value = String.valueOf(this.getValue())+
                        " "+activity.getResources().getString(R.string.days);
                ActivitySetting.tvQuantity.setText(value);
                dismiss();
                break;

            case R.id.btn_inter_cancel:
                dismiss();
                break;

            default:
                dismiss();
        }
    }


}
