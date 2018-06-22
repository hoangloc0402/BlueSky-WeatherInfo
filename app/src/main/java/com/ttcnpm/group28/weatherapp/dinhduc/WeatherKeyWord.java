package com.ttcnpm.group28.weatherapp.dinhduc;

public class WeatherKeyWord {

    public static final String SHARED_PREF_NAME = "weatherSharedPref";

    public static final String CURRENT_LATITUDE = "currentLat";
    public static final String CURRENT_LONGITUDE = "currentLon";
    public static final String CURRENT_PLACE_NAME = "currentPlaceName";
    public static final String CURRENT_PLACE_NAME_GET_FROM_SERVER = "currentPlaceNameGetFromServer";
    public static final String CURRENT_INTERVAL = "currentInterval";
    public static final String CURRENT_QUANTITY_OF_DAY = "currentQuantityOfDays";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String RESTART_FROM_SETTING = "restartFromSetting";
    public static final String REMEMBER_MY_LOCATION = "rememberLocation";

    public static final String CURRENT_LANGUAGE = "currentLanguage";
    public static final String ENGLISH = "en";
    public static final String VIETNAMESE = "vi";

    public static final String CURRENT_TEMPERATURE_TYPE = "currentTemperatureType";
    public static final String CELCIUS = "C";
    public static final String FARENHEIT = "F";

    public static final String CURRENT_VELOCITY = "currentVelocity";
    public static final String METER_PER_SECOND = "ms";
    public static final String KILOMETER_PER_HOUR = "kmh";

    public static final String CURRENT_THEME = "currentTheme";
    public static final int THEME_CITY = 0;
    public static final int THEME_COUNTRYSIDE = 1;
    public static final int THEME_FOREST = 2;
    public static final int THEME_HIGHLAND = 3;

    public static final int MSG_RESTART = -2;
    public static final int MSG_INTERNET_CONNECTION_FAIL = -1;
    public static final int MSG_RECEIVE_HOUR_DATA = 0;
    public static final int MSG_RECEIVE_DAY_DATA = 1;
    public static final int MSG_RECEIVE_HOUR_DATA_DBS = 2;
    public static final int MSG_RECEIVE_DAY_DATA_DBS = 3;
}