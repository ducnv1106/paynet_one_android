package com.paynetone.counter.utils;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterCharacterNumber implements InputFilter {

    private boolean isCharacter(String s) {
        return s.matches(".*[0-9]+.*");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if(isCharacter(source.toString())) {
            return source.toString();
        }
        return "";
    }


}