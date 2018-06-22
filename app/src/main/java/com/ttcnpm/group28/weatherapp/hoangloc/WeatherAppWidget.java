package com.ttcnpm.group28.weatherapp.hoangloc;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;
import com.ttcnpm.group28.weatherapp.namquan.DataFetcher;
import com.ttcnpm.group28.weatherapp.namquan.WeatherInformationPacket;

import java.util.ArrayList;

public class WeatherAppWidget extends AppWidgetProvider {
    static RemoteViews remoteViews;
    static int appWidgetId;
    static AppWidgetManager appWidgetManager;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        WeatherAppWidget.remoteViews = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
        WeatherAppWidget.appWidgetId = appWidgetId;
        WeatherAppWidget.appWidgetManager = appWidgetManager;
        Intent intent = new Intent(context, ActivityMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widgetWeather, pendingIntent);
        new dataFetcherAsync(remoteViews, appWidgetId, appWidgetManager).execute();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Bundle widgetBundle = intent.getExtras();
        if (widgetBundle == null) return;
        WeatherInformationPacket WIP0, WIP1, WIP2, WIP3, WIP4;
        WIP0 = (WeatherInformationPacket) widgetBundle.getSerializable("WIP0");
        WIP1 = (WeatherInformationPacket) widgetBundle.getSerializable("WIP1");
        WIP2 = (WeatherInformationPacket) widgetBundle.getSerializable("WIP2");
        WIP3 = (WeatherInformationPacket) widgetBundle.getSerializable("WIP3");
        WIP4 = (WeatherInformationPacket) widgetBundle.getSerializable("WIP4");
        if (WIP0 == null) return;
        String placeName = "";
        if (ActivityMain.weatherSharedPref != null)
            placeName = ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_PLACE_NAME, "");

        boolean toF = ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, "C").equals("F");

        remoteViews.setTextViewText(R.id.textViewWidgetDay0Location, placeName);
        remoteViews.setImageViewResource(R.id.imageViewWidgetDay0WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP0.weatherCode)));
        remoteViews.setTextViewText(R.id.textViewWidgetDay0Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP0.tempC, toF));
        remoteViews.setTextViewText(R.id.textViewWidgetDay0DayOfWeek, WeatherUtils.getDayEEEE(WIP0));

        remoteViews.setImageViewResource(R.id.imageViewWidgetDay1WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP1.weatherCode)));
        remoteViews.setTextViewText(R.id.textViewWidgetDay1Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP1.tempC, toF));
        remoteViews.setTextViewText(R.id.textViewWidgetDay1DayOfWeek, WeatherUtils.getDayEEE(WIP1));

        remoteViews.setImageViewResource(R.id.imageViewWidgetDay2WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP2.weatherCode)));
        remoteViews.setTextViewText(R.id.textViewWidgetDay2Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP2.tempC, toF));
        remoteViews.setTextViewText(R.id.textViewWidgetDay2DayOfWeek, WeatherUtils.getDayEEE(WIP2));

        remoteViews.setImageViewResource(R.id.imageViewWidgetDay3WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP3.weatherCode)));
        remoteViews.setTextViewText(R.id.textViewWidgetDay3Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP3.tempC, toF));
        remoteViews.setTextViewText(R.id.textViewWidgetDay3DayOfWeek, WeatherUtils.getDayEEE(WIP3));

        remoteViews.setImageViewResource(R.id.imageViewWidgetDay4WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP4.weatherCode)));
        remoteViews.setTextViewText(R.id.textViewWidgetDay4Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP4.tempC, toF));
        remoteViews.setTextViewText(R.id.textViewWidgetDay4DayOfWeek, WeatherUtils.getDayEEE(WIP4));


        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private static class dataFetcherAsync extends AsyncTask<Void, Void, Integer> {
        final int DO_NOTHING = 0;
        final int RECEIVE_INFORMATION_SUCCESSFULLY = 1;
        RemoteViews remoteViews;
        int appWidgetId;
        AppWidgetManager appWidgetManager;
        Double lat = 15.11849;
        Double lon = 15.11849;
        DataFetcher dataFetcher;
        ArrayList<WeatherInformationPacket> forecastDayData, forecastHourData;
        WeatherInformationPacket WIP0, WIP1, WIP2, WIP3, WIP4;

        public dataFetcherAsync(RemoteViews rv, int appWidgetId, AppWidgetManager awm) {
            remoteViews = rv;
            this.appWidgetId = appWidgetId;
            appWidgetManager = awm;
            dataFetcher = new DataFetcher();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            if (ActivityMain.weatherSharedPref == null)
                return DO_NOTHING;
            lat = Double.valueOf(ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_LATITUDE, "12.3456"));
            lon = Double.valueOf(ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_LONGITUDE, "12.3456"));
            forecastDayData = dataFetcher.renderWeatherDataByDay(dataFetcher.getWeatherDataByDay(lat, lon));
            forecastHourData = dataFetcher.renderWeatherDataByHour(dataFetcher.getWeatherDataByHour(lat, lon));
            if (forecastDayData == null || forecastHourData == null || forecastDayData.size() <= 3 || forecastHourData.size() <= 3)
                return DO_NOTHING;
            return RECEIVE_INFORMATION_SUCCESSFULLY;
        }

        @Override
        protected void onPostExecute(Integer status) {
            if (status == DO_NOTHING) return;
            WIP0 = forecastHourData.get(forecastHourData.size() - 1);
            WIP1 = forecastDayData.get(1);
            WIP2 = forecastDayData.get(2);
            WIP3 = forecastDayData.get(3);
            WIP4 = forecastDayData.get(4);
            if (WIP0 == null || WIP1 == null || WIP2 == null || WIP3 == null || WIP4 == null) return;

            String placeName = ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_PLACE_NAME, "");

            boolean toF = ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, WeatherKeyWord.CELCIUS)
                    .equals(WeatherKeyWord.FARENHEIT);

            remoteViews.setTextViewText(R.id.textViewWidgetDay0Location, placeName);
            remoteViews.setImageViewResource(R.id.imageViewWidgetDay0WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP0.weatherCode)));
            remoteViews.setTextViewText(R.id.textViewWidgetDay0Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP0.tempC, toF));
            remoteViews.setTextViewText(R.id.textViewWidgetDay0DayOfWeek, WeatherUtils.getDayEEEE(WIP0));

            remoteViews.setImageViewResource(R.id.imageViewWidgetDay1WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP1.weatherCode)));
            remoteViews.setTextViewText(R.id.textViewWidgetDay1Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP1.tempC, toF));
            remoteViews.setTextViewText(R.id.textViewWidgetDay1DayOfWeek, WeatherUtils.getDayEEE(WIP1));

            remoteViews.setImageViewResource(R.id.imageViewWidgetDay2WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP2.weatherCode)));
            remoteViews.setTextViewText(R.id.textViewWidgetDay2Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP2.tempC, toF));
            remoteViews.setTextViewText(R.id.textViewWidgetDay2DayOfWeek, WeatherUtils.getDayEEE(WIP2));

            remoteViews.setImageViewResource(R.id.imageViewWidgetDay3WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP3.weatherCode)));
            remoteViews.setTextViewText(R.id.textViewWidgetDay3Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP3.tempC, toF));
            remoteViews.setTextViewText(R.id.textViewWidgetDay3DayOfWeek, WeatherUtils.getDayEEE(WIP3));

            remoteViews.setImageViewResource(R.id.imageViewWidgetDay4WeatherStatus, WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP4.weatherCode)));
            remoteViews.setTextViewText(R.id.textViewWidgetDay4Temperature, WeatherUtils.convertTemperatureWithoutConcat(WIP4.tempC, toF));
            remoteViews.setTextViewText(R.id.textViewWidgetDay4DayOfWeek, WeatherUtils.getDayEEE(WIP4));

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

}

