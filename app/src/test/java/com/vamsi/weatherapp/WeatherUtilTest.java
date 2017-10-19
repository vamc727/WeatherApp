package com.vamsi.weatherapp;

import com.vamsi.weatherapp.util.WeatherUtil;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Junit test
 */
public class WeatherUtilTest {

    private final double TEST_KELVIN = 293.33;
    private final float TEST_CELCIUS = 20.33F;
    private final float TEST_FARHENHEIT = 68.59F;

    @Test
    public void convertKelvinInCelsius() throws Exception {
        assertThat(WeatherUtil.convertKelvinInCelsius(TEST_KELVIN), is(TEST_CELCIUS));
    }

    @Test
    public void convertKelvinInFarhenheit() throws Exception {
        assertThat(WeatherUtil.convertKelvinInFarhenheit(TEST_KELVIN), is(TEST_FARHENHEIT));
    }

}