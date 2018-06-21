package com.example.user.weatherforecast.model;

import com.orm.SugarRecord;

public class User extends SugarRecord{

    String userName, cityName;
    double lat, lon;
    String units = "metric";

    public User(){}

    public User(String userName, String cityName, double lat, double lon)
    {
        this.userName = userName;
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }

}
