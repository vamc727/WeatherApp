package com.vamsi.weatherapp.data;

public interface DataService {
    void saveCityWeather(String cityWeather);

    String getCityWeather();
}
