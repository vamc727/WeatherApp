package com.vamsi.weatherapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vamsi.weatherapp.weather.WeatherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * UI test with Espresso
 */
@RunWith(AndroidJUnit4.class)
public class WeatherActivityTest {

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityTestRule = new ActivityTestRule<>(WeatherActivity.class);

    @Test
    public void weatherActivityTest() {

        onView(withId(R.id.et_city)).perform(click());

        // here we are checking if the text that has been entered by the text is same as what we wanted to
        onView(withId(R.id.et_city)).perform(replaceText("chicago"), closeSoftKeyboard())
                .check(matches(withText(containsString("chicago"))));

        // here we are checking if the button has the text "Get Weather". Test will fail if it's not
        onView(withId(R.id.button_city)).perform(click()).check(matches(withText(containsString("Get Weather"))));
    }

}
