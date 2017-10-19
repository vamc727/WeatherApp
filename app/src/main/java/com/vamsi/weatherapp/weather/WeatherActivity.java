package com.vamsi.weatherapp.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vamsi.weatherapp.R;
import com.vamsi.weatherapp.util.ActivityUtils;
import com.vamsi.weatherapp.util.Constants;
import com.vamsi.weatherapp.util.Injection;

/**
 * Main activity of the application
 */
public class WeatherActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (weatherFragment == null) {
            weatherFragment = WeatherFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), weatherFragment, R.id.container);
        }

        new WeatherPresenter(Injection.provideNetworkService(getApplicationContext()).getWeatherAPI(), Injection.provideDataService(getApplicationContext()), weatherFragment);
    }
}
