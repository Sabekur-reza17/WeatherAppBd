package com.sabekur2017.weatherappbd.service;

import com.sabekur2017.weatherappbd.model.CurrentWeatherResponseModel;
import com.sabekur2017.weatherappbd.model.ForecastWeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherApiService {
    @GET()
    Call<CurrentWeatherResponseModel> getCurrentWeatherResponse(@Url String urlString);
    @GET()
    Call<ForecastWeatherResponseModel> getForecastWeatherResonse(@Url String forcastString);
}
