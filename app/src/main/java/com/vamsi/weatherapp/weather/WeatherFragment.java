package com.vamsi.weatherapp.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.vamsi.weatherapp.R;
import com.vamsi.weatherapp.data.WeatherItem;
import com.vamsi.weatherapp.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class WeatherFragment extends Fragment implements WeatherContract.View, Constants {
    private WeatherContract.Presenter mPresenter;

    @BindView(R.id.et_city)
    EditText mCityName;
    @BindView(R.id.button_city)
    Button mGetWeatherDetails;
    @BindView(R.id.tv_degree)
    TextView mDegreeCelcius;
    @BindView(R.id.loading_weather)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.weatherLayout)
    LinearLayout mWeatherLayout;
    @BindView(R.id.iv_weather_icon)
    ImageView mWeatherIcon;
    @BindView(R.id.tv_humidity)
    TextView mHumidity;
    @BindView(R.id.tv_rain_level)
    TextView mRainLevel;
    @BindView(R.id.tv_wind)
    TextView mWind;


    public WeatherFragment() {
        // Requires empty public constructor
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        mGetWeatherDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                mPresenter.loadWeatherDetails(mCityName.getText().toString());
            }
        });

        mPresenter.start();
        return view;
    }

    @Override
    public void setPresenter(@NonNull WeatherContract.Presenter presenter) {
        checkNotNull(presenter);
        mPresenter = presenter;
    }

    @Override
    public void showWeatherItem(WeatherItem weatherItem) {

        mWind.setText(weatherItem.getWind() + "m/s");
        mDegreeCelcius.setText(String.valueOf(weatherItem.getDegreeFarhenheit()));
        mHumidity.setText(weatherItem.getHumidity() + "%");
        mRainLevel.setText(String.valueOf(weatherItem.getRainLevel()));

        // If more time was there, we can move this part of the code to model to fetch the required image,
        // and cache it with the name specified in the url so that next time, we can directly pick image
        // from cache which is faster than downloading over network
        // Also separation of concerns can be achieved.
        final String imageUrl = WEATHER_IMAGE_URL + weatherItem.getImg() + ".png";
        Picasso.with(getContext())
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(mWeatherIcon, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(getContext())
                                .load(imageUrl)
                                .into(mWeatherIcon, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });
    }



    @Override
    public void displayErrorMessage(int stringRessource) {
        mCityName.setError(getString(stringRessource));
    }

    @Override
    public void setCity(String city) {
        mCityName.setText(city);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void displayLoadingIndicator(Boolean isVisible) {
        mLoadingIndicator.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mWeatherLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    @Override
    public void displayNoNetworkMessage() {
        if (getView() != null) {
            Snackbar snackbarNoInternetConnection = Snackbar
                    .make(getView(), getString(R.string.error_msg_internet), Snackbar.LENGTH_LONG);
            snackbarNoInternetConnection.show();
        }
    }
}
