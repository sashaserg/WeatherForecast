package com.example.user.weatherforecast.model;


import android.content.Context;
import android.content.SharedPreferences;
import com.example.user.weatherforecast.MainActivity;
import static android.content.Context.MODE_PRIVATE;

public class PositionSettings {

    private static PositionSettings settings;
    private static String PREF_NAME = "posPrefSettings";

    SharedPreferences sharedPreferences;

    private PositionSettings(){}

    public static PositionSettings getSettings()
    {
        if (settings == null)
            settings = new PositionSettings();
        return settings;
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String loadCityName(Context context) {
        return getPrefs(context).getString("CityName", "");
    }
    public Double loadLatitude(Context context)
    {
        return (double)getPrefs(context).getFloat("Lat", 0);
    }
    public Double loadLongitude(Context context)
    {
        return (double)getPrefs(context).getFloat("Lon", 0);
    }
    public boolean isSettingsExist(Context context)
    {
        return getPrefs(context).getBoolean("Existings", false);
    }

    public void savePositionSettings(Context context, String cityName, double lat, double lon) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("CityName", cityName);
        editor.putFloat("Lat", (float)lat);
        editor.putFloat("Lon", (float)lon);
        editor.putBoolean("Existings", true);
        editor.commit();
    }


}
