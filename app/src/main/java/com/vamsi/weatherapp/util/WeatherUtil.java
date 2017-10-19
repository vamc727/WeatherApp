package com.vamsi.weatherapp.util;

import java.text.DecimalFormat;

/**
 * Utility class to convert Kelvin to Celcius and Farhenheit.
 * ToDo: If more time was there, we can add a settings menu with option for user to select between celcius and farhenheit
 */
public class WeatherUtil {
    public static float convertKelvinInCelsius(double kelvin) {
        return (float)(kelvin - 273);
    }

    public static float convertKelvinInFarhenheit(double kelvin) {
        return (float) roundTwoDecimals(((1.8) * convertKelvinInCelsius(kelvin)) + 32);
    }

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
