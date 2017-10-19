package com.vamsi.weatherapp.weather;

import com.vamsi.weatherapp.BasePresenter;
import com.vamsi.weatherapp.BaseView;
import com.vamsi.weatherapp.data.WeatherItem;

public class WeatherContract {
    interface View extends BaseView<Presenter> {

        void showWeatherItem(WeatherItem weatherItem);

        void displayLoadingIndicator(Boolean isVisible);

        void displayNoNetworkMessage();

        void displayErrorMessage(int stringRessource);

        void setCity(String city);

        void hideKeyboard();
    }

    interface Presenter extends BasePresenter {

        void loadWeatherDetails(String city);
    }
}
