package com.example.user.weatherforecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.user.weatherforecast.adapter.WeatherAdapterToday;
import com.example.user.weatherforecast.adapter.WeatherAdapterDaily;
import com.example.user.weatherforecast.model.Forecast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    WeatherApi.ApiInterface weatherApi;
    WeatherNotificator weatherNotificator;

    double lat, lon;
    String key = WeatherApi.KEY;
    String units = "metric";
    String curCityNameString;

    Button btnSearchByGoogleMaps;
    TextView curTemp, curCityName, curDateTime;
    GridView gvForecast, gvForecastDaily;
    PlaceAutocompleteFragment autocompleteFragment;

    int PLACE_PICKER_REQUEST = 1;
    int FORECAST_NOTIFICATION_ID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvForecast = (GridView)findViewById(R.id.gvForecast);
        gvForecastDaily = (GridView)findViewById(R.id.gvForecastDays);
        curCityName = (TextView)findViewById(R.id.currentCityName);
        curTemp = (TextView)findViewById(R.id.currentTemperature);
        curDateTime = (TextView)findViewById(R.id.currentDateTime);
        btnSearchByGoogleMaps = (Button)findViewById(R.id.ButtonGoogleMapsSearch);

        weatherApi = WeatherApi.getInstance().create(WeatherApi.ApiInterface.class);
        weatherNotificator = new WeatherNotificator(MainActivity.this, "Test Title", "TestText");

        /**
         *  Гуглвский фрагмент поля с автодополнением
         */

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lon = place.getLatLng().longitude;
                lat = place.getLatLng().latitude;
                curCityNameString = place.getName().toString();
                curCityName.setText(curCityNameString);
                updateWeather();
            }

            @Override
            public void onError(Status status) {
                Log.d("MyLog", "An error occurred: " + status);
            }
        });

        btnSearchByGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        /*btnGoogleSingin.setOnClickListener(new View.OnClickListener() { // обработка кнопки гугл
            @Override
            public void onClick(View view) {
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                        false, null, null, null, null);
                startActivityForResult(intent, 123);
            }
        });*/
    }

    /**
     *  Обработка выбора места на гугл карте
     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                lon = place.getLatLng().longitude;
                lat = place.getLatLng().latitude;
                autocompleteFragment.setText("");
                curCityNameString = place.getAddress().toString();
                curCityName.setText(curCityNameString);
                updateWeather();
            }
        }
    }

    /**
     *  Выполняем запрос на http://api.openweathermap.org с помощью retrofit с нужными координатами.
     *  Ответ парсится gson-ом.
     *  Распихиваем всё по вьюшкам на гланом активити, кидаем адаптеры.
     */

    public void updateWeather(){
        Call<Forecast> callForecast = weatherApi.getForecast(lat, lon, units, key);
        callForecast.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast data = response.body();
                Log.d("MyLogs", "Weather Forecast " + data.getForecast().toString());
                if (response.isSuccessful()) {
                    gvForecast.setAdapter(new WeatherAdapterToday(MainActivity.this, data));
                    gvForecastDaily.setAdapter(new WeatherAdapterDaily(MainActivity.this, data));
                    curTemp.setText(data.getForecast().get(0).getTemperature());
                    weatherNotificator.createNotification(FORECAST_NOTIFICATION_ID, curCityNameString, data.getForecast().get(0).getTemperature());
                    curDateTime.setText(new SimpleDateFormat("H:mm").format(data.getForecast().get(0).getDate().getTime()));
                }
            }
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.d("MyLogs", t.toString());
            }
        });
    }


}
