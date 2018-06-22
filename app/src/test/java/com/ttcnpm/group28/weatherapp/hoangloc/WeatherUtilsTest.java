package com.ttcnpm.group28.weatherapp.hoangloc;

import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;

import org.junit.Test;
import static org.junit.Assert.*;

public class WeatherUtilsTest {
    @Test
    public void testConvertTemperature_ToC_isCorrect() {
        String temp = WeatherUtils.convertTemperature(12,false );
        assertTrue(temp.equals("12°C"));
    }
    @Test
    public void testConvertTemperature_ToF_isCorrect() {
        String temp = WeatherUtils.convertTemperature(12,true);
        assertTrue(temp.equals("53.6°F"));
    }
    @Test
    public void testGetWarning_validWeatherCode() {
        int warningStringID = WeatherUtils.getWarningStringId(350);
        assertTrue(warningStringID == R.string.warning_ice_pellets || warningStringID ==R.string.warning_ice_pellets2);
    }

    @Test
    public void testConvertSpeed_ToKMH_isCorrect() {
        String speed = WeatherUtils.convertSpeed(12,false );
        assertTrue(speed.equals("12 km/h"));
    }
    @Test
    public void testConvertSpeed_ToMS_isCorrect() {
        String speed = WeatherUtils.convertSpeed(12,true);
        assertTrue(speed.equals("3.32 m/s"));
    }
    @Test
    public void getWeatherStatusResourceID_isCorrect() {
        int warningStringID = WeatherUtils.getWeatherStatusResourceID(179);
        assertTrue(warningStringID == R.drawable.weather_light_snow);
    }
    @Test
    public void getWeatherStatusBackgroundID_isCorrect() {
        String city = WeatherUtils.getWeatherStatusBackgroundID(113,WeatherKeyWord.THEME_CITY);
        String countryside = WeatherUtils.getWeatherStatusBackgroundID(260,WeatherKeyWord.THEME_COUNTRYSIDE);
        String forest = WeatherUtils.getWeatherStatusBackgroundID(365,WeatherKeyWord.THEME_FOREST);
        String highland = WeatherUtils.getWeatherStatusBackgroundID(119,WeatherKeyWord.THEME_HIGHLAND);
        assertTrue(city.equals("city_sunny")
                && countryside.equals("countryside_mist")
                && forest.equals("forest_snow")
                && highland.equals("highland_cloud"));
    }
    @Test
    public void getWeatherStatusBackgroundID_isIncorrect() {
        String city = WeatherUtils.getWeatherStatusBackgroundID(456,WeatherKeyWord.THEME_CITY);
        String countryside = WeatherUtils.getWeatherStatusBackgroundID(789,WeatherKeyWord.THEME_COUNTRYSIDE);
        String forest = WeatherUtils.getWeatherStatusBackgroundID(123,WeatherKeyWord.THEME_FOREST);
        String highland = WeatherUtils.getWeatherStatusBackgroundID(963,WeatherKeyWord.THEME_HIGHLAND);
        assertFalse(city.equals("highland_cloud")
                && countryside.equals("forest_snow")
                && forest.equals("countryside_mist")
                && highland.equals("city_sunny"));
    }
}