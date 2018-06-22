package com.ttcnpm.group28.weatherapp.dinhduc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ttcnpm.group28.weatherapp.namquan.WeatherInformationPacket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Weather_Infor.db";
    public static final String TABLE_WEATHER_INFO_HOUR="WeatherInformationForHour";
    public static final String TABLE_WEATHER_INFO_DAY="WeatherInformationForDay";
    public static final String COL_ID="ID";
    public static final String COL_DATE="Date";
    public static final String COL_TIME="Time";
    public static final String COL_SUNRISE="SunriseTime";
    public static final String COL_SUNSET="SunsetTime";
    public static final String COL_MOON="MoonIllumination";
    public static final String COL_UVINDEX="UVIndex";
    public static final String COL_WEATHER_CODE="WeatherCode";
    public static final String COL_WEATHER_ICON="WeatherIcon";
    public static final String COL_PRECIPMM="PrecipMM";
    public static final String COL_WIND_GUST="WindGust";
    public static final String COL_CHANCE_RAIN="ChanceOfRain";
    public static final String COL_CHANCE_WINDY="ChanceOfWindy";
    public static final String COL_CHANCE_OVERCAST="ChanceOfOvercast";
    public static final String COL_CHANCE_SUNSHINE="ChanceOfSunshine";
    public static final String COL_CHANCE_FOG="ChanceOfFog";
    public static final String COL_CHANCE_THUNDER="ChanceOfThunder";
    public static final String COL_CHANCE_FROST="ChanceOfFrost";
    public static final String COL_CLOUDINESS="Cloudiness";
    public static final String COL_WIND_DIRECT="WindDirection";
    public static final String COL_WIND_SPEED="WindSpeed";
    public static final String COL_HUMIDILITY="Humidility";
    public static final String COL_MINTEMP="MinimumTemp";
    public static final String COL_MAXTEMP="MaximumTemp";
    public static final String COL_CURRTEMP="CurrentTemp";
    public static final String COL_VISIBILITY="Visibility";
    public static final String COL_AREA="Area";
    public static final String COL_COUNTRY="Country";
    private Context context;
    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlForHour="CREATE TABLE "+TABLE_WEATHER_INFO_HOUR+" ("
                +COL_ID+" INTEGER PRIMARY KEY, "
                +COL_DATE+" TEXT, "
                +COL_TIME+" TEXT, "
                +COL_SUNRISE+" TEXT, "
                +COL_SUNSET+" TEXT, "
                +COL_MOON+" INTEGER, "
                +COL_MAXTEMP+" INTEGER, "
                +COL_MINTEMP+" INTEGER, "
                +COL_UVINDEX+" INTEGER, "
                +COL_CURRTEMP+" INTEGER, "
                +COL_WIND_SPEED+" INTEGER, "
                +COL_WIND_DIRECT+" TEXT, "
                +COL_WEATHER_CODE+" TEXT, "
                +COL_WEATHER_ICON+" TEXT, "
                +COL_PRECIPMM+" REAL, "
                +COL_HUMIDILITY+" INTEGER, "
                +COL_VISIBILITY+" INTEGER, "
                +COL_CLOUDINESS+" INTEGER, "
                +COL_WIND_GUST+" INTEGER, "
                +COL_CHANCE_RAIN+" INTEGER, "
                +COL_CHANCE_WINDY+" INTEGER, "
                +COL_CHANCE_OVERCAST+" INTEGER, "
                +COL_CHANCE_SUNSHINE+" INTEGER, "
                +COL_CHANCE_THUNDER+" INTEGER, "
                +COL_CHANCE_FOG+" INTEGER, "
                +COL_CHANCE_FROST+" INTEGER, "
                +COL_AREA+" TEXT, "
                +COL_COUNTRY+" TEXT "
                +" )";
        db.execSQL(sqlForHour);
        String sqlForDay="CREATE TABLE "+TABLE_WEATHER_INFO_DAY+" ("
                +COL_ID+" INTEGER PRIMARY KEY, "
                +COL_DATE+" TEXT, "
                +COL_TIME+" TEXT, "
                +COL_SUNRISE+" TEXT, "
                +COL_SUNSET+" TEXT, "
                +COL_MOON+" INTEGER, "
                +COL_MAXTEMP+" INTEGER, "
                +COL_MINTEMP+" INTEGER, "
                +COL_UVINDEX+" INTEGER, "
                +COL_CURRTEMP+" INTEGER, "
                +COL_WIND_SPEED+" INTEGER, "
                +COL_WIND_DIRECT+" TEXT, "
                +COL_WEATHER_CODE+" TEXT, "
                +COL_WEATHER_ICON+" TEXT, "
                +COL_PRECIPMM+" REAL, "
                +COL_HUMIDILITY+" INTEGER, "
                +COL_VISIBILITY+" INTEGER, "
                +COL_CLOUDINESS+" INTEGER, "
                +COL_WIND_GUST+" INTEGER, "
                +COL_CHANCE_RAIN+" INTEGER, "
                +COL_CHANCE_WINDY+" INTEGER, "
                +COL_CHANCE_OVERCAST+" INTEGER, "
                +COL_CHANCE_SUNSHINE+" INTEGER, "
                +COL_CHANCE_THUNDER+" INTEGER, "
                +COL_CHANCE_FOG+" INTEGER, "
                +COL_CHANCE_FROST+" INTEGER, "
                +COL_AREA+" TEXT, "
                +COL_COUNTRY+" TEXT "
                +" )";
        db.execSQL(sqlForDay);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_WEATHER_INFO_HOUR);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_WEATHER_INFO_DAY);
        onCreate(db);
    }
    public void addInformation(ArrayList<WeatherInformationPacket> list, String tableName) {
        Log.d("abcdefghiklmn","----------------------------------------------");
        deleteTable(tableName);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i < list.size(); i++){

            //values.put(COL_ID, i+1);
            WeatherInformationPacket index=list.get(i);
            values.put(COL_DATE,index.Date);
            values.put(COL_TIME,index.time );
            values.put(COL_SUNRISE, index.sunrise);
            values.put(COL_SUNSET, index.sunset);
            values.put(COL_MOON, index.moon_illumination);
            values.put(COL_WEATHER_CODE, index.weatherCode);
            values.put(COL_WEATHER_ICON, index.weatherIcon);
            values.put(COL_PRECIPMM, index.precipMM);
            values.put(COL_WIND_GUST, index.windGustKmph);
            values.put(COL_CLOUDINESS, index.cloudCover);
            values.put(COL_WIND_DIRECT, index.windDirection16Point);
            values.put(COL_WIND_SPEED, index.windSpeedKmph);
            values.put(COL_HUMIDILITY, index.humidity);
            values.put(COL_MINTEMP,index.minTempC);
            values.put(COL_MAXTEMP, index.maxTempC);
            values.put(COL_CURRTEMP, index.tempC);
            values.put(COL_CHANCE_FOG,index .chanceOfFog);
            values.put(COL_CHANCE_FROST, index.chanceOfFrost);
            values.put(COL_CHANCE_OVERCAST,index.chanceOfOvercast);
            values.put(COL_CHANCE_RAIN,index .chanceOfRain);
            values.put(COL_CHANCE_SUNSHINE,index.chanceOfSunshine);
            values.put(COL_CHANCE_THUNDER, index.chanceOfThunder);
            values.put(COL_CHANCE_WINDY, index.chanceOfWindy);
            values.put(COL_UVINDEX, index.uvIndex);
            values.put(COL_VISIBILITY, index.visibility);
            values.put(COL_AREA, index.areaName);
            values.put(COL_COUNTRY, index.country);

            db.insert(tableName, null, values);
        }
        db.close();
    }
    public WeatherInformationPacket getInformation(String tableName){

        String num=String.valueOf(getNumberRaw(tableName));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WEATHER_INFO_HOUR, new String[] {
                COL_DATE, COL_TIME,COL_SUNRISE,COL_SUNSET,COL_MOON,COL_MAXTEMP,COL_MINTEMP,COL_UVINDEX,COL_CURRTEMP,
                COL_WIND_SPEED,COL_WIND_DIRECT,COL_WEATHER_CODE,COL_WEATHER_ICON,COL_PRECIPMM,COL_HUMIDILITY,
                COL_VISIBILITY,COL_CLOUDINESS,COL_WIND_GUST,COL_CHANCE_RAIN,COL_CHANCE_WINDY,COL_CHANCE_OVERCAST,
                COL_CHANCE_SUNSHINE,COL_CHANCE_THUNDER,COL_CHANCE_FOG,COL_CHANCE_FROST,COL_AREA,COL_COUNTRY
        }, COL_ID + "="+num, null, null, null, null);
        //if (cursor != null)
        //    cursor.moveToFirst();
        String date,sunrise,sunset,windDir,WeaCode,WeaIcon,area,country,time;
        int moon,max,min,uv,curr,wSpeed,hum,vis,clou,wGus,cRain,cWind,cOverC,cSun,cThund,cFog,cFrost;
        double pre;
        date=cursor.getString(1);
        time=cursor.getString(2);
        sunrise=cursor.getString(3);
        sunset=cursor.getString(4);
        moon=cursor.getInt(5);
        max=cursor.getInt(6);
        min=cursor.getInt(7);
        uv=cursor.getInt(8);
        curr=cursor.getInt(9);
        wSpeed=cursor.getInt(10);
        windDir=cursor.getString(11);
        WeaCode=cursor.getString(12);
        WeaIcon=cursor.getString(13);
        pre=cursor.getDouble(14);
        hum=cursor.getInt(15);
        vis=cursor.getInt(16);
        clou=cursor.getInt(17);
        wGus=cursor.getInt(18);
        cRain=cursor.getInt(19);
        cWind=cursor.getInt(20);
        cOverC=cursor.getInt(21);
        cSun=cursor.getInt(22);
        cThund=cursor.getInt(23);
        cFog=cursor.getInt(24);
        cFrost=cursor.getInt(25);
        area=cursor.getString(26);
        country=cursor.getString(27);

        WeatherInformationPacket ret=new WeatherInformationPacket(area,country,date,sunrise,sunset,moon,max,min,uv, time,curr,wSpeed,windDir,WeaCode,WeaIcon,pre,hum,vis,clou,wGus,cFog,cFrost,cOverC,cRain,cSun,cThund,cWind);


        cursor.close();
        db.close();
        return ret;
    }
    public int getNumberRaw(String tableName) {
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }
    public String getTableWeatherInfoHour(){
        return TABLE_WEATHER_INFO_HOUR;
    }
    public String getTableWeatherInfoDay(){
        return TABLE_WEATHER_INFO_DAY;
    }

    public String getDaySystem(){
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String ret=String.valueOf(date.format(calendar.getTime()));
        return ret;
    }
    public int getTimeSystem(){
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("hh");
        String ret=String.valueOf(time.format(calendar.getTime()));
        int intTime = 0;

        try
        {
            intTime = Integer.parseInt(ret);
        }
        catch(NumberFormatException e) {

        }
        return intTime;
    }
    public ArrayList<WeatherInformationPacket> getInformationUseful(String tableName){
        ArrayList<WeatherInformationPacket> list=new ArrayList<WeatherInformationPacket>();
        String sql="SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery(sql,new String[]{});
        int id=0;
        int sysDate=Integer.valueOf(getDaySystem());
        if(cursor.moveToFirst()){
            for(int j=0;j<cursor.getCount();j++)
            {

                String date,sunrise,sunset,windDir,WeaCode,WeaIcon,area,country,time;
                int moon,max,min,uv,curr,wSpeed,hum,vis,clou,wGus,cRain,cWind,cOverC,cSun,cThund,cFog,cFrost;
                double pre;
                date=cursor.getString(1);
                time=cursor.getString(2);
                String tempDay=date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
                int appDay=Integer.valueOf(tempDay);
                //Log.d("system Day: ",String.valueOf(sysDate));
                //Log.d("app day: ",String.valueOf(appDay));
                if(sysDate>appDay) {
                    cursor.moveToNext();
                    continue;
                }
                else
                     {
                        if (tableName.compareTo(TABLE_WEATHER_INFO_HOUR) == 0) {
                            Log.d("ID: ",String.valueOf(cursor.getInt(0)));
                            int sysTime = getTimeSystem();
                            String temp = time.substring(0, 2);
                            int timeCompa = Integer.valueOf(temp);
                            Log.d("system time", String.valueOf(sysTime));
                            Log.d("app time", String.valueOf(timeCompa));
                            if (sysTime >= timeCompa) {
                                cursor.moveToNext();
                                continue;
                            }
                        }
                    }
                sunrise=cursor.getString(3);
                sunset=cursor.getString(4);
                moon=cursor.getInt(5);
                max=cursor.getInt(6);
                min=cursor.getInt(7);
                uv=cursor.getInt(8);
                curr=cursor.getInt(9);
                wSpeed=cursor.getInt(10);
                windDir=cursor.getString(11);
                WeaCode=cursor.getString(12);
                WeaIcon=cursor.getString(13);
                pre=cursor.getDouble(14);
                hum=cursor.getInt(15);
                vis=cursor.getInt(16);
                clou=cursor.getInt(17);
                wGus=cursor.getInt(18);
                cRain=cursor.getInt(19);
                cWind=cursor.getInt(20);
                cOverC=cursor.getInt(21);
                cSun=cursor.getInt(22);
                cThund=cursor.getInt(23);
                cFog=cursor.getInt(24);
                cFrost=cursor.getInt(25);
                area=cursor.getString(26);
                country=cursor.getString(27);

                WeatherInformationPacket element=new WeatherInformationPacket(area,country,date,sunrise,sunset,moon,max,min,uv, time,curr,wSpeed,windDir,WeaCode,WeaIcon,pre,hum,vis,clou,wGus,cFog,cFrost,cOverC,cRain,cSun,cThund,cWind);

                list.add(element);
                cursor.moveToNext();
            }

        }
        cursor.close();
        db.close();
        return list;
    }
    public void deleteTable(String tableName){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(tableName,null,null);
        db.close();
    }
}
