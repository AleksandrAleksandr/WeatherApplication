package com.example.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather.adapters.WeatherAdapter;
import com.example.weather.model.CityModel;
import com.example.weather.model.server.Forecast;
import com.example.weather.viewmodels.WeatherViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityWeatherFragment extends Fragment {

    //CityWeatherFragmentArgs mArgs;
    RecyclerView recyclerView;
    TextView cityTextView, country, textView3, textView4, day, time, description, temperature, osadki, humidity, pressure, direction, wind;
    WeatherAdapter adapter;
    WeatherViewModel viewModel;
    ImageView icon, animationTest, arrowDirection;
    RotateAnimation animation;
    Calendar calendar = Calendar.getInstance();

    public CityWeatherFragment(){
        super(R.layout.city_weather_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.city_weather_fragment,container,false);
        findViews(v);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animation = new RotateAnimation(
                0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(6000);

        CityModel city = CityWeatherFragmentArgs.fromBundle(getArguments()).getCityModel();
        cityTextView.setText(String.format("%s, ", city.name));
        country.setText(city.country);

        adapter = new WeatherAdapter(new WeatherAdapter.WeatherClickListener() {
            @Override
            public void onWeatherClick(Forecast forecast) {
                updateData(forecast);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.getWeathers(city.lat, city.lon).observe(getViewLifecycleOwner(), weathers -> {
            adapter.refresh(weathers);
            updateData(weathers.get(0)); // init with 1st forecast
            //weathers.get(0).weather.get(0).main  - SNOW ... RAIN.. SUN. ... THUNDER
//            layout.setBackgroundResource(R.drawable.animation_snow);
//            AnimationDrawable frameAnimation = (AnimationDrawable) layout.getBackground();
//            frameAnimation.start();
        });
    }

    void updateData(Forecast forecast){

        Date date = new Date(forecast.dt*1000);
        calendar.setTime(date);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        day.setText(String.format("%s %s", month, forecast.dtTxt.substring(8, 10)));

        //day.setText(forecast.dtTxt.substring(0, 10));
        time.setText(forecast.dtTxt.substring(11, 16));
        String desc = forecast.weather.get(0).description;
        description.setText(String.format("%s%s", Character.toUpperCase(desc.charAt(0)), desc.substring(1)));
        int temp = (int)Math.round(forecast.main.temp);
        temperature.setText(String.format("%s℃", String.valueOf(temp)));
        osadki.setText(String.format("Осадки: %s%%", String.valueOf(forecast.pop)));
        humidity.setText(String.format("Влажность: %s%%", String.valueOf(forecast.main.humidity)));
        pressure.setText(String.format("Давление: %s гПа", String.valueOf(forecast.main.pressure)));

        int degree = forecast.wind.deg;
        String dir;
        if (degree <= 90 )
            dir = "Ю-З";
        else if (degree <= 180)
            dir = "С-З";
        else if (degree <= 270)
            dir = "С-В";
        else
            dir = "Ю-В";
        direction.setText(String.format("Направление: %s", dir));
        arrowDirection.setRotation(forecast.wind.deg);
        wind.setText(String.format("Скорость: %s м/с", String.valueOf(forecast.wind.speed)));

        animation.setDuration(Double.valueOf(25000/forecast.wind.speed).longValue());
        animationTest.startAnimation(animation);

        String path = "https://openweathermap.org/img/wn/" + forecast.weather.get(0).icon + "@2x.png";
        Glide.with(icon).load(path).into(icon);
    }

    private void findViews(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        cityTextView = view.findViewById(R.id.city);
        country = view.findViewById(R.id.country);
        day = view.findViewById(R.id.day_of_week);
        time = view.findViewById(R.id.time);
        description = view.findViewById(R.id.description);
        temperature = view.findViewById(R.id.temperature);
        osadki = view.findViewById(R.id.osadki);
        humidity = view.findViewById(R.id.humidity);
        pressure = view.findViewById(R.id.pressure);
        direction = view.findViewById(R.id.direction);
        wind = view.findViewById(R.id.wind);


        icon = view.findViewById(R.id.icon);
        animationTest = view.findViewById(R.id.animation_test);
        arrowDirection = view.findViewById(R.id.arrow_direction);
    }
}
