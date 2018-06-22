package com.ttcnpm.group28.weatherapp.namquan;

import java.io.Serializable;
import java.util.Date;

public class WeatherInformationPacket implements Serializable {
    public String areaName, country;
    public String Date, sunrise, sunset;
    public int moon_illumination;
    public int maxTempC, minTempC, uvIndex;

    public String time;
    public int tempC, windSpeedKmph;
    public String windDirection16Point, weatherCode, weatherIcon;
    public double precipMM;
    public int humidity, visibility, cloudCover;
    public int windGustKmph;
    public int chanceOfRain, chanceOfWindy, chanceOfOvercast, chanceOfSunshine, chanceOfFog
            , chanceOfThunder, chanceOfFrost;


    public WeatherInformationPacket(){
        areaName = "";
        country = "";
        Date = "";
        sunrise = "";
        sunset = "";
        moon_illumination = 0;
        maxTempC = 0;
        minTempC = 0;
        uvIndex = 0;
        time = "";
        tempC = 0;
        windSpeedKmph = 0;
        windDirection16Point = "";
        weatherCode = "";
        weatherIcon = "";
        precipMM = 0;
        humidity = 0;
        visibility = 0;
        cloudCover = 0;
        windGustKmph = 0;
        chanceOfFog = 0;
        chanceOfOvercast = 0;
        chanceOfRain = 0;
        chanceOfSunshine = 0;
        chanceOfThunder = 0;
        chanceOfWindy = 0;
        chanceOfFrost=0;

    }
    public WeatherInformationPacket(String areaName, String country,
                                    String date, String sunrise, String sunset, int moon_illumination,
                                    int maxTempC, int minTempC, int uvIndex, String time, int tempC, int windSpeedKmph,
                                    String windDirection16Point, String weatherCode, String weatherIcon,
                                    double precipMM, int humidity, int visibility, int cloudCover,
                                    int windGustKmph, int chanceOfFog, int chanceOfFrost, int chanceOfOvercast,
                                    int chanceOfRain, int chanceOfSunshine, int chanceOfThunder, int chanceOfWindy){
        this.areaName = areaName;
        this.country = country;
        this.Date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moon_illumination = moon_illumination;
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.uvIndex = uvIndex;
        this.time = time;
        this.tempC = tempC;
        this.windSpeedKmph = windSpeedKmph;
        this.windDirection16Point = windDirection16Point;
        this.weatherCode = weatherCode;
        this.weatherIcon = weatherIcon;
        this.precipMM = precipMM;
        this.humidity = humidity;
        this.visibility = visibility;
        this.cloudCover = cloudCover;
        this.windGustKmph = windGustKmph;
        this.chanceOfFog = chanceOfFog;
        this.chanceOfOvercast = chanceOfOvercast;
        this.chanceOfRain = chanceOfRain;
        this.chanceOfSunshine = chanceOfSunshine;
        this.chanceOfThunder = chanceOfThunder;
        this.chanceOfWindy = chanceOfWindy;
        this.chanceOfFrost=chanceOfFrost;
    }
}
