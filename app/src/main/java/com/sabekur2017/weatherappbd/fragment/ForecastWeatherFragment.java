package com.sabekur2017.weatherappbd.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabekur2017.weatherappbd.MainActivity;
import com.sabekur2017.weatherappbd.R;
import com.sabekur2017.weatherappbd.adapter.ForecastWeatherAdapter;
import com.sabekur2017.weatherappbd.model.ForecastModel;
import com.sabekur2017.weatherappbd.model.ForecastWeatherResponseModel;
import com.sabekur2017.weatherappbd.service.RetrofitApiBuilder;
import com.sabekur2017.weatherappbd.service.WeatherApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastWeatherFragment extends Fragment {
    private RecyclerView recyclerView;
    private WeatherApiService weatherApiService;
    private ForecastWeatherResponseModel forecastWeatherResponseModel;
    private ArrayList<ForecastModel> forecastModels=new ArrayList<>();
    private ForecastModel forecastModel;
    private ForecastWeatherAdapter forecastWeatherAdapter;
    private Calendar calendar;
    private String iconString, statusString, dayString, tempString, minTString, maxTString, sunRiseString, sunSetString;


    public ForecastWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        calendar = Calendar.getInstance();
        weatherApiService=new RetrofitApiBuilder().getWeatherApiService();
        String forcastString=String.format("forecast?lat=%f&lon=%f&units=%s&appid=%s", MainActivity.latitude,MainActivity.longitude,MainActivity.units,getString(R.string.weather_api));
        Call<ForecastWeatherResponseModel> forCastCall=weatherApiService.getForecastWeatherResonse(forcastString);
        forCastCall.enqueue(new Callback<ForecastWeatherResponseModel>() {
            @Override
            public void onResponse(Call<ForecastWeatherResponseModel> call, Response<ForecastWeatherResponseModel> response) {
                if(response.isSuccessful()){
                    ForecastWeatherResponseModel forecastWeatherResponseModel=response.body();
                    ArrayList<ForecastModel> forecastModelArrayList=new ArrayList<>();
                    for(int i=0;i<forecastWeatherResponseModel.getList().size();i++){
                        iconString = forecastWeatherResponseModel.getList().get(i).getWeather().get(0).getIcon();

                        statusString = forecastWeatherResponseModel.getList().get(i).getWeather().get(0).getDescription();

                        long unix_day = forecastWeatherResponseModel.getList().get(i).getDt();
                        Date date = new Date(unix_day*1000L);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMd, hha");
                        SimpleDateFormat df2 = new SimpleDateFormat("hha");
                        String todayTime = df2.format(date.getTime());

                        SimpleDateFormat dfMy = new SimpleDateFormat("d");
                        int weatherDate = Integer.parseInt(dfMy.format(date.getTime()));
                        int sysDate = Integer.parseInt(dfMy.format(calendar.getTime()));
                        if( weatherDate == sysDate){
                            dayString = "Today, "+todayTime;
                        }
                        else if( (weatherDate-1) == sysDate){
                            dayString = "Tomorrow, "+todayTime;
                        }
                        else {
                            dayString = df.format(date.getTime());
                        }
                        tempString = String.valueOf(forecastWeatherResponseModel.getList().get(i).getMain().getTemp().intValue());

                        minTString = String.valueOf(forecastWeatherResponseModel.getList().get(i).getMain().getTempMin().intValue());

                        maxTString = String.valueOf(forecastWeatherResponseModel.getList().get(i).getMain().getTempMax().intValue());

                        sunRiseString = String.valueOf(forecastWeatherResponseModel.getList().get(i).getMain().getHumidity().intValue());

                        sunSetString = String.valueOf(forecastWeatherResponseModel.getList().get(i).getMain().getPressure().intValue());
                        forecastModel=new ForecastModel(iconString,statusString,dayString,tempString,minTString,maxTString,sunRiseString,sunSetString);
                        forecastModelArrayList.add(forecastModel);

                    }
                    forecastModels=forecastModelArrayList;
                    forecastWeatherAdapter=new ForecastWeatherAdapter(getActivity().getApplicationContext(),forecastModels);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(forecastWeatherAdapter);

                }
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponseModel> call, Throwable t) {

            }
        });
        return view;
    }

}
