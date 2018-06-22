package com.ttcnpm.group28.weatherapp.hoangloc;

import android.content.res.Resources;
import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.namquan.WeatherInformationPacket;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class WeatherUtils {
    public static Resources weatherResources;

    public static int getWarningStringId(int weatherCode){
        int rand = Math.abs(new Random().nextInt())%2;
        if (rand == 0) {
            switch (weatherCode) {
                case 113: return R.string.warning_sunny;
                case 116: return R.string.warning_partly_cloud;
                case 119: return R.string.warning_partly_cloud;
                case 122: return R.string.warning_overcast;
                case 200: return R.string.warning_thunder;
                case 248: case 260: case 143:
                    return R.string.warning_mist;
                case 179: case 182: case 311: case 314:
                    return (R.string.warning_snow);
                case 185: case 176: case 263: case 266: case 281:
                case 284: case 293: case 296: case 299: case 302:
                case 305: case 308: case 353: case 356: case 359:
                    return R.string.warning_rain;
                case 227: case 230: case 317: case 320: case 323:
                case 326: case 329: case 332: case 335: case 338:
                case 362: case 365: case 368: case 371:
                    return R.string.warning_light_snow;
                case 350: case 374: case 377:
                    return R.string.warning_ice_pellets;
                case 386: case 389: case 392: case 395:
                    return R.string.warning_rain_thunder ;
                default: return R.string.warning_sunny;
            }
        } else {
            switch (weatherCode) {
                case 113: return R.string.warning_sunny2;
                case 116: return R.string.warning_partly_cloud2;
                case 119: return R.string.warning_partly_cloud2;
                case 122: return R.string.warning_overcast2;
                case 200: return R.string.warning_thunder2;
                case 248: case 260: case 143:
                    return R.string.warning_mist2;
                case 179: case 182: case 311: case 314:
                    return (R.string.warning_snow2);
                case 185: case 176: case 263: case 266: case 281:
                case 284: case 293: case 296: case 299: case 302:
                case 305: case 308: case 353: case 356: case 359:
                    return R.string.warning_rain2;
                case 227: case 230: case 317: case 320: case 323:
                case 326: case 329: case 332: case 335: case 338:
                case 362: case 365: case 368: case 371:
                    return R.string.warning_light_snow2;
                case 350: case 374: case 377:
                    return R.string.warning_ice_pellets2;
                case 386: case 389: case 392: case 395:
                    return R.string.warning_rain_thunder2;
                default: return R.string.warning_sunny2;
            }
        }
    }

    public static String convertTemperature(int tempC, boolean toF){
        if (toF == true){
            String tempF = String.valueOf(tempC*1.8 + 32);
            if (tempF.length()> 4) tempF=tempF.substring(0,5);
            return  String.valueOf(tempF) + "°F";
        }
        else return String.valueOf(tempC) +  "°C";
    }

    public static String convertTemperatureWithoutConcat(int tempC, boolean toF){
        if (toF == true){
            String tempF = String.valueOf(tempC*1.8 + 32);
            if (tempF.length()> 4) tempF=tempF.substring(0,5);
            return  String.valueOf(tempF) + "°";
        }
        else return String.valueOf(tempC) + "°";
    }

    public static String convertSpeed(int speedKmh, boolean toMS){
        if (toMS == true){
            String s = String.valueOf(0.277f*speedKmh);
            if (s.length() > 4) s = s.substring(0,4);
            return  s + " m/s";
        }
        else return String.valueOf(speedKmh) + " km/h";
    }

    public static int getWeatherStatusResourceID(int weatherCode){
        switch (weatherCode) {
            case 113: return R.drawable.weather_sunny;
            case 116: return R.drawable.weather_partly_cloud;
            case 119: return R.drawable.weather_partly_cloud;
            case 122: return R.drawable.weather_overcast;
            case 200: return R.drawable.weather_thunder;

            case 248: case 260: case 143:
                return R.drawable.weather_mist;

            case 179: case 182: case 311: case 314:
                return R.drawable.weather_light_snow;

            case 185: case 176: case 263: case 266: case 281:
            case 284: case 293: case 296: case 299: case 302:
            case 305: case 308: case 353: case 356: case 359:
                return R.drawable.weather_rain;

            case 227: case 230: case 317: case 320: case 323:
            case 326: case 329: case 332: case 335: case 338:
            case 362: case 365: case 368: case 371:
                return R.drawable.weather_snow;

            case 350: case 374: case 377:
                return R.drawable.weather_ice_pellets;

            case 386: case 389: case 392: case 395:
                return R.drawable.weather_rain_thunder;

            default: return R.drawable.weather_overcast;
        }
    }

    public static String getWeatherStatusBackgroundID(int weatherCode, int themeID){
        String currentBackground;
        switch (themeID){
            case WeatherKeyWord.THEME_CITY:
                currentBackground = "city"; break;
            case WeatherKeyWord.THEME_COUNTRYSIDE:
                currentBackground = "countryside"; break;
            case WeatherKeyWord.THEME_FOREST:
                currentBackground = "forest"; break;
            case WeatherKeyWord.THEME_HIGHLAND:
                currentBackground = "highland"; break;
            default: currentBackground = "highland";
        }
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (currentHour > 16 || currentHour < 6) return currentBackground + "_night";

        switch (weatherCode) {
            case 113: currentBackground = currentBackground +"_sunny"; break;
            case 116: currentBackground = currentBackground + "_cloud"; break;
            case 119: currentBackground = currentBackground + "_cloud"; break;
            case 122: currentBackground = currentBackground + "_overcast"; break;
            case 200: currentBackground = currentBackground + "_rain_thunder"; break; //thunder

            case 248: case 260: case 143:
                currentBackground = currentBackground + "_mist";
                break;
            case 179: case 182: case 311: case 314:
                currentBackground = currentBackground + "_snow";
                break;
            case 185: case 176: case 263: case 266: case 281:
            case 284: case 293: case 296: case 299: case 302:
            case 305: case 308: case 353: case 356: case 359:
                currentBackground = currentBackground + "_rain";
                break;
            case 227: case 230: case 317: case 320: case 323:
            case 326: case 329: case 332: case 335: case 338:
            case 362: case 365: case 368: case 371:
                currentBackground = currentBackground + "_snow";
                break;
            case 350: case 374: case 377:
                currentBackground = currentBackground + "_snow"; //ice_pellets
                break;
            case 386: case 389: case 392: case 395:
                currentBackground = currentBackground + "_rain_thunder";
                break;
            default: currentBackground = currentBackground + "_overcast";
        }
        return currentBackground;
    }

    public static String getDayEEE(WeatherInformationPacket WIP){
        Date date = new Date();
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = inFormat.parse(WIP.Date.substring(8, 10) + "-" + WIP.Date.substring(5, 7) + "-" + WIP.Date.substring(0, 4));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
        String day = outFormat.format(date);
        switch (day){
            case "Mon": return weatherResources.getString(R.string.mon);
            case "Tue": return weatherResources.getString(R.string.tue);
            case "Wed": return weatherResources.getString(R.string.wed);
            case "Thu": return weatherResources.getString(R.string.thu);
            case "Fri": return weatherResources.getString(R.string.fri);
            case "Sat": return weatherResources.getString(R.string.sat);
            case "Sun": return weatherResources.getString(R.string.sun);
            default: return "";
        }
    }

    public static String getDayEEEE(WeatherInformationPacket WIP){
        Date date = new Date();
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = inFormat.parse(WIP.Date.substring(8, 10) + "-" + WIP.Date.substring(5, 7) + "-" + WIP.Date.substring(0, 4));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
        String day = outFormat.format(date);
        switch (day){
            case "Mon": return weatherResources.getString(R.string.monday);
            case "Tue": return weatherResources.getString(R.string.tuesday);
            case "Wed": return weatherResources.getString(R.string.wednesday);
            case "Thu": return weatherResources.getString(R.string.thursday);
            case "Fri": return weatherResources.getString(R.string.friday);
            case "Sat": return weatherResources.getString(R.string.saturday);
            case "Sun": return weatherResources.getString(R.string.sunday);
            default: return "";
        }
    }

}
