package com.ttcnpm.group28.weatherapp.hoangloc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttcnpm.group28.weatherapp.R;
import com.ttcnpm.group28.weatherapp.dinhduc.WeatherKeyWord;
import com.ttcnpm.group28.weatherapp.namquan.WeatherInformationPacket;

import java.util.ArrayList;
import java.util.List;

public class AdapterRCVForecastDay extends RecyclerView.Adapter<AdapterRCVForecastDay.RecyclerViewHolder> {

    List<WeatherInformationPacket> data;

    public AdapterRCVForecastDay(List<WeatherInformationPacket> data) {
        if (data == null) {
            this.data = new ArrayList<>();
            return;
        }
        this.data = data;
    }

    public void setData(List<WeatherInformationPacket> newData) {
        if (newData == null)
            return;
        this.data = newData;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_forecast_day, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (data == null)
            return;
        WeatherInformationPacket WIP = data.get(position);
        if (WIP == null)
            return;
        holder.textViewDaysOfWeek.setText(WeatherUtils.getDayEEEE(WIP));

        boolean toF = ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, WeatherKeyWord.CELCIUS)
                .equals(WeatherKeyWord.FARENHEIT);
        holder.textViewMinTemp.setText(WeatherUtils.convertTemperatureWithoutConcat(WIP.minTempC, toF) + " -");
        holder.textViewMaxTemp.setText(WeatherUtils.convertTemperatureWithoutConcat(WIP.maxTempC, toF));
        holder.imageViewWeatherStatus.setBackgroundResource(WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP.weatherCode)));
    }

    @Override
    public int getItemCount() {
        if (data == null |data.size() == 0) return 0;
        return ActivityMain.weatherSharedPref.getInt(WeatherKeyWord.CURRENT_QUANTITY_OF_DAY, 7);
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDaysOfWeek, textViewMinTemp, textViewMaxTemp;
        ImageView imageViewWeatherStatus;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewDaysOfWeek = itemView.findViewById(R.id.textViewDaysOfWeek);
            textViewMinTemp = itemView.findViewById(R.id.textViewForecastMinTemp);
            textViewMaxTemp = itemView.findViewById(R.id.textViewForecastMaxTemp);
            imageViewWeatherStatus = itemView.findViewById(R.id.imageViewWeatherStatus);
        }
    }


}
