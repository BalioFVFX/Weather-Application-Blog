package com.example.erik.weatherapplicationblog;

import com.google.gson.annotations.SerializedName;

public class WeatherMain {

    @SerializedName(value = "main")
    private Weather weather;

    public WeatherMain() {
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}

