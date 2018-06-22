package com.ttcnpm.group28.weatherapp.huylinh;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.ttcnpm.group28.weatherapp.R;

import java.util.logging.SocketHandler;

import static android.content.Context.MODE_PRIVATE;

public class Setting {
    private static final String TAG = "SETTING";
    private static final String NULL_POINTER = "Null Pointer Exception";

    public static final String NAME = "weatherSharedPref";
    public static final String REMEMBER_LOCATION_KEY = "rememberLocation";
    public static final String QUANTITY_OF_DAYS_KEY = "currentQuantityOfDays";
    public static final String LANGUAGE_KEY = "currentLanguage";
    public static final String INTERVAL_KEY = "currentInterval";
    public static final String TEMPERATURE_TYPE_KEY = "currentTemperatureType";
    public static final String VELOCITY_KEY = "currentVelocity";
    public static final String THEME_KEY = "currentTheme";

    //Theme setting

    public class Themes{
        public static final int CITY = 0;
        public static final int COUNTRYSIDE = 1;
        public static final int FOREST = 2;
        public static final int HIGHLAND = 3;
    }
    public static int getThemeSetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getInt(THEME_KEY, Themes.CITY);
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return Themes.CITY;
        }
    }
    public static void setTheme(SharedPreferences sp, int value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(THEME_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }

    //Reset setting
    public static void resetSetting(SharedPreferences sp){
        if(sp!=null){
            setInterval(sp, 15);
            setQuantityOfDays(sp, 7);
            setTemperatureType(sp, TemperatureType.CELCIUS);
            setVelocity(sp, VelocityUnit.METERS_PER_SECOND);
            setRememberLocation(sp, false);
            setLanguage(sp,Language.VIETNAMESE);
            setTheme(sp, Themes.CITY);
        }
        else{
            Log.e(TAG,NULL_POINTER);
        }
    }
    //Language setting

    public static void setLanguage(SharedPreferences sp, String value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(LANGUAGE_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }
    public static String getLanguageSetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getString(LANGUAGE_KEY, "vi");
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return null;
        }
    }

    public class Language{
        public static final String VIETNAMESE = "vi";
        public static final String ENGLISH = "en";
    }

    //Location Setting

    public static boolean getRememberLocationSetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getBoolean(REMEMBER_LOCATION_KEY, false);
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return false;
        }
    }
    public static void setRememberLocation(SharedPreferences sp, boolean value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(REMEMBER_LOCATION_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }

    //Quantity Of Days Setting

    public static void setQuantityOfDays(SharedPreferences sp, int value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(QUANTITY_OF_DAYS_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }
    public static int getQuantityOfDaysSetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getInt(QUANTITY_OF_DAYS_KEY, 7);
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return 7;
        }
    }

    //Interval setting

    public static void setInterval(SharedPreferences sp, int value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(INTERVAL_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }
    public static int getIntervalSetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getInt(INTERVAL_KEY, 15);
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return 15;
        }
    }

    //Temperature setting

    public class TemperatureType{
        public static final String CELCIUS = "C";
        public static final String FAHRENHEIT = "F";
    }
    public static void setTemperatureType(SharedPreferences sp, String value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(TEMPERATURE_TYPE_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }
    public static String getTemperatureTypeSetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getString(TEMPERATURE_TYPE_KEY, TemperatureType.CELCIUS);
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return TemperatureType.CELCIUS;
        }
    }

    //Velocity Setting

    public class VelocityUnit{
        public static final String METERS_PER_SECOND = "ms";
        public static final String KILOMETERS_PER_SECOND = "kms";
    }
    public static void setVelocity(SharedPreferences sp, String value){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(VELOCITY_KEY, value).commit();
        }
        else {
            Log.e(TAG,NULL_POINTER);
        }
    }
    public static String getVelocitySetting(SharedPreferences sp){
        if(sp!=null){
            return sp.getString(VELOCITY_KEY, VelocityUnit.METERS_PER_SECOND);
        }
        else {
            Log.e(TAG,NULL_POINTER);
            return VelocityUnit.METERS_PER_SECOND;
        }
    }
}
