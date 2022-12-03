package com.core.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AppUtils {
    public static void hideKeyboard(View view){
        try{
            InputMethodManager manager  = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
