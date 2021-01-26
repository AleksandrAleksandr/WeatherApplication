package com.example.weather.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.model.LocalCity;
import java.util.ArrayList;
import java.util.List;

public class FavoriteCityAdapter extends RecyclerView.Adapter<FavoriteCityAdapter.ViewHolder>{
    private List<LocalCity> cities = new ArrayList<>();
    final private FavoriteCityAdapter.ButtonClickListener mOnClickListener;
    LocalCity oldHome;


    public FavoriteCityAdapter(FavoriteCityAdapter.ButtonClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public FavoriteCityAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_item, viewGroup, false);
        return new FavoriteCityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteCityAdapter.ViewHolder viewHolder, final int position) {
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
        private final TextView textView, countryName;
        private final ImageView imageView;
        private final Button button;
        private final ImageView homeButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.city_name);
            countryName = (TextView) view.findViewById(R.id.country_name);
            imageView = (ImageView) view.findViewById(R.id.icon);
            button = (Button) view.findViewById(R.id.delete_button);
            homeButton = (ImageView) view.findViewById(R.id.set_home_button);

            //imageView.setOnClickListener();
            //view.setOnClickListener(this);
            textView.setOnClickListener(this);
            button.setOnClickListener(this);
            homeButton.setOnClickListener(this);
        }

        public void bind(LocalCity city){
            textView.setText(city.name);
            countryName.setText(city.country);

            if (city.homeLocation){
                Glide.with(homeButton).load(R.drawable.home).into(homeButton);
            }
            else Glide.with(homeButton).load(R.drawable.home_outlined).into(homeButton);

            //Glide.with(imageView).load(R.drawable.us).into(imageView);
            String path = city.country.toLowerCase() + ".png";
            Glide.with(imageView).load(Uri.parse("file:///android_asset/" + path)).into(imageView);
        }

        public void bindHome(LocalCity city){
            if (city.homeLocation)       // выбираем тот же город
                return;

            for (LocalCity elem: cities) {
                if (elem.homeLocation){
                    elem.homeLocation = false;
                    oldHome = elem;
                }
            }
            city.homeLocation = true;
            mOnClickListener.onSetHomeClick(oldHome, city);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()){
                case R.id.delete_button :
                    mOnClickListener.onButtonClick(cities.get(position));
                    break;
                case R.id.city_name :
                    mOnClickListener.onCityNameClick(cities.get(position));
                    break;
                case R.id.set_home_button :
                    bindHome(cities.get(position));
                    break;
            }
        }
    }

    public interface ButtonClickListener{
        void onButtonClick(LocalCity city);
        void onCityNameClick(LocalCity city);
        void onSetHomeClick(LocalCity oldCity, LocalCity newCity);
    }
}
