package com.ttcnpm.group28.weatherapp.hoangloc;

import android.Manifest;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;
import com.ttcnpm.group28.weatherapp.dinhduc.DatabaseManager;
import com.ttcnpm.group28.weatherapp.namquan.DataFetcher;
import com.ttcnpm.group28.weatherapp.namquan.WeatherInformationPacket;
import com.ttcnpm.group28.weatherapp.quochoang.DetailAnalyticActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static java.lang.Thread.sleep;

public class FragmentWeather extends Fragment{
    public DataFetcher dataFetcher;
    public static Handler mHandler;
    DatabaseManager weatherDBS;
    ProgressDialog dialogLoading;
    public ArrayList<WeatherInformationPacket> forecastHourData;
    public ArrayList<WeatherInformationPacket> forecastDayData;
    View myView;
    TextView textViewHumidity;
    TextView textViewWindSpeed;
    TextView textViewDetail;
    TextView textViewCloudCover;
    TextView textViewUpdated;
    TextView textViewWarning;
    TextView textViewWeatherStatus;
    TextView textViewCurrentLocation;
    TextView textViewCurrentTemperature;
    TextView textViewCelciusOrFarenheit;
    ImageView imageViewWeatherStatus;
    ImageView imageViewCurrentHumidity;
    ImageView imageViewCurrentCloudCover;
    ImageView imageViewCurrentWindSpeed;
    CardView cardViewForecast;
    RecyclerView recyclerViewForecastHour;
    RecyclerView recyclerViewForecastDay;
    AdapterRCVForecastHour adapterRCVForecastHour;
    AdapterRCVForecastDay adapterRCVForecastDay;
    ScrollView scrollView;
    Boolean flag =  true;
    int backgroundColor;
    int backgroundImage;
    int weatherStatusImage;

    public FragmentWeather() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleMessageFromWorkerThread();
        weatherDBS = new DatabaseManager(getContext());
        dialogLoading = new ProgressDialog(getActivity());
        dialogLoading.setCancelable(true);
        dialogLoading.setMessage(getString(R.string.loading_data));
        //countInterval();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView =  inflater.inflate(R.layout.fragment_weather, container, false);
        setupAllViewContents();

        if (isNetworkConnected()){
            if (ActivityMain.weatherSharedPref.getBoolean(WeatherKeyWord.RESTART_FROM_SETTING, false) ){
                ActivityMain.weatherSharedPrefEditor.putBoolean(WeatherKeyWord.RESTART_FROM_SETTING, false ).commit();
                getNewWeatherData();
            }
            else if (ActivityMain.weatherSharedPref.getBoolean(WeatherKeyWord.REMEMBER_MY_LOCATION, false)){
                getNewWeatherData();
            }
            else {
                getLocation();
                ActivityMain.weatherSharedPrefEditor.putBoolean(WeatherKeyWord.CURRENT_PLACE_NAME_GET_FROM_SERVER, true);
            }
        }
        else {
            showAlertDialogInternetConnectionError();
        }
        return myView;
    }

    void setupAllViewContents(){
        textViewCurrentLocation =  myView.findViewById(R.id.textViewLocation);
        textViewCurrentLocation.setSelected(true);
        textViewCurrentTemperature = myView.findViewById(R.id.textViewTemperature);
        textViewCelciusOrFarenheit = myView.findViewById(R.id.textViewCelciusOrFarenheit);
        textViewCloudCover = myView.findViewById(R.id.textViewCloudCover);
        textViewHumidity =  myView.findViewById(R.id.textViewHumidity);
        textViewWindSpeed = myView.findViewById(R.id.textViewWindSpeed);
        textViewWarning =  myView.findViewById(R.id.textViewWarnings);
        textViewDetail = myView.findViewById(R.id.buttonAnalytics);
        textViewWarning.setSelected(true);
        textViewWeatherStatus =  myView.findViewById(R.id.textViewWeatherStatus);
        textViewWeatherStatus.setSelected(true);
        textViewUpdated =  myView.findViewById(R.id.textViewUpdated);
        imageViewWeatherStatus = myView.findViewById(R.id.imageViewWeatherStatus);
        imageViewCurrentHumidity = myView.findViewById(R.id.imageViewHumidity);
        imageViewCurrentCloudCover = myView.findViewById(R.id.imageViewCloudCover);
        imageViewCurrentWindSpeed= myView.findViewById(R.id.imageViewWindSpeed);
        scrollView = myView.findViewById(R.id.scrollViewWeatherFragment);
        cardViewForecast = myView.findViewById(R.id.cardViewForecast);
        cardViewForecast.setVisibility(View.INVISIBLE);
        recyclerViewForecastHour = myView.findViewById(R.id.recyclerViewForecastHour);
        recyclerViewForecastDay =  myView.findViewById(R.id.recyclerViewForecastDay);
        recyclerViewForecastHour.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewForecastDay.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapterRCVForecastHour = new AdapterRCVForecastHour(null);
        adapterRCVForecastDay = new AdapterRCVForecastDay(null);
        recyclerViewForecastHour.setAdapter(adapterRCVForecastHour);
        recyclerViewForecastHour.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                ((ActivityMain)getActivity()).famMenu.close(true);
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });
        recyclerViewForecastDay.setAdapter(adapterRCVForecastDay);
        recyclerViewForecastDay.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                ((ActivityMain)getActivity()).famMenu.close(true);
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });
        myView.findViewById(R.id.constraintLayoutBackground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityMain)getActivity()).famMenu.close(true);
            }
        });

        Button showAnalytic = myView.findViewById(R.id.buttonAnalytics);
        showAnalytic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailAnalyticActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("forecastHourData", forecastHourData);
                bundle.putSerializable("forecastDayData", forecastDayData);
                bundle.putSerializable("backgroundColor", backgroundColor);
                bundle.putSerializable("backgroundImage", backgroundImage);
                bundle.putSerializable("weatherStatusImage", weatherStatusImage);
                intent.putExtras(bundle);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageViewWeatherStatus, "imageViewWeatherStatusTransition");
                ActivityCompat.startActivity(getActivity(),intent, options.toBundle());
            }
        });
    }

    void handleMessageFromWorkerThread(){
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (msg == null) return;
                switch (msg.what){
                    case WeatherKeyWord.MSG_RESTART:
                        ((ActivityMain)getActivity()).restart();
                        break;
                    case WeatherKeyWord.MSG_INTERNET_CONNECTION_FAIL:
                        showAlertDialogInternetConnectionError();
                        break;
                    case WeatherKeyWord.MSG_RECEIVE_HOUR_DATA:
                        forecastHourData = (ArrayList<WeatherInformationPacket>) msg.obj;
                        adapterRCVForecastHour.setData(forecastHourData);
                        synchronized (adapterRCVForecastHour){
                            adapterRCVForecastHour.notifyDataSetChanged();
                        }
                        long date = Calendar.getInstance().getTimeInMillis();
                        ActivityMain.weatherSharedPrefEditor.putLong(WeatherKeyWord.LAST_UPDATED, date).commit();
                        setCurrentWeatherStatusView(forecastHourData.get(forecastHourData.size()-1));
                        weatherDBS.addInformation(forecastHourData, DatabaseManager.TABLE_WEATHER_INFO_HOUR);
                        break;
                    case WeatherKeyWord.MSG_RECEIVE_DAY_DATA:
                        forecastDayData = (ArrayList<WeatherInformationPacket>) msg.obj;
                        adapterRCVForecastDay.setData(forecastDayData);
                        synchronized (adapterRCVForecastDay){
                            adapterRCVForecastDay.notifyDataSetChanged();
                        }
                        weatherDBS.addInformation(forecastDayData, DatabaseManager.TABLE_WEATHER_INFO_DAY);
                        sendDataToWidget();
                        dialogLoading.cancel();
                        break;

                    case WeatherKeyWord.MSG_RECEIVE_HOUR_DATA_DBS:
                        forecastHourData = (ArrayList<WeatherInformationPacket>) msg.obj;
                        adapterRCVForecastHour.setData(forecastHourData);
                        synchronized (adapterRCVForecastHour){
                            adapterRCVForecastHour.notifyDataSetChanged();
                        }

                        break;
                    case WeatherKeyWord.MSG_RECEIVE_DAY_DATA_DBS:
                        forecastDayData = (ArrayList<WeatherInformationPacket>) msg.obj;
                        adapterRCVForecastDay.setData(forecastDayData);
                        synchronized (adapterRCVForecastDay){
                            adapterRCVForecastDay.notifyDataSetChanged();
                        }
                        setCurrentWeatherStatusViewForNoInternetConnection();
                        break;
                    default: break;
                }
            }
        };
    }

    void sendDataToWidget(){
        Intent intent = new Intent(this.getContext(), WeatherAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Bundle widgetBundle = new Bundle();
        widgetBundle.putSerializable("WIP0", forecastHourData.get(forecastHourData.size()-1));
        widgetBundle.putSerializable("WIP1", forecastDayData.get(1));
        widgetBundle.putSerializable("WIP2", forecastDayData.get(2));
        widgetBundle.putSerializable("WIP3", forecastDayData.get(3));
        widgetBundle.putSerializable("WIP4", forecastDayData.get(4));
        intent.putExtras(widgetBundle);
        int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getContext(), WeatherAppWidget.class));
        if(ids != null && ids.length > 0) {
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            getContext().sendBroadcast(intent);
        }
    }

    void showAlertDialogInternetConnectionError(){
        loadDataFromDBS();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getString(R.string.connection_failed));
        alertDialogBuilder.setMessage(getString(R.string.unable_to_connect_to_server));
        alertDialogBuilder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getNewWeatherData();
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogLoading.cancel();
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setIcon(R.drawable.ic_download);
        alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogLoading.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    void getNewWeatherData(){
        Double lat = Double.valueOf(ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_LATITUDE, "10.0"));
        Double lon = Double.valueOf(ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_LONGITUDE, "10.0"));
        dataFetcher = new DataFetcher();
        dataFetcher.setCoord(lat, lon);
        dataFetcher.start();
        dialogLoading.show();
    }

    void getNewWeatherData(Double lat, Double lon){
        dataFetcher = new DataFetcher();
        dataFetcher.setCoord(Double.valueOf(lat), Double.valueOf(lon));
        dataFetcher.start();
        dialogLoading.show();
    }

    boolean getLocation(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (flag) {
                    ActivityMain.weatherSharedPrefEditor.putString(WeatherKeyWord.CURRENT_LATITUDE, String.valueOf(location.getLatitude()));
                    ActivityMain.weatherSharedPrefEditor.putString(WeatherKeyWord.CURRENT_LONGITUDE, String.valueOf(location.getLongitude()));
                    ActivityMain.weatherSharedPrefEditor.commit();
                    getNewWeatherData(location.getLatitude(), location.getLongitude());
                }
                flag = false;
            }
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider) { }
            public void onProviderDisabled(String provider) { }
        };


        //String locationProviderG = LocationManager.NETWORK_PROVIDER;
        //String locationProvider = LocationManager.GPS_PROVIDER;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showAlertDialogLocationError();
            return false;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 10, locationListener);
        //locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return true;
    }

    boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) (getActivity().getSystemService(Context.CONNECTIVITY_SERVICE));
        return cm.getActiveNetworkInfo() != null;
    }

    void showAlertDialogLocationError() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getString(R.string.choose_location))
                .setMessage(getString(R.string.please_enable_gps))
                .setPositiveButton(getString(R.string.gps_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivity(myIntent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(getString(R.string.open_map), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.dismiss();
                        ((ActivityMain)getActivity()).startPlacePicker();
                    }
                });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        dialog.show();
    }

    void setCurrentWeatherStatusView(WeatherInformationPacket WIP){
        if (ActivityMain.weatherSharedPref.getBoolean(WeatherKeyWord.CURRENT_PLACE_NAME_GET_FROM_SERVER, false) == true){
            textViewCurrentLocation.setText(WIP.areaName);
            ActivityMain.weatherSharedPrefEditor.putBoolean(WeatherKeyWord.CURRENT_PLACE_NAME_GET_FROM_SERVER, false);
        }
        else
            textViewCurrentLocation.setText(ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_PLACE_NAME,""));

        if (ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, WeatherKeyWord.CELCIUS).equals(WeatherKeyWord.CELCIUS))
            textViewCurrentTemperature.setText(WeatherUtils.convertTemperatureWithoutConcat(WIP.tempC, false));
        else
            textViewCurrentTemperature.setText(WeatherUtils.convertTemperatureWithoutConcat(WIP.tempC, true));

        textViewCelciusOrFarenheit.setText(
                ActivityMain.weatherSharedPref.getString(
                        WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, WeatherKeyWord.CELCIUS));
        textViewHumidity.setText(String.valueOf(WIP.humidity) + " %");
        textViewCloudCover.setText(String.valueOf(WIP.cloudCover)+ " %");

        if (ActivityMain.weatherSharedPref.getString(
                WeatherKeyWord.CURRENT_VELOCITY, WeatherKeyWord.METER_PER_SECOND).equals(WeatherKeyWord.KILOMETER_PER_HOUR))
            textViewWindSpeed.setText(WeatherUtils.convertSpeed(WIP.windSpeedKmph, false));
        else textViewWindSpeed.setText(WeatherUtils.convertSpeed(WIP.windSpeedKmph, true));

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        textViewUpdated.setText(df.format(ActivityMain.weatherSharedPref.getLong(WeatherKeyWord.LAST_UPDATED, 0)));

        Resources currentResources = getActivity().getResources();
        backgroundColor = currentResources.getIdentifier("cardViewBackGroundColor" + String.valueOf((Math.abs(new Random().nextInt())) % 6) , "color", getActivity().getPackageName());
        cardViewForecast.setCardBackgroundColor(currentResources.getColor(backgroundColor));

        int resourceCode = currentResources.getIdentifier("code" + WIP.weatherCode, "string", ActivityMain.PACKAGE_NAME);
        textViewWeatherStatus.setText(getString(resourceCode));

        int weatherCode = Integer.valueOf(WIP.weatherCode);
        weatherStatusImage = WeatherUtils.getWeatherStatusResourceID(weatherCode);
        imageViewWeatherStatus.setBackgroundResource(weatherStatusImage);
        textViewWarning.setText(WeatherUtils.getWarningStringId(weatherCode));

        int themeID = ActivityMain.weatherSharedPref.getInt(WeatherKeyWord.CURRENT_THEME,0);
        String backgroundID = WeatherUtils.getWeatherStatusBackgroundID(weatherCode,themeID);
        backgroundImage = currentResources.getIdentifier(backgroundID, "drawable", ActivityMain.PACKAGE_NAME);

        scrollView.setBackgroundResource(backgroundImage);
        cardViewForecast.setVisibility(View.VISIBLE);
        imageViewWeatherStatus.setVisibility(View.VISIBLE);
        imageViewCurrentWindSpeed.setVisibility(View.VISIBLE);
        imageViewCurrentCloudCover.setVisibility(View.VISIBLE);
        imageViewCurrentHumidity.setVisibility(View.VISIBLE);
        textViewDetail.setVisibility(View.VISIBLE);
        cardViewForecast.invalidate();
    }

    void setCurrentWeatherStatusViewForNoInternetConnection(){
        scrollView.setBackgroundResource(R.drawable.countryside_rain);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        textViewUpdated.setText(df.format(ActivityMain.weatherSharedPref.getLong(WeatherKeyWord.LAST_UPDATED, 0)));
        textViewCurrentLocation.setText(ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_PLACE_NAME,""));
        textViewWeatherStatus.setText(R.string.fail_to_connect);
        cardViewForecast.setVisibility(View.VISIBLE);
    }

    void loadDataFromDBS(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                forecastHourData = weatherDBS.getInformationUseful(DatabaseManager.TABLE_WEATHER_INFO_HOUR);
                forecastDayData = weatherDBS.getInformationUseful(DatabaseManager.TABLE_WEATHER_INFO_DAY);
                Message completeMessageHour = FragmentWeather.mHandler.obtainMessage(WeatherKeyWord.MSG_RECEIVE_HOUR_DATA_DBS, forecastHourData);
                completeMessageHour.sendToTarget();
                Message completeMessageDay = FragmentWeather.mHandler.obtainMessage(WeatherKeyWord.MSG_RECEIVE_DAY_DATA_DBS, forecastDayData);
                completeMessageDay.sendToTarget();
            }
        }).start();
    }

    @Override
    public void onPause() {
        if (dialogLoading != null)
            dialogLoading.dismiss();
        super.onPause();
    }



    public static String convertTemperature(int tempC){
        if (ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, "C").equals("C")){
            return String.valueOf(tempC) + "°";
        } else {
            Double tempF = tempC*1.8 + 32;
            String s = String.valueOf(tempF);
            if (s.length() > 4) s = s.substring(0,4);
            return  s + "°";
        }
    }

    public static String convertSpeed(int speedKmh){
        if (ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_VELOCITY,"ms").equals("kmh")){
            String s = String.valueOf(0.277f*speedKmh);
            if (s.length() > 4) s = s.substring(0,4);
            return  s + " m/s";
        }
        else return String.valueOf(speedKmh) + " km/h";
    }
}