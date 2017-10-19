package com.vamsi.weatherapp.weather;

import android.support.annotation.NonNull;

import com.vamsi.weatherapp.R;
import com.vamsi.weatherapp.api.WeatherAPI;
import com.vamsi.weatherapp.api.pojo.WeatherData;
import com.vamsi.weatherapp.data.DataServiceImpl;
import com.vamsi.weatherapp.data.WeatherItem;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

public class WeatherPresenter implements WeatherContract.Presenter {
    private final WeatherContract.View mView;
    private final WeatherAPI mWeatherAPI;
    private WeatherItem mWeatherItem;
    private final DataServiceImpl mDataService;
    private boolean isShownError = false;

    public WeatherPresenter(@NonNull WeatherAPI weatherAPI, @NonNull DataServiceImpl dataService, @NonNull WeatherContract.View view) {
        mView = checkNotNull(view, "MainView cannot be null!");
        mView.setPresenter(this);
        mWeatherAPI = checkNotNull(weatherAPI, "WeatherAPI cannot be null!");
        mDataService = checkNotNull(dataService, "DataService cannot be null!");
    }

    @Override
    public void start() {
        loadWeatherDetails(mDataService.getCityWeather());
    }

    @Override
    public void loadWeatherDetails(String cityWeather) {

            if (cityWeather.isEmpty()) {
                return;
            }
            isShownError = false;
            mView.displayLoadingIndicator(true);
            mWeatherItem = null;

            mView.setCity(cityWeather);

            Call<WeatherData> call = mWeatherAPI.getWeatherCity(cityWeather);
            call.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, retrofit2.Response<WeatherData> response) {
                    if (response.isSuccessful()) {
                        WeatherData currentWeatherData = response.body();
                        String city = "";
                        String img = "";
                        double kelvin = 0;
                        int humidity = 0;
                        float windSpeed = 0;
                        float rainLevel = 0;
                        if (response.body().cod == 200) {
                            city = currentWeatherData.name;
                            mDataService.saveCityWeather(city);
                        }
                        else {
                            mView.displayErrorMessage(R.string.city_not_found);
                        }
                        if (currentWeatherData != null) {
                            if (currentWeatherData.weather != null) {
                                if (currentWeatherData.weather.size() > 0) {
                                    img = currentWeatherData.weather.get(0).icon;
                                }
                            }

                            if (currentWeatherData.main != null) {
                                kelvin = (currentWeatherData.main.temp == null) ? 0 : currentWeatherData.main.temp;
                                humidity = (currentWeatherData.main.humidity == null) ? 0 : currentWeatherData.main.humidity;
                            }
                            if (currentWeatherData.wind != null) {
                                windSpeed = (currentWeatherData.wind.speed == null) ? 0 : currentWeatherData.wind.speed;
                            }
                            if (currentWeatherData.rain != null) {
                                rainLevel = (currentWeatherData.rain.rainLevelLastThreeHours == null) ? 0 : currentWeatherData.rain.rainLevelLastThreeHours;
                            }
                        }
                        mWeatherItem = new WeatherItem(city, img, kelvin, humidity, rainLevel, windSpeed);
                        mView.showWeatherItem(mWeatherItem);
                    }
                    else {
                        if (!isShownError) {
                            mView.displayNoNetworkMessage();
                            isShownError = true;
                        }

                    }
                    mView.displayLoadingIndicator(false);
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    mView.displayLoadingIndicator(false);
                    mView.displayNoNetworkMessage();
                }
            });
    }
}
