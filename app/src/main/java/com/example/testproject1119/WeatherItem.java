package com.example.testproject1119;

import java.util.List;

public class WeatherItem {
    public String dt_txt;
    public MainInfo main;
    public List<Weather> weather;
    public Wind wind;

    class MainInfo {
        public double temp;
        public double pressure;
        public double humidity;
    }

    class Weather {
        public String main;
    }

    class Wind {
        public double speed;
    }
}
