package com.sample.penbrothersexam.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtil {

    private KeyboardUtil() {
    }

    public static void hideSoftKeyboard(Activity activity) {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(@NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        view.postDelayed(() -> showInMainThread(inputMethodManager, view), 200);
    }

    private static void showInMainThread(InputMethodManager manager, final View view) {
        if (view == null)
            return;

        view.requestFocus();
        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

}