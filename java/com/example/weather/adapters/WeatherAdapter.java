package com.example.weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.model.server.Forecast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{

    private List<Forecast> forecasts = new ArrayList<>();
    final private WeatherAdapter.WeatherClickListener mOnClickListener;
    Calendar calendar = Calendar.getInstance();

    public WeatherAdapter(WeatherAdapter.WeatherClickListener clickListener){
        this.mOnClickListener = clickListener;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item, viewGroup, false);
        return new WeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder viewHolder, final int position) {
        Forecast forecast = forecasts.get(position);
        viewHolder.bind(forecast);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public void refresh(List<Forecast> forecasts){
        this.forecasts = forecasts;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView time, temperature;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            time = (TextView) view.findViewById(R.id.time);
            temperature = (TextView) view.findViewById(R.id.temperature);
            imageView = (ImageView) view.findViewById(R.id.weather_icon);

            view.setOnClickListener(this);
        }

        public void bind(Forecast forecast){
            int temp = (int)Math.round(forecast.main.temp);
            temperature.setText(String.valueOf(temp) + "\u00B0");

            Date date = new Date(forecast.dt*1000);
            calendar.setTime(date);
            String day = String.valueOf(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
            //String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            //time.setText(day +" " + forecast.dtTxt.substring(11, 16));
            time.setText(forecast.dtTxt.substring(11, 16));


            //Glide.with(imageView).load(R.drawable.us).into(imageView);
            String path = "https://openweathermap.org/img/wn/" + forecast.weather.get(0).icon + "@2x.png";
            Glide.with(imageView).load(path).into(imageView);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onWeatherClick(forecasts.get(getAdapterPosition()));
        }
    }

    public interface WeatherClickListener{
        void onWeatherClick(Forecast forecast);
    }
}
