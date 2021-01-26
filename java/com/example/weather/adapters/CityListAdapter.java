package com.example.weather.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.model.LocalCity;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder>{

    private List<LocalCity> cities = new ArrayList<>();
    final private CityListAdapter.ButtonClickListener mOnClickListener;


    public CityListAdapter(CityListAdapter.ButtonClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public CityListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_item_layout, viewGroup, false);
        return new CityListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityListAdapter.ViewHolder viewHolder, final int position) {
        LocalCity city = cities.get(position);
        viewHolder.bind(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void refresh(List<LocalCity> cities){
        this.cities = cities;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView, countryName, lat, lon;
        private final ImageView imageView;
        private final Button button;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.city_name);
            countryName = (TextView) view.findViewById(R.id.country_name);
            lat = (TextView) view.findViewById(R.id.lat);
            lon = (TextView) view.findViewById(R.id.lon);
            imageView = (ImageView) view.findViewById(R.id.country_icon);
            button = (Button) view.findViewById(R.id.add_button);

            //imageView.setOnClickListener();
            //view.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        public void bind(LocalCity city){
            textView.setText(city.name);
            countryName.setText(city.country);
            lat.setText(String.format("[%s,", String.valueOf(city.lat)));
            lon.setText(String.format("%s]", String.valueOf(city.lon)));

            String path = city.country.toLowerCase() + ".png";
            Glide.with(imageView).load(Uri.parse("file:///android_asset/" + path)).into(imageView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onButtonClick(cities.get(position));
//            FavoritesFragmentDirections.ActionHomeToCityWeatherFragment action = FavoritesFragmentDirections.actionHomeToCityWeatherFragment();
//            action.setCityName(localDataSet[position]);
//
//            Navigation.findNavController(v).navigate(action);
        }
    }

    public interface ButtonClickListener{
        void onButtonClick(LocalCity city);
    }
}
