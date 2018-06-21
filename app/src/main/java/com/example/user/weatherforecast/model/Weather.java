package com.example.user.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class Weather {

    public class WeatherValue {
        double temp, temp_min, temp_max;
        double humidity;
        double sea_level;
    }

    public class WeatherIcon {
        String icon;
    }

    @SerializedName("main")
    private WeatherValue weatherTemp; // в объект записывается температура и влажность

    @SerializedName("weather")
    private List<WeatherIcon> weatherIcon;

    @SerializedName("dt")
    private long time;

    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time * 1000);
        return date;
    }

    public String getTemperature() {
        return String.valueOf(Math.round(weatherTemp.temp)) + "\u00B0";
    }

    public String getMinMaxTemperature() {
        return String.valueOf(Math.round(weatherTemp.temp_max)) + "\u00B0" +
                " / " + Math.round(weatherTemp.temp_min) + "\u00B0";
    }

    public String getHumidity() {
        return String.valueOf(weatherTemp.humidity) + "%";
    }


    public String getIcon() {
        return "http://openweathermap.org/img/w/" + weatherIcon.get(0).icon + ".png";
    }

    @Override
    public String toString() {
        return  "Weather{" +
                "weatherTemp=" + weatherTemp.temp + " " + weatherTemp.temp_min + " " + weatherTemp.temp_max +
                "humidity=" + weatherTemp.humidity +
                ", time=" + getDate().getTime().toString() +
                '}';
    }
}
