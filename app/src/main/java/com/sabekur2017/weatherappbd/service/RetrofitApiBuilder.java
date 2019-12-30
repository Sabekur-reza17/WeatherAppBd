package com.sabekur2017.weatherappbd.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiBuilder {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private Retrofit retrofit;

    public RetrofitApiBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public WeatherApiService getWeatherApiService(){
        return retrofit.create(WeatherApiService.class);
    }
}
