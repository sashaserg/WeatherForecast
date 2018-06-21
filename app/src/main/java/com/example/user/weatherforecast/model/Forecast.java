package com.example.user.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    public class CityValue {
        String name;
        int id;
        String country;
    }

    @SerializedName("list")
    private List<Weather> forecast;

    @SerializedName("city")
    private CityValue CityTemp;

    public String getCityName() {
        return CityTemp.name;
    }

    public List<Weather> getForecast() {
        return forecast;
    }
}
