package com.ttcnpm.group28.weatherapp.hoangloc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;
import com.ttcnpm.group28.weatherapp.huylinh.ActivitySetting;
import com.ttcnpm.group28.weatherapp.namquan.ActivityMapLayers;
import java.util.Calendar;
import java.util.Locale;


public class ActivityMain extends AppCompatActivity {

    public static final int PLACE_PICKER_REQUEST = 1000;
    public static final int SETTING_REQUEST = 2000;
    public static String PACKAGE_NAME;
    public static SharedPreferences weatherSharedPref;
    public static SharedPreferences.Editor weatherSharedPrefEditor;
    public FloatingActionMenu famMenu;
    FragmentWeather fragmentWeather;
    FloatingActionButton fabSetting;
    FloatingActionButton fabChooseLocation;
    FloatingActionButton fabMap;
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        weatherSharedPref = getSharedPreferences(WeatherKeyWord.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        weatherSharedPrefEditor = weatherSharedPref.edit();
        weatherSharedPrefEditor.putString(WeatherKeyWord.CURRENT_VELOCITY, WeatherKeyWord.METER_PER_SECOND);
        setAppLanguage();
        WeatherUtils.weatherResources = getResources();
        setContentView(R.layout.activity_main);

        mClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        fragmentWeather = new FragmentWeather();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.placeHolder, fragmentWeather).commit();
        }

        famMenu = findViewById(R.id.fabMenu);
        fabSetting = findViewById(R.id.fabSetting);
        fabChooseLocation = findViewById(R.id.fabChooseLocation);
        fabMap = findViewById(R.id.fabMap);

        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                famMenu.close(true);
                Intent intent = new Intent(ActivityMain.this, ActivitySetting.class);
                startActivityForResult(intent, SETTING_REQUEST);
            }
        });

        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                famMenu.close(true);
                Intent intent = new Intent(ActivityMain.this, ActivityMapLayers.class);
                startActivity(intent);
            }
        });

        fabChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                famMenu.close(true);
                startPlacePicker();
            }
        });

        Button tv = findViewById(R.id.tvL);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlacePicker();
            }
        });
    }

    void setAppLanguage() {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        String currentLang = weatherSharedPref.getString(WeatherKeyWord.CURRENT_LANGUAGE, "vi");
        if (currentLang.equals("en"))
            conf.setLocale(new Locale("en"));
        else conf.setLocale(new Locale("vi"));
        res.updateConfiguration(conf, dm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long lastUpdated = weatherSharedPref.getLong(WeatherKeyWord.LAST_UPDATED, currentTime);
        int interval = weatherSharedPref.getInt(WeatherKeyWord.CURRENT_INTERVAL, 45);
        if ((currentTime - lastUpdated) / 60000 > interval)
            restart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    protected void onStop() {
        mClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                //String placeName = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());

                if (address.length() < 6) address = getString(R.string.unnamed_place);
                Toast.makeText(this, address, Toast.LENGTH_LONG).show();

                weatherSharedPrefEditor.putString(WeatherKeyWord.CURRENT_PLACE_NAME, address);
                weatherSharedPrefEditor.putString(WeatherKeyWord.CURRENT_LATITUDE, latitude);
                weatherSharedPrefEditor.putString(WeatherKeyWord.CURRENT_LONGITUDE, longitude);
                weatherSharedPrefEditor.commit();
                fragmentWeather.getNewWeatherData(Double.valueOf(latitude), Double.valueOf(longitude));
            }
        }
        else if (requestCode == SETTING_REQUEST && resultCode == RESULT_OK) {
            weatherSharedPrefEditor.putBoolean(WeatherKeyWord.RESTART_FROM_SETTING, true);
            weatherSharedPrefEditor.commit();
            restart();
        }
    }

    public void startPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        LatLng northEast = new LatLng(27, 114);
        LatLng southWest = new LatLng(9, 96);
        builder.setLatLngBounds(new LatLngBounds(southWest, northEast));
        try {
            startActivityForResult(builder.build(ActivityMain.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    void restart() {
        Intent i = new Intent(this, ActivityMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}

