package com.sample.penbrothersexam.screens.city_list;

import android.app.Activity;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.sample.penbrothersexam.R;
import com.sample.penbrothersexam.base.BaseActivity;
import com.sample.penbrothersexam.databinding.ActivityCityListBinding;
import com.sample.penbrothersexam.utils.Constants;
import com.sample.penbrothersexam.utils.KeyboardUtil;

public class CityListActivity extends BaseActivity<ActivityCityListBinding> {


    private CityListViewModel viewModel;
    private CityListAdapter adapter;
    private SearchView searchView;
    private Handler searchHandler;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_city_list;
    }

    @Override
    public void onCreated() {

        setSupportActionBar(binding.toolbar);
        setToolbar(true,false,true,"");

        searchHandler = new Handler();
        viewModel = ViewModelProviders.of(this).get(CityListViewModel.class);

        setupList();
    }

    private void setupList(){
        binding.contentCityList.rvData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new CityListAdapter(this, viewModel, this);
        binding.contentCityList.rvData.setAdapter(adapter);
        binding.contentCityList.rvData.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra(Constants.INTENT_KEY.CITY, new Gson().toJson(adapter.getCity(position)));
            setResult(
                    Activity.RESULT_OK,
                    intent
            );
            finish();
        });
        binding.contentCityList.rvData.setOnTouchListener((v, event) -> {
            KeyboardUtil.hideSoftKeyboard(CityListActivity.this);
            return false;
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCities().observe(this, cities -> {
            if (cities != null) {
                binding.contentCityList.rvData.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getError().observe(this, isError -> {
            //noinspection ConstantConditions
            if (isError) {
                binding.contentCityList.tvError.setVisibility(View.VISIBLE);
                binding.contentCityList.rvData.setVisibility(View.GONE);
            } else
                binding.contentCityList.tvError.setVisibility(View.GONE);

        });

        viewModel.getLoading().observe(this, isLoading -> {
            binding.contentCityList.pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                binding.contentCityList.tvError.setVisibility(View.GONE);
                binding.contentCityList.rvData.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchHandler.removeCallbacksAndMessages(null);
                searchHandler.postDelayed(() -> search(), 1000);

                return false;
            }
        });

        return true;
    }

    private void search(){
        String queryString = searchView.getQuery().toString();
        viewModel.searchCities(queryString);
    }


}
