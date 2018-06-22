package com.ttcnpm.group28.weatherapp.dinhduc;

import android.app.Activity;
import android.util.Log;

import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.dinhduc.DatabaseManager;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    @Test
    public void testGetDaySystem_isCorrect() {
        DatabaseManager db=new DatabaseManager(new Activity());
        String temp=db.getDaySystem();
        int date=Integer.valueOf(temp.substring(6,8));
        assertTrue(date<32);
    }
    @Test
    public void testGetMonthSystem_isCorrect() {
        DatabaseManager db=new DatabaseManager(new Activity());
        String temp=db.getDaySystem();
        int date=Integer.valueOf(temp.substring(4,6));
        assertTrue(date<13);
    }
    @Test
    public void testGetYearSystem_isCorrect() {
        DatabaseManager db=new DatabaseManager(new Activity());
        String temp=db.getDaySystem();
        int date=Integer.valueOf(temp.substring(0,2));
        assertTrue(date<21);
    }
    @Test
    public void testGetTimeSystem_isCorrect() {
        DatabaseManager db=new DatabaseManager(new Activity());
        int time=db.getTimeSystem();
        assertTrue(time<25&&time>-1);
    }
}
