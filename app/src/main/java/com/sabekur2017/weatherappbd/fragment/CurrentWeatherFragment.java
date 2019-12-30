package com.sabekur2017.weatherappbd.fragment;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sabekur2017.weatherappbd.MainActivity;
import com.sabekur2017.weatherappbd.R;
import com.sabekur2017.weatherappbd.model.CurrentWeatherResponseModel;
import com.sabekur2017.weatherappbd.service.RetrofitApiBuilder;
import com.sabekur2017.weatherappbd.service.WeatherApiService;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {
    private TextView location, temp, description, date, maxTemp, minTemp, sunSet, sunRise, humedity, pressure, wind;
    private ImageView image;
    private static final String TAG = MainActivity.class.getSimpleName();

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        location = view.findViewById(R.id.myLocation);
        temp = view.findViewById(R.id.temp);
        image = view.findViewById(R.id.weatherImage);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        maxTemp = view.findViewById(R.id.maxTemp);
        minTemp = view.findViewById(R.id.minTemp);
        sunRise = view.findViewById(R.id.sunRise);
        sunSet = view.findViewById(R.id.sunSet);
        humedity = view.findViewById(R.id.humedity);
        pressure = view.findViewById(R.id.pressure);
        wind = view.findViewById(R.id.wind);
        // Inflate the layout for this fragment
        WeatherApiService weatherApiService=new RetrofitApiBuilder().getWeatherApiService();
        String urlString = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s", MainActivity.latitude,MainActivity.longitude,MainActivity.units,getString(R.string.weather_api));
        Call<CurrentWeatherResponseModel> call=weatherApiService.getCurrentWeatherResponse(urlString);
        call.enqueue(new Callback<CurrentWeatherResponseModel>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponseModel> call, Response<CurrentWeatherResponseModel> response) {
                if(response.isSuccessful()){
                    CurrentWeatherResponseModel currentWeatherResponseModel=response.body();
                    String loc = currentWeatherResponseModel.getName();
                    String country = currentWeatherResponseModel.getSys().getCountry();
                    location.setText(loc+", "+country);
                    double tmp = currentWeatherResponseModel.getMain().getTemp();
                    int mm = (int) tmp;
                    String nn = String.valueOf(mm);
                    temp.setText(nn+ MainActivity.tempSign);
                    String iconString = currentWeatherResponseModel.getWeather().get(0).getIcon();
                    Uri iconUri = Uri.parse("http://openweathermap.org/img/w/"+iconString+".png");
                    Glide.with(getContext()).load(iconUri).into(image);


                    String desc = currentWeatherResponseModel.getWeather().get(0).getDescription().toString();
                    description.setText(desc);

                    long currentTime = currentWeatherResponseModel.getDt();
                    Date cTimeDateFormte = new Date(currentTime*1000L);
                    SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
                    SimpleDateFormat dfTime = new SimpleDateFormat("hh:mm a");
                    String finalDate = df.format(cTimeDateFormte.getTime());
                    String curTime = dfTime.format(cTimeDateFormte.getTime());
                    date.setText(finalDate+"       "+curTime);

                    String minTmp = currentWeatherResponseModel.getMain().getTempMin().toString();
                    minTemp.setText(minTmp+ MainActivity.tempSign);

                    String maxTmp = currentWeatherResponseModel.getMain().getTempMax().toString();
                    maxTemp.setText(maxTmp+ MainActivity  .tempSign);

                    long unix_sunrise = currentWeatherResponseModel.getSys().getSunrise();
                    Date date_sunrise = new Date(unix_sunrise*1000L);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("hh:mm a");
                    String sunRs = df2.format(date_sunrise.getTime());
                    sunRise.setText(sunRs);

                    long unix_sunset = currentWeatherResponseModel.getSys().getSunset();
                    Date date_sunset = new Date(unix_sunset*1000L);
                    SimpleDateFormat df3 = new SimpleDateFormat("hh:mm a");
                    String sunSt = df3.format(date_sunset.getTime());
                    sunSet.setText(sunSt);

                    String hmedity = currentWeatherResponseModel.getMain().getHumidity().toString();
                    humedity.setText(hmedity+"%");

                    String prssure = currentWeatherResponseModel.getMain().getHumidity().toString();
                    pressure.setText(prssure+" mb");

                    String wnd = currentWeatherResponseModel.getWind().getSpeed().toString();
                    wind.setText(wnd+" mphn");

                    Log.d(TAG, " loc: "+loc);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponseModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
        return view;
    }

}
