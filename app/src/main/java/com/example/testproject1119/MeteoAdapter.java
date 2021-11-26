package com.example.testproject1119;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MeteoAdapter extends RecyclerView.Adapter<MeteoAdapter.ViewHolder> {

    private List<WeatherItem> data;

    public MeteoAdapter(final List<WeatherItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MeteoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_meteo, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MeteoAdapter.ViewHolder holder, int position) {
        WeatherItem weatherItem = data.get(position);

        holder.date.setText(
            formatDataToDisplay("Time",
                weatherItem.dt_txt)
        );

        holder.temp.setText(
            formatDataToDisplay("Temp",
                Double.toString(weatherItem.main.temp)
            )
        );

        holder.cloud.setText(
            formatDataToDisplay("Weather",
                weatherItem.weather.get(0).main
            )
        );

        holder.wind.setText(
            formatDataToDisplay("Wind",
                Double.toString(weatherItem.wind.speed)
            )
        );

        holder.pressure.setText(
            formatDataToDisplay("Pressure",
                Double.toString(weatherItem.main.pressure))
        );

        holder.humidity.setText(
            formatDataToDisplay("Humidity",
                Double.toString(weatherItem.main.humidity))
        );
    }

    private String formatDataToDisplay(String header, String data) {
        return header + ": " + data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView date;
        private TextView temp;
        private TextView cloud;
        private TextView wind;
        private TextView pressure;
        private TextView humidity;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            date = view.findViewById(R.id.date);
            temp = view.findViewById(R.id.temp);
            cloud = view.findViewById(R.id.cloud);
            wind = view.findViewById(R.id.wind);
            pressure = view.findViewById(R.id.pressure);
            humidity = view.findViewById(R.id.humidity);
        }
    }
}
