package com.ttcnpm.group28.weatherapp.quochoang;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.hoangloc.FragmentWeather;
import com.ttcnpm.group28.weatherapp.namquan.WeatherInformationPacket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class DetailAnalyticActivity extends AppCompatActivity {
    int backgroundColor;
    int backgroundImage;
    int weatherStatusImage;
    ArrayList<WeatherInformationPacket> forecastDayData;
    ArrayList<WeatherInformationPacket> forecastHourData;

    private CombinedChart mChart;
    TextView tvTemperature, tvHumidity, tvVisibility,tvUVIndex, tvSunrise, tvSunset, tvMoon, tvWindSpeed, tvWindGust,tvWindDirection,tvCloudCover,
    tvChanceSunshine ,tvChanceOvercast ,tvChanceFrost ,tvChanceRain;
    ImageView bigBlade, smallBlade;
    ScrollView scrollView;
    CardView cardViewDetails, cardViewChart, cardViewChance, cardViewWindandPressure, cardViewSunandMoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_detail_analytic);
        Bundle bundle = this.getIntent().getExtras();
        forecastDayData = (ArrayList<WeatherInformationPacket>) bundle.getSerializable("forecastDayData");
        forecastHourData = (ArrayList<WeatherInformationPacket>) bundle.getSerializable("forecastHourData");
        backgroundColor = (int) bundle.getSerializable("backgroundColor");
        backgroundImage = (int)bundle.getSerializable("backgroundImage");
        weatherStatusImage = (int) bundle.getSerializable("weatherStatusImage");

        WeatherInformationPacket currentWIP = forecastHourData.get(0);

        cardViewDetails = findViewById(R.id.cardViewDetails);
        cardViewChart= findViewById(R.id.cardViewChart);
        cardViewChance= findViewById(R.id.cardViewChance);
        cardViewWindandPressure= findViewById(R.id.cardViewWindandPressure);
        cardViewSunandMoon= findViewById(R.id.cardViewSunandMoon);
        tvTemperature = findViewById(R.id.textViewDetailTemperature);
        tvHumidity = findViewById(R.id.textViewDetailHumidity);
        tvVisibility = findViewById(R.id.textViewDetailVisibility);
        tvUVIndex = findViewById(R.id.textViewDetailUVIndex);
        tvSunrise = findViewById(R.id.textViewDetailSunrise);
        tvSunset = findViewById(R.id.textViewDetailSunset);
        tvMoon = findViewById(R.id.textViewDetailMoonIllumination);
        tvWindSpeed = findViewById(R.id.textViewDetailWindSpeed);
        tvWindDirection = findViewById(R.id.textViewDetailWindDirection);
        tvWindGust= findViewById(R.id.textViewDetailWindGust);
        tvCloudCover = findViewById(R.id.textViewDetailCloudCover);
        scrollView = findViewById(R.id.scrollViewDetail);
        tvChanceFrost = findViewById(R.id.textViewChanceFrost);
        tvChanceSunshine = findViewById(R.id.textViewChanceSunshine);
        tvChanceOvercast = findViewById(R.id.textViewChanceOvercast);
        tvChanceRain = findViewById(R.id.textViewChanceRain);
        tvTemperature.setText(FragmentWeather.convertTemperature(currentWIP.tempC));
        tvHumidity.setText(String.valueOf(currentWIP.humidity) +" %");
        tvVisibility.setText(String.valueOf(currentWIP.visibility)+" km");
        tvUVIndex.setText(String.valueOf(currentWIP.uvIndex));
        tvSunrise.setText(String.valueOf(currentWIP.sunrise));
        tvSunset.setText(String.valueOf(currentWIP.sunset));
        tvMoon.setText(String.valueOf(currentWIP.moon_illumination)+" %");

        tvWindSpeed.setText(FragmentWeather.convertSpeed(currentWIP.windSpeedKmph));
        tvWindDirection.setText(currentWIP.windDirection16Point);
        tvWindGust.setText(FragmentWeather.convertSpeed(Integer.valueOf(currentWIP.windGustKmph)));

        tvCloudCover.setText(String.valueOf(currentWIP.cloudCover)+" %");
        tvChanceFrost.setText(String.valueOf(currentWIP.chanceOfFrost)+" %");
        tvChanceSunshine.setText(String.valueOf(currentWIP.chanceOfSunshine)+" %");
        tvChanceOvercast.setText(String.valueOf(currentWIP.chanceOfOvercast)+" %");
        tvChanceRain.setText(String.valueOf(currentWIP.chanceOfRain)+" %");

        smallBlade = findViewById(R.id.imageViewSmallBlade);
        bigBlade = findViewById(R.id.imageViewBigBlade);

        findViewById(R.id.imageViewDetailWeatherStatus).setBackgroundResource(weatherStatusImage);
        Resources currentResources = this.getResources();
        cardViewDetails.setCardBackgroundColor(currentResources.getColor(backgroundColor));
        cardViewChart.setCardBackgroundColor(currentResources.getColor(backgroundColor));
        cardViewChance.setCardBackgroundColor(currentResources.getColor(backgroundColor));
        cardViewWindandPressure.setCardBackgroundColor(currentResources.getColor(backgroundColor));
        cardViewSunandMoon.setCardBackgroundColor(currentResources.getColor(backgroundColor));
        RotateAnimation rotate = new RotateAnimation(0, 36000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(150000);
        rotate.setRepeatCount(Animation.INFINITE);
        bigBlade.setAnimation(rotate);

        RotateAnimation rotate2 = new RotateAnimation(0, 36000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate2.setDuration(150000);
        rotate2.setStartOffset(2000);
        rotate2.setRepeatCount(Animation.INFINITE);
        smallBlade.setAnimation(rotate2);

        RotateAnimation rotate3 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate3.setDuration(4000);
        rotate3.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.imageViewSunshine).setAnimation(rotate3);
        scrollView.setBackgroundResource(backgroundImage);


        drawChart();
    }


    void drawChart(){
        mChart = findViewById(R.id.chart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setScaleY(0.9f);


        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextColor(Color.WHITE);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setTextColor(Color.rgb(251, 148, 3));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextColor(Color.WHITE);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);


        String[] quarters = new String[forecastDayData.size()];
        int count = 0;
        for (WeatherInformationPacket  WIP : forecastDayData){
            Date date = new Date();
            SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date = inFormat.parse(WIP.Date.substring(8,10) + "-" + WIP.Date.substring(5,7)+ "-" + WIP.Date.substring(0,4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            String day = outFormat.format(date);
            quarters[count]= day.substring(0,3 );
            count ++;
        }
        final String[] f = quarters;
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return f[(int) value];
            }
        };
        xAxis.setValueFormatter(formatter);
        CombinedData data = new CombinedData();
        LineDataSet lineDataSet = generateLineData(forecastDayData);
        data.setData(setLineDataSet(lineDataSet));
        data.setData(generateBarData());
        mChart.setData(data);
        mChart.invalidate();
    }

    public LineData setLineDataSet(LineDataSet set) {
        LineData d = new LineData();
        set.setColor(Color.rgb(255, 153, 0));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(255, 102, 0));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(251, 148, 3));
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        d.addDataSet(set);
        return d;
    }

    public LineDataSet generateLineData(ArrayList<WeatherInformationPacket> forecastDayData) {

        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < forecastDayData.size(); index++)
            entries.add(new Entry(0.5f + index , forecastDayData.get(index).tempC));
        LineDataSet set = new LineDataSet(entries, getString(R.string.temperatureC));


        return set;
    }

    private BarData generateBarData() {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        for (int index = 0; index < forecastDayData.size(); index++) {
            entries.add(new BarEntry(0.5f + index ,Float.valueOf(String.valueOf(forecastDayData.get(index).precipMM))));
        }
        BarDataSet set = new BarDataSet(entries, getString(R.string.rainfall));
        set.setColor(Color.rgb(0, 102, 204));
        set.setValueTextColor(Color.rgb(0, 0, 153));
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.WHITE);
        set.setColor(Color.rgb(69, 193, 250));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        BarData d = new BarData(set);
        return d;
    }
}
