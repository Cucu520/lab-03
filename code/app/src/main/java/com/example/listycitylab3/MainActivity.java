package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        dataList.add(new City("Edmonton", "AB"));
        dataList.add(new City("Vancouver", "BC"));
        dataList.add(new City("Toronto", "ON"));

        ListView cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                new AddCityFragment().show(getSupportFragmentManager(), "Add City")
        );

        // ✅ Tap a city to edit
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selectedCity = dataList.get(position);
            EditCityFragment.newInstance(position,
                            selectedCity.getName(),
                            selectedCity.getProvince())
                    .show(getSupportFragmentManager(), "Edit City");
        });
    }

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityEdited(int position, String newName, String newProvince) {
        City city = dataList.get(position);
        city.setName(newName);
        city.setProvince(newProvince);
        cityAdapter.notifyDataSetChanged();
    }
}
