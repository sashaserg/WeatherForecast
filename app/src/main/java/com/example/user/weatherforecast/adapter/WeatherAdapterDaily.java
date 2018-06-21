package com.example.user.weatherforecast.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.weatherforecast.R;
import com.example.user.weatherforecast.model.Forecast;
import com.example.user.weatherforecast.model.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherAdapterDaily extends BaseAdapter {

    private final Context mContext;
    private List<Weather> mForecast;
    private List<Weather> mForecastLastDays;

    DateFormat format;

    boolean positionIsDone = false;
    TextView tvDate, tvTemp, tvHum;
    ImageView ivIcon;

    public WeatherAdapterDaily(Context context, Forecast forecast) {
        this.mContext = context;
        this.mForecast = forecast.getForecast();
        format = new SimpleDateFormat("dd.MM, EE");
        mForecastLastDays = mForecast.subList(8, mForecast.size()); // делаем из 5ти дней 4 последних дня
        Log.d("adapter", "mForecast_size " + mForecast.size() + " last " + mForecastLastDays.size());
    }


    @Override
    public int getCount() {
        return 4;
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
        View view = inflater.inflate(R.layout.forecast_horizontal_item, parent, false);

        Log.d("adapter", "pos " + position);

        Weather dayWeather = mForecastLastDays.get(8*position);

        tvDate = view.findViewById(R.id.tv_date);
        tvTemp = view.findViewById(R.id.tv_temp);
        tvHum = view.findViewById(R.id.tv_humidity);
        ivIcon = view.findViewById(R.id.ivIcon);

        tvDate.setText(format.format(dayWeather.getDate().getTime()));
        tvTemp.setText(dayWeather.getTemperature());
        //tvHum.setText(dayWeather.getHumidity());
        Glide.with(mContext).load(dayWeather.getIcon()).into(ivIcon);

        return view;
    }

}
