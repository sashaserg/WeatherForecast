package com.example.user.weatherforecast.adapter;
import com.bumptech.glide.Glide;
import com.example.user.weatherforecast.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.weatherforecast.model.Forecast;
import com.example.user.weatherforecast.model.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherAdapterToday extends BaseAdapter {
    private final Context mContext;
    private List<Weather> mForecast;
    private List<Weather> mForecastLastDays;

    DateFormat format;

    TextView tvDate, tvTemp, tvHum;
    ImageView ivIcon;

    public WeatherAdapterToday(Context context, Forecast forecast) {
        this.mContext = context;
        this.mForecast = forecast.getForecast();
        format = new SimpleDateFormat("dd.MM, EE");
    }

    @Override
    public int getCount() {
        return 8;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);

        Log.d("adapter", "pos " + position);

        Weather dayWeather = mForecast.get(position);

        tvDate = view.findViewById(R.id.tv_date);
        tvTemp = view.findViewById(R.id.tv_temp);
        tvHum = view.findViewById(R.id.tv_humidity);
        ivIcon = view.findViewById(R.id.ivIcon);

        //tvDate.setText(format.format(dayWeather.getDate().getTime())); // установка даты

        tvDate.setText(new SimpleDateFormat("H:mm").format(dayWeather.getDate().getTime()));
        tvTemp.setText(dayWeather.getTemperature());
        //tvHum.setText(dayWeather.getHumidity()); // установить влажность
        Glide.with(mContext).load(dayWeather.getIcon()).into(ivIcon);

        return view;
    }
}

