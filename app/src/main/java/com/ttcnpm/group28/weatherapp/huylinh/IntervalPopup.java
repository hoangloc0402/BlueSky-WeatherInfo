package com.ttcnpm.group28.weatherapp.huylinh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ttcnpm.group28.weatherapp.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class IntervalPopup extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public Button btnOk, btnCancel;
    protected int value;

    protected SharedPreferences sharedPreferences;
    protected SeekBar seekBar;


    public IntervalPopup(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.interval_popup);
        sharedPreferences = activity.getSharedPreferences(Setting.NAME, MODE_PRIVATE);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        initIntervals();
        btnOk = (Button)findViewById(R.id.btn_interOk);
        btnCancel = (Button)findViewById(R.id.btn_inter_cancel);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    protected void initIntervals() {
        TextView textView = (TextView)findViewById(R.id.tv_intervalValue);
        this.value = Setting.getIntervalSetting(sharedPreferences);
        textView.setText(String.valueOf(this.value));
        seekBar.setMax(45);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView textView = (TextView)findViewById(R.id.tv_intervalValue);
                textView.setText(String.valueOf(i+15));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(this.value-15);
        //Up and down button
        ImageButton button = (ImageButton)findViewById(R.id.ibtn_IntervalUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = seekBar.getProgress();
                if(value<45){
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

    public int getValue(){
        return value;
    }

    public void setValue(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        this.value = seekBar.getProgress()+15;
        Setting.setInterval(sharedPreferences, this.value);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_interOk:
                setValue();
                String value = activity.getResources().getString(R.string.each)+" "+ String.valueOf(this.getValue())+
                        " "+activity.getResources().getString(R.string.minutes);
                ActivitySetting.tvInterval.setText(value);
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
