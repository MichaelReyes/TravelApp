package com.sample.penbrothersexam.screens.home;


import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.sample.penbrothersexam.R;
import com.sample.penbrothersexam.base.BaseActivity;
import com.sample.penbrothersexam.databinding.ActivityHomeBinding;
import com.sample.penbrothersexam.model.City;
import com.sample.penbrothersexam.screens.city_list.CityListActivity;
import com.sample.penbrothersexam.utils.Constants;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreated() {
        binding.setPresenter(() -> {
            CityListActivity.startWithResult(
                    this,
                    CityListActivity.class,
                    Constants.REQUEST_CODE.CITY_LIST

            );
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CODE.CITY_LIST) {
            if (data.hasExtra(Constants.INTENT_KEY.CITY)) {
                City city = new Gson().fromJson(
                        data.getStringExtra(Constants.INTENT_KEY.CITY),
                        City.class
                );

                if(city != null)
                    binding.tvSelectedCity.setText(String.format(getString(R.string.selected_city),city.getName()));
            }
        }
    }
}
