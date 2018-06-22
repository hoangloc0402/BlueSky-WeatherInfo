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

public class AdapterRCVForecastHour extends RecyclerView.Adapter<AdapterRCVForecastHour.RecyclerViewHolder> {

    List<WeatherInformationPacket> data;

    public AdapterRCVForecastHour(List<WeatherInformationPacket> data) {
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
        View view = inflater.inflate(R.layout.recyclerview_forecast_hour, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (data == null) return;
        WeatherInformationPacket WIP = data.get(position);
        if (WIP == null) return;
        holder.textViewHour.setText(WIP.time);
        boolean toF = ActivityMain.weatherSharedPref.getString(WeatherKeyWord.CURRENT_TEMPERATURE_TYPE, WeatherKeyWord.CELCIUS)
                .equals(WeatherKeyWord.FARENHEIT);
        holder.textViewDegree.setText(WeatherUtils.convertTemperatureWithoutConcat(WIP.tempC, toF));
        holder.imageViewWeatherStatus.setBackgroundResource(WeatherUtils.getWeatherStatusResourceID(Integer.valueOf(WIP.weatherCode)));
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() == 0)
            return 0;
        return data.size() - 1;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHour, textViewDegree;
        ImageView imageViewWeatherStatus;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewHour = itemView.findViewById(R.id.textViewHour);
            textViewDegree = itemView.findViewById(R.id.textViewDegree);
            imageViewWeatherStatus = itemView.findViewById(R.id.imageViewWeatherStatus);
        }
    }
}
