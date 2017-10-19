package com.vamsi.weatherapp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vamsi.weatherapp.util.Constants;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class is used to save the city into shared preferences so that when
 * user reopens the app, the city that was last searched will be shown.
 */

public class DataServiceImpl implements Constants, DataService {
    private static DataServiceImpl INSTANCE = null;
    private SharedPreferencesHelper mSharedPreferencesHelper = null;

    private DataServiceImpl(@NonNull Context context) {
        checkNotNull(context);
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public static DataServiceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataServiceImpl(context);
        }
        return INSTANCE;
    }

    public void saveCityWeather(String cityWeather) {
        mSharedPreferencesHelper.putString(KEY_CITY_WEATHER, cityWeather);
    }

    public String getCityWeather() {
        return mSharedPreferencesHelper.getString(KEY_CITY_WEATHER);
    }
}
