package com.ttcnpm.group28.weatherapp.namquan;

import android.os.Message;
import android.util.Log;

import com.ttcnpm.group28.weatherapp.hoangloc.FragmentWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DataFetcher extends Thread {
    private String jsonString = "";
    private double longitude = 0.0;
    private double latitude = 0.0;

    private JSONObject json = new JSONObject();

    public JSONObject getWeatherDataByDay(Double latitude, Double longitude) {
        final String urlString =
                "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=9367f9c38aed4f19a2160105181406&q="
                        + latitude.toString() + "," + longitude.toString() + "&format=json&num_of_days=14&tp=24&includelocation=yes";
        try {
            return getWeatherData(urlString);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public JSONObject getWeatherDataByHour(Double latitude, Double longitude) {
        final String urlString =
                "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=9367f9c38aed4f19a2160105181406&q="
                        + latitude.toString() + "," + longitude.toString() + "&format=json&num_of_days=2&tp=1&includelocation=yes";
        try {
            return getWeatherData(urlString);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    private JSONObject getWeatherData(final String urlString) throws JSONException {
        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(2000);
            urlConnection.setReadTimeout(4000);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                jsonString = stringBuilder.toString();
                //Log.d("JSON String: ",jsonString);
                json = new JSONObject(jsonString);
                Log.d("Length: ", Integer.toString(jsonString.length()));
                bufferedReader.close();
                //return stringBuilder.toString();
            } catch (Exception e) {
                json = new JSONObject();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            return new JSONObject();
        }

        return json;
    }

    public ArrayList<WeatherInformationPacket> renderWeatherData(JSONObject json) {
        ArrayList<WeatherInformationPacket> listOfWeatherInfomationPacket = new ArrayList<WeatherInformationPacket>();
        ArrayList<WeatherInformationPacket> information = new ArrayList<WeatherInformationPacket>();

        try {
            JSONObject data = json.getJSONObject("data");
            JSONArray weather = data.getJSONArray("weather");
            Integer n_days = weather.length();
            for (int i = 0; i < n_days; i++) {
                information = getData(i, json);
                listOfWeatherInfomationPacket.addAll(information);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listOfWeatherInfomationPacket;
    }

    public ArrayList<WeatherInformationPacket> renderWeatherDataByHour(JSONObject json) {
        ArrayList<WeatherInformationPacket> listOfWeatherInfomationPacket = renderWeatherData(json);
        for (int i = 0; i < listOfWeatherInfomationPacket.size(); i++) {
            listOfWeatherInfomationPacket.get(i).time = String.format("%02d", i % 24) + ":00";
            // Log.d("Thoi gian theo gio: ",listOfWeatherInfomationPacket.get(i).time);
            //Log.d("Thoi gian hien tai: ", listOfWeatherInfomationPacket.get(i).time);

        }
        WeatherInformationPacket currentCondition = getCurrentCondition(json);

        listOfWeatherInfomationPacket.add(currentCondition);


        return listOfWeatherInfomationPacket;
    }

    public ArrayList<WeatherInformationPacket> renderWeatherDataByDay(JSONObject json) {
        ArrayList<WeatherInformationPacket> listOfWeatherInfomationPacket = renderWeatherData(json);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date next_date;
        Calendar c = Calendar.getInstance();
        String time = "";
        try {
            //Log.d("Thoi gian dau tien: ", listOfWeatherInfomationPacket.get(0).Date);
            //Log.d("SUnset: ",listOfWeatherInfomationPacket.get(0).sunset);
            next_date = formatter.parse(listOfWeatherInfomationPacket.get(0).Date);
            for (int i = 0; i < listOfWeatherInfomationPacket.size(); i++) {

                listOfWeatherInfomationPacket.get(i).time = next_date.toString();
                //Log.d("Thoi gian: ", next_date.toString());
                c.setTime(next_date);
                c.add(Calendar.DATE, 1);
                time = formatter.format(c.getTime());
                next_date = formatter.parse(time);
                //Log.d("Thoi gian: ", formatter.format(c.getTime()));
                //Log.d("Thoi gian theo ngay: ",listOfWeatherInfomationPacket.get(i).time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listOfWeatherInfomationPacket;
    }

    private ArrayList<WeatherInformationPacket> getData(int i, JSONObject json) {
        WeatherInformationPacket hour = new WeatherInformationPacket();
        ArrayList<WeatherInformationPacket> list = new ArrayList<>();
        try {

            JSONObject data = json.getJSONObject("data");

            JSONArray nearest_area = data.getJSONArray("nearest_area");
            hour.areaName = nearest_area.getJSONObject(0).getJSONArray("areaName").
                    getJSONObject(0).getString("value");
            hour.country = nearest_area.getJSONObject(0).getJSONArray("country").
                    getJSONObject(0).getString("value");

            JSONArray weather = data.getJSONArray("weather");
            JSONObject day = weather.getJSONObject(i);

            hour.Date = day.getString("date");
            hour.maxTempC = Integer.valueOf(day.getString("maxtempC"));
            //hour.maxTempF = Integer.valueOf(day.getString("maxtempF"));
            hour.minTempC = Integer.valueOf(day.getString("mintempC"));
            //hour.minTempF = Integer.valueOf(day.getString("mintempF"));
            hour.uvIndex = Integer.valueOf(day.getString("uvIndex"));

            JSONArray astronomy = day.getJSONArray("astronomy");
            hour.sunrise = astronomy.getJSONObject(0).getString("sunrise");
            hour.sunset = astronomy.getJSONObject(0).getString("sunset");
            hour.moon_illumination = Integer.valueOf(astronomy.getJSONObject(0).getString("moon_illumination"));

            JSONArray hourly = day.getJSONArray("hourly");
            JSONObject everyHour;
            int n_hours = hourly.length();
            for (int j = 0; j < n_hours; j++) {
                everyHour = hourly.getJSONObject(j);
                hour.time = String.format("%02d", j % 24) + ":00";//everyHour.getString("time");
                hour.tempC = Integer.valueOf(everyHour.getString("tempC"));
                //hour.tempF = Integer.valueOf(everyHour.getString("tempF"));
                //hour.windSpeedMiles = Integer.valueOf(everyHour.getString("windspeedMiles"));
                hour.windSpeedKmph = Integer.valueOf(everyHour.getString("windspeedKmph"));
                hour.windDirection16Point = everyHour.getString("winddir16Point");
                hour.weatherCode = everyHour.getString("weatherCode");


                hour.weatherIcon = everyHour.getJSONArray("weatherIconUrl").
                        getJSONObject(0).getString("value");
                hour.precipMM = Double.valueOf(everyHour.getString("precipMM"));
                hour.humidity = Integer.valueOf(everyHour.getString("humidity"));
                hour.visibility = Integer.valueOf(everyHour.getString("visibility"));
                hour.cloudCover = Integer.valueOf(everyHour.getString("cloudcover"));
                //hour.windGustMiles = Integer.valueOf(everyHour.getString("WindGustMiles"));
                hour.windGustKmph = Integer.valueOf(everyHour.getString("WindGustKmph"));
                hour.chanceOfWindy = Integer.valueOf(everyHour.getString("chanceofwindy"));
                hour.chanceOfThunder = Integer.valueOf(everyHour.getString("chanceofthunder"));
                hour.chanceOfSunshine = Integer.valueOf(everyHour.getString("chanceofsunshine"));
                hour.chanceOfRain = Integer.valueOf(everyHour.getString("chanceofrain"));
                hour.chanceOfOvercast = Integer.valueOf(everyHour.getString("chanceofovercast"));
                hour.chanceOfFog = Integer.valueOf(everyHour.getString("chanceoffog"));
                hour.chanceOfFrost = Integer.valueOf(everyHour.getString("chanceoffrost"));

                WeatherInformationPacket t = new WeatherInformationPacket();
                t.Date = hour.Date;
                t.country = hour.country;
                t.areaName = hour.areaName;
                t.chanceOfFrost = hour.chanceOfFrost;
                t.cloudCover = hour.cloudCover;
                t.tempC = hour.tempC;
                t.time = hour.time;
                t.visibility = hour.visibility;
                t.humidity = hour.humidity;
                t.precipMM = hour.precipMM;
                t.windDirection16Point = hour.windDirection16Point;
                t.windSpeedKmph = hour.windSpeedKmph;
                t.weatherIcon = hour.weatherIcon;
                t.weatherCode = hour.weatherCode;
                t.chanceOfFog = hour.chanceOfFog;
                t.chanceOfRain = hour.chanceOfRain;
                t.chanceOfSunshine = hour.chanceOfSunshine;
                t.chanceOfThunder = hour.chanceOfThunder;
                t.chanceOfWindy = hour.chanceOfWindy;
                t.maxTempC = hour.maxTempC;
                t.minTempC = hour.minTempC;
                t.moon_illumination = hour.moon_illumination;
                t.sunrise = hour.sunrise;
                t.sunset = hour.sunset;
                t.uvIndex = hour.uvIndex;
                t.windGustKmph = hour.windGustKmph;
                t.chanceOfOvercast = hour.chanceOfOvercast;

                list.add(t);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private WeatherInformationPacket getCurrentCondition(JSONObject json) {
        WeatherInformationPacket current = new WeatherInformationPacket();
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dataParse = new SimpleDateFormat("h:mm aaa", Locale.ENGLISH);

        try {
            JSONObject data = json.getJSONObject("data");
            JSONArray nearest_area = data.getJSONArray("nearest_area");
            current.areaName = nearest_area.getJSONObject(0).getJSONArray("areaName").
                    getJSONObject(0).getString("value");
            current.country = nearest_area.getJSONObject(0).getJSONArray("country").
                    getJSONObject(0).getString("value");
            JSONObject current_condition = data.getJSONArray("current_condition").getJSONObject(0);

	    JSONObject weather = data.getJSONArray("weather").getJSONObject(0);

            current.Date = weather.getString("date");



            try {
                current.time = formater.format(dataParse.parse(current_condition.getString("observation_time")));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            current.tempC = Integer.valueOf(current_condition.getString("temp_C"));
            current.weatherCode = current_condition.getString("weatherCode");
            current.weatherIcon = current_condition.getJSONArray("weatherIconUrl").
                    getJSONObject(0).getString("value");
            current.windSpeedKmph = Integer.valueOf(current_condition.getString("windspeedKmph"));
            current.windDirection16Point = current_condition.getString("winddir16Point");
            current.precipMM = Double.valueOf(current_condition.getString("precipMM"));
            current.humidity = Integer.valueOf(current_condition.getString("humidity"));
            current.visibility = Integer.valueOf(current_condition.getString("visibility"));
            current.cloudCover = Integer.valueOf(current_condition.getString("cloudcover"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return current;
    }

    @Override
    public void run() {
        JSONObject hourData = getWeatherDataByHour(this.latitude, this.longitude);
        ArrayList<WeatherInformationPacket> forecastHourData = renderWeatherDataByHour(hourData);

        if (forecastHourData.size() <= 3) {
            Message errorMessage = FragmentWeather.mHandler.obtainMessage(-1, "");
            errorMessage.sendToTarget();
            return;
        }

        Message completeMessageHour = FragmentWeather.mHandler.obtainMessage(0, forecastHourData);
        completeMessageHour.sendToTarget();

        JSONObject dayData = getWeatherDataByDay(this.latitude, this.longitude);
        ArrayList<WeatherInformationPacket> forecastDayData = renderWeatherDataByDay(dayData);
        if (forecastDayData.size() <= 3) {
            Message errorMessage = FragmentWeather.mHandler.obtainMessage(-1, "");
            errorMessage.sendToTarget();
            return;
        }

        Message completeMessageDay = FragmentWeather.mHandler.obtainMessage(1, forecastDayData);
        completeMessageDay.sendToTarget();
    }

    public void setCoord(Double lat, Double lon) {
        this.longitude = lon;
        this.latitude = lat;
    }
}
