package com.ttcnpm.group28.weatherapp.huylinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.hoangloc.ActivityMain;
import com.ttcnpm.group28.weatherapp.quochoang.ActivityAboutApp;

import java.util.ArrayList;

public class ActivitySetting extends AppCompatActivity {

    SharedPreferences sharedPreferences = ActivityMain.weatherSharedPref;
    //Demo data for display in activity
    ArrayList<SpinnerItem> langList;
    ArrayList<String> interList;
    ArrayList<String> tempList;
    ArrayList<String> lengList;

    public static TextView tvInterval;
    public static TextView tvQuantity;

    //Declerate Spinner
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
        tvInterval = (TextView)findViewById(R.id.tvInterval);
        tvQuantity = (TextView)findViewById(R.id.tvQuantity);
        /*.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();*/

        //Init Location
        initLocation();

        //Init demo data for language
        initLang();

        //Init demo data for temperature
        initTemperature();

        //Init demo data for Unit
        initVelocity();

        //Init Interval data
        initInterval();

        //Init Quantity data
        initQuantity();

    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public void initQuantity() {
        TextView textView = (TextView)findViewById(R.id.tvQuantity);
        String value = Setting.getQuantityOfDaysSetting(sharedPreferences)+" "+getResources().getString(R.string.days);
        textView.setText(value);
    }

    public void initInterval() {
        TextView textView = (TextView)findViewById(R.id.tvInterval);
        String value = getResources().getString(R.string.each)+" "+ Setting.getIntervalSetting(sharedPreferences)+" "+getResources().getString(R.string.minutes);
        textView.setText(value);
    }

    private void initLocation() {
        boolean value = Setting.getRememberLocationSetting(sharedPreferences);
        Switch sw = (Switch) findViewById(R.id.sw_rememberLocation);
        sw.setChecked(value);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                putLocation(b);
            }
        });
    }

    private void initVelocity() {

        lengList = new ArrayList<String>();
        lengList.add(getResources().getString(R.string.meter));
        lengList.add(getResources().getString(R.string.kilometer));
        spinner = (Spinner) findViewById(R.id.sp_velocity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lengList);
        spinner.setAdapter(adapter);
        String value = Setting.getVelocitySetting(sharedPreferences);
        int pos = value.equals(Setting.VelocityUnit.METERS_PER_SECOND)?0:1;
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                putVelocity(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initTemperature() {
        tempList = new ArrayList<String>();;
        tempList.add(getResources().getString(R.string.celcius));
        tempList.add(getResources().getString(R.string.farenheit));
        spinner = (Spinner) findViewById(R.id.sp_temp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tempList);
        spinner.setAdapter(adapter);
        String value = Setting.getTemperatureTypeSetting(sharedPreferences);
        int pos = value.equals(Setting.TemperatureType.CELCIUS)?0:1;
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                putTemperature(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initLang() {
        langList = new ArrayList<SpinnerItem>();
        langList.add(new SpinnerItem(R.raw.vi, getResources().getString(R.string.vietnamese)));
        langList.add(new SpinnerItem(R.raw.en, getResources().getString(R.string.english)));
        spinner = (Spinner) findViewById(R.id.sp_lang);
        SpinnerAdapter langAdapter = new SpinnerAdapter(this, R.layout.spinner_item, langList);
        spinner.setAdapter(langAdapter);
        //set data from setting value
        String value = Setting.getLanguageSetting(sharedPreferences);
        int pos = value.equals(Setting.Language.VIETNAMESE)?0:1;
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                putLang(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void startThemes(View v) {
        Intent themes = new Intent(this, ActivityThemes.class);
        startActivity(themes);
    }

    public void startAboutUs(View v) {
        Intent aboutUs = new Intent(this, ActivityAboutApp.class);
        startActivity(aboutUs);
    }

    public void finishActivity(View v) {
        setResult(RESULT_OK);
        finish();
    }

    private void putLocation(boolean value) {
        Setting.setRememberLocation(sharedPreferences,value);
    }

    private void putVelocity(int pos) {
        String value = pos == 0 ? Setting.VelocityUnit.METERS_PER_SECOND : Setting.VelocityUnit.KILOMETERS_PER_SECOND;
        Setting.setVelocity(sharedPreferences,value);
    }

    private void putTemperature(int pos) {
        String value = pos == 0?Setting.TemperatureType.CELCIUS : Setting.TemperatureType.FAHRENHEIT;
        Setting.setTemperatureType(sharedPreferences, value);
    }

    private void putLang(int pos) {
        String value = pos == 0?Setting.Language.VIETNAMESE : Setting.Language.ENGLISH;
        Setting.setLanguage(sharedPreferences, value);
    }

    public void resetSetting(View view){
        Setting.resetSetting(sharedPreferences);
        initLocation();
        initLang();
        initVelocity();
        initTemperature();
        initQuantity();
        initInterval();
        Toast.makeText(getApplicationContext(), "Resetted", Toast.LENGTH_SHORT).show();
    }

    public void startInterval(View view){
        IntervalPopup intervalPopup = new IntervalPopup(this);
        intervalPopup.show();
    }

    public void startQuantity(View view){
        QuantityPopup quantityPopup = new QuantityPopup(this);
        quantityPopup.show();
    }
}
