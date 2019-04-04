package com.sample.penbrothersexam.screens.city_list;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.penbrothersexam.R;
import com.sample.penbrothersexam.custom.CustomRecyclerViewAdapter;
import com.sample.penbrothersexam.databinding.ItemCityBinding;
import com.sample.penbrothersexam.model.City;
import com.sample.penbrothersexam.screens.city_list.CityListViewModel;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends CustomRecyclerViewAdapter {

    private List<City> cities = new ArrayList<>();
    private Context context;

    public CityListAdapter(Context context, CityListViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.context = context;
        viewModel.getCities().observe(lifecycleOwner, cities -> {
            this.cities.clear();
            if (cities != null && cities.size() > 0) {
                this.cities.addAll(cities);
                notifyDataSetChanged();
            }
        });
    }

    public void setCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        notifyDataSetChanged();
    }

    public City getCity(int position) {
        if (position < this.cities.size())
            return this.cities.get(position);
        else
            return null;
    }

    @NonNull
    @Override
    public CustomRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_city, parent, false);
        return new Holder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecycleViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        viewHolder.bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class Holder extends CustomRecycleViewHolder {

        private ItemCityBinding binding;

        public Holder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(City city) {
            binding.tvCityName.setText(city.getName());
            binding.tvCitySubTitle.setText(city.getSubtitle());

            binding.civBanner.setVisibility(View.VISIBLE);
            binding.layoutBanner.setVisibility(View.VISIBLE);

            if(city.getBanner() != null &&
                    !city.getBanner().equalsIgnoreCase("")) {
                binding.civBanner.setImageUrl(city.getBanner());
                binding.layoutBanner.setVisibility(View.GONE);
            }else{
                binding.tvBannerName.setText(city.getName().substring(0,3));
                binding.civBanner.setVisibility(View.INVISIBLE);

                GradientDrawable bgShape = (GradientDrawable) binding.layoutBanner.getBackground();
                bgShape.setColor(Color.parseColor(city.getColor()));
            }

        }
    }
}
