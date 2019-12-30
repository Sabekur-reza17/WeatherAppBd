package com.sabekur2017.weatherappbd.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sabekur2017.weatherappbd.MainActivity;
import com.sabekur2017.weatherappbd.R;
import com.sabekur2017.weatherappbd.model.ForecastModel;

import java.util.ArrayList;

public class ForecastWeatherAdapter extends RecyclerView.Adapter<ForecastWeatherAdapter.ForcastViewHolder> {

    private Context context;
    private ArrayList<ForecastModel > forecastModels;

    public ForecastWeatherAdapter(Context context, ArrayList<ForecastModel> forecastModels) {
        this.context = context;
        this.forecastModels = forecastModels;
    }

    @NonNull
    @Override
    public ForcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.forecast_single_row,parent,false);
        return new ForcastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForcastViewHolder holder, int position) {
        String image = forecastModels.get(position).getImage();
        Uri iconUri = Uri.parse("http://openweathermap.org/img/w/" + image + ".png");
        Glide.with(context).load(iconUri).into(holder.image);
        holder.status.setText(forecastModels.get(position).getStatus());
        holder.day.setText(forecastModels.get(position).getDay());
        holder.temp.setText(forecastModels.get(position).getTemp()+ MainActivity.tempSign);
        holder.minTemp.setText(forecastModels.get(position).getMinTemp()+ MainActivity.tempSign);
        holder.maxTemp.setText(forecastModels.get(position).getMaxTemp()+ MainActivity.tempSign);
        holder.sunRise.setText(forecastModels.get(position).getSunRise()+"%");
        holder.sunSet.setText(forecastModels.get(position).getSunSet()+" mb");


    }

    @Override
    public int getItemCount() {
        return forecastModels.size();
    }

    public class ForcastViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView status;
        TextView day;
        TextView temp;
        TextView maxTemp;
        TextView minTemp;
        TextView sunRise;
        TextView sunSet;
        public ForcastViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            status = itemView.findViewById(R.id.status);
            day = itemView.findViewById(R.id.day);
            temp = itemView.findViewById(R.id.temp);
            maxTemp = itemView.findViewById(R.id.maxTemp);
            minTemp = itemView.findViewById(R.id.minTemp);
            sunRise = itemView.findViewById(R.id.sunRise);
            sunSet = itemView.findViewById(R.id.sunSet);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
