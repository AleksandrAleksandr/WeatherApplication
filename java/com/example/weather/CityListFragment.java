package com.example.weather;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.adapters.CityListAdapter;
import com.example.weather.model.LocalCity;
import com.example.weather.viewmodels.CityListViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityListFragment extends Fragment{

    RecyclerView recyclerView;
    TextInputEditText editText;
    Button button;
    Boolean update = false;
    CityListAdapter adapter;
    CityListViewModel viewModel;

    public CityListFragment(){
        super(R.layout.city_list_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.city_list_fragment,container,false);
        recyclerView = v.findViewById(R.id.recycler_view);
        editText = v.findViewById(R.id.edit_text);
        button = v.findViewById(R.id.find_button);

        adapter = new CityListAdapter(new CityListAdapter.ButtonClickListener() {
            @Override
            public void onButtonClick(LocalCity city) {
                onClickCity(city);
            }
        });
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CityListViewModel.class);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 2 && update){
                    viewModel.getPopularCities().observe(getViewLifecycleOwner(), cities -> {
                        adapter.refresh(cities);
                    });
                    update = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if (input.isEmpty() || input.length() == 1){

                }
                else {
                    viewModel.findCity(input.trim()).observe(getViewLifecycleOwner(), location -> {
                        // можем не найти город !!!

                        //адаптер работает с LocalCity, надо преобразовать SearchResponce -> LocalCity
                        //LocalCity city = new LocalCity(location.name,location.country, location.lat, location.lon);
                        //List<LocalCity> list = new ArrayList<>();
                        //list.add(new LocalCity())
                        //list.add(city);
                        adapter.refresh(location);
                    });
                    update = true;
                }
            }
        });


        viewModel.getPopularCities().observe(getViewLifecycleOwner(), cities -> {
            adapter.refresh(cities);
        });
    }

    public void onClickCity(LocalCity city){
        viewModel.onClickCity(city);
    }
}
