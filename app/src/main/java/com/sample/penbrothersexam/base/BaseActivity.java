package com.sample.penbrothersexam.base;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sample.penbrothersexam.utils.KeyboardUtil;

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity implements BaseView {

    protected V binding;


    private boolean isScreenBlocked;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        onCreated();
    }


    protected final void hideKeyboard() {
        KeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (!isScreenBlocked)
            super.onBackPressed();
    }

    protected final void setScreenBlocked(boolean screenBlocked) {
        isScreenBlocked = screenBlocked;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * This is to start a specific currentActivity
     *
     * @param currentActivity       - Current currentActivity
     * @param mClass                - Class of the currentActivity to start
     * @param finishCurrentActivity - if true, will finish the current currentActivity and will be removed from back stack.
     */
    public static void start(@NonNull Activity currentActivity, Class mClass, boolean finishCurrentActivity) {
        boolean clearTask = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN;
        Intent intent = new Intent(currentActivity, mClass);
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        currentActivity.startActivity(intent);
        if (finishCurrentActivity)
            currentActivity.finish();

    }

    /**
     * This is to start a specific currentActivity with bundle
     *
     * @param currentActivity       - Current currentActivity
     * @param mClass                - Class of the currentActivity to start
     * @param finishCurrentActivity - if true, will finish the current currentActivity and will be removed from back stack.
     * @param extras                - contains extra parameters for the currentActivity called
     */
    public static void start(@NonNull Activity currentActivity, Class mClass, boolean finishCurrentActivity, Bundle extras) {
        boolean clearTask = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN;
        Intent intent = new Intent(currentActivity, mClass);

        if (extras != null)
            intent.putExtras(extras);

        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        currentActivity.startActivity(intent);
        if (finishCurrentActivity)
            currentActivity.finish();

    }

    /**
     * This is to start a specific currentActivity with result
     *
     * @param currentActivity - Current currentActivity
     * @param mClass          - Class of the currentActivity to start
     * @param code            - The unique identifier of the currentActivity for the onActivityResult callback
     */
    public static void startWithResult(@NonNull Activity currentActivity, Class mClass, int code) {
        boolean clearTask = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN;
        Intent intent = new Intent(currentActivity, mClass);
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        currentActivity.startActivityForResult(intent, code);

    }

    public void setToolbar(boolean show, boolean showIcon, boolean showBackButton, String title) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (show) {
                actionBar.show();

                if (showIcon) {
                    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    //actionBar.setCustomView(R.layout.app_bar_icon);
                }
                actionBar.setHomeButtonEnabled(showBackButton);
                actionBar.setDisplayHomeAsUpEnabled(showBackButton);
                actionBar.setTitle(title);
                actionBar.setElevation(0);

            } else
                actionBar.hide();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}