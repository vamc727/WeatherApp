package com.vamsi.weatherapp.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("3h")
    @Expose
    public Float rainLevelLastThreeHours;
}
