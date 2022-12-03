package com.paynetone.counter.utils;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterCharacter implements InputFilter {

    private boolean isCharacter(String s) {
        return s.matches(".*[a-zA-Z0-9 đ]+.*");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if(isCharacter(source.toString())) {
            return source.toString();
        }
        return "";
    }


}