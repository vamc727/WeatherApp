package com.vamsi.weatherapp.data;

import android.support.annotation.NonNull;

import com.vamsi.weatherapp.util.WeatherUtil;

public class WeatherItem {
    private final String mCity;
    private final String mImg;
    private final float mDegreeFarhenheit;
    private final int mHumidity;
    private final float mRainLevel;
    private final float mWind;

    public WeatherItem(@NonNull String city, @NonNull String img, @NonNull double kelvin, @NonNull int humidity, @NonNull float rainLevel, @NonNull float wind) {
        mCity = city;
        mImg = img;
        mDegreeFarhenheit = WeatherUtil.convertKelvinInFarhenheit(kelvin);
        mHumidity = humidity;
        mRainLevel = rainLevel;
        mWind = wind;
    }

    public String getCity() {
        return mCity;
    }

    public String getImg() {
        return mImg;
    }

    public float getDegreeFarhenheit() {
        return mDegreeFarhenheit;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public float getRainLevel() {
        return mRainLevel;
    }

    public float getWind() {
        return mWind;
    }
}
